package com.gts.coordinator.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gts.coordinator.Model.ContractorData.BookingActivities.GetBookingActivitiesData;
import com.gts.coordinator.Model.ContractorData.BookingActivities.SetBookingActivitiesData;
import com.gts.coordinator.Model.ContractorData.acceptBooking.AcceptBookingResponse;
import com.gts.coordinator.Model.ContractorData.assignBooking.BookingDataResponse;
import com.gts.coordinator.Model.ContractorData.assignBooking.PostContData;
import com.gts.coordinator.R;
import com.gts.coordinator.Retrofit.RetrofitApiInterface;
import com.gts.coordinator.Retrofit.RetrofitClient;
import com.gts.coordinator.roomDB.MyDatabase;
import com.gts.coordinator.utils.PreferenceData;
import com.gts.coordinator.utils.Utils;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MySimpleService extends Service {
    private static final String TAG = "MyTag"; 
    private Timer mTimer;
    private Handler mHandler = new Handler();
    private static final int TIMER_INTERVAL = 20000; // 20 sec
    private static final int TIMER_DELAY = 0;
    private MyDatabase appDatabase;
    public static MutableLiveData<String> messageBookingList = new MutableLiveData<>();
    public static MutableLiveData<String> messageAssignedList = new MutableLiveData<>();
    public static MutableLiveData<String> messageBookingActivities = new MutableLiveData<>();


    @Override
    public void onCreate() {
        super.onCreate();
        appDatabase = MyDatabase.getInstance(getApplicationContext());
        Log.d(TAG, "MySimpleService onCreate: ");
        if (mTimer != null)
            mTimer = null;
        // Create new Timer
        mTimer = new Timer();
        // Required to Schedule DisplayToastTimerTask for repeated execution with an interval of `1 min`
        mTimer.scheduleAtFixedRate(new DisplayToastTimerTask(), TIMER_DELAY, TIMER_INTERVAL);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "MySimpleService onStartCommand: ");
        return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "My Service onDestroy: ");
        // Cancel timer
        mTimer.cancel();
    }


    private class DisplayToastTimerTask extends TimerTask {
        @Override
        public void run() {
            mHandler.post(() -> {
                if (Utils.getConnectivityStatus(getApplicationContext()) == 0) {
                    Utils.showOkAlert(getApplicationContext(), getString(R.string.info), "Please check your internet connection", false);
                } else {
                    new Thread(() -> callApbookingList()).start();
                    new Thread(() -> callApiAssignList()).start();
                    new Thread(() -> callApiBookingActivitiesList()).start();
                }
            });
        }
    }

    private  void callApbookingList() {
        Log.d(TAG,"Api BookingList Called");
        RetrofitApiInterface apiInterface = RetrofitClient.getClient().create(RetrofitApiInterface.class);
        Call<BookingDataResponse> call = apiInterface.getAllData(new PostContData(PreferenceData.getLong(getApplicationContext(), "cont_id")));
        call.enqueue(new Callback<BookingDataResponse>() {
            @Override
            public void onResponse(Call<BookingDataResponse> call, Response<BookingDataResponse> response) {
                if (response.isSuccessful()) {
                    BookingDataResponse body = response.body();
                    appDatabase.myDao().deleteBookingList();
                    if (body.getStatus() == 0) {
                        Log.d(TAG, "onResponse ApiBookingList: " + body.getMessage());
                        messageBookingList.setValue(body.getMessage());
                            new Thread(() -> {
                                try {
                                    appDatabase.myDao().insertBooking(body.getBookingList());
                                } catch (Exception e) {
                                }
                            }).start();
                    }
                }
            }

            @Override
            public void onFailure(Call<BookingDataResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t);
            }
        });
    }

    private  void callApiAssignList() {
        RetrofitApiInterface apiInterface = RetrofitClient.getClient().create(RetrofitApiInterface.class);
        Call<AcceptBookingResponse> call = apiInterface.getAcceptBooking(new PostContData(PreferenceData.getLong(getApplicationContext(), "cont_id")));
        call.enqueue(new Callback<AcceptBookingResponse>() {
            @Override
            public void onResponse(Call<AcceptBookingResponse> call, Response<AcceptBookingResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 0) {
                        messageAssignedList.setValue(response.body().getMessage());
                            appDatabase.myDao().deleteAcceptBookingList();
                            new Thread(() -> appDatabase.myDao().insertAcceptBooking(response.body().getAssignList())).start();
                    }
                }
            }

            @Override
            public void onFailure(Call<AcceptBookingResponse> call, Throwable t) {

            }
        });
    }

    private void callApiBookingActivitiesList(){
        RetrofitApiInterface apiInterface = RetrofitClient.getClient().create(RetrofitApiInterface.class);
        Call<GetBookingActivitiesData> call = apiInterface.getBookingActivities(new SetBookingActivitiesData((PreferenceData.getLong(getApplicationContext(), "cont_id").toString())));
        call.enqueue(new Callback<GetBookingActivitiesData>() {
            @Override
            public void onResponse(Call<GetBookingActivitiesData> call, Response<GetBookingActivitiesData> response) {
                if (response.isSuccessful() && response.body()!=null)
                {
                    if (response.body().getStatus() == 0)
                    {
                        messageBookingActivities.setValue(response.body().getMessage());
                        appDatabase.myDao().deleteBookingActivitiesList();
                        new Thread(() -> appDatabase.myDao().insertBookingActivities(response.body().getBookingActivitiesList())).start();
                    }
                }
            }
            @Override
            public void onFailure(Call<GetBookingActivitiesData> call, Throwable t) {
                call.cancel();
            }
        });
    }
}