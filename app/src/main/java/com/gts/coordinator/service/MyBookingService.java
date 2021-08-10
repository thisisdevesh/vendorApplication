package com.gts.coordinator.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.JobIntentService;
import androidx.lifecycle.MutableLiveData;

import com.gts.coordinator.Model.ContractorData.BookingActivities.BookingActivitiesList;
import com.gts.coordinator.Model.ContractorData.BookingActivities.GetBookingActivitiesData;
import com.gts.coordinator.Model.ContractorData.BookingActivities.SetBookingActivitiesData;
import com.gts.coordinator.Model.ContractorData.acceptBooking.AcceptBookingResponse;
import com.gts.coordinator.Model.ContractorData.acceptBooking.AssignList;
import com.gts.coordinator.Model.ContractorData.assignBooking.BookingDataResponse;
import com.gts.coordinator.Model.ContractorData.assignBooking.BookingList;
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

public class MyBookingService extends JobIntentService {
    private static final String TAG = "MyTag";
    private Timer mTimer;
    private Handler mHandler = new Handler();
    private static final int TIMER_INTERVAL = 5000; // 5 sec
    private static final int TIMER_DELAY = 0;

    public static final int JOB_ID = 1;

    public static MutableLiveData<List<BookingList>> unassignedBookingList = new MutableLiveData<>();
    public static MutableLiveData<List<AssignList>> assignedBookingList = new MutableLiveData<>();
    public static MutableLiveData<List<BookingActivitiesList>> bookingActivitiesList = new MutableLiveData<>();


    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, MyBookingService.class, JOB_ID, work);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "MyBookingService onCreate: ");
        if (mTimer != null)
            mTimer = null;
        // Create new Timer
        mTimer = new Timer();
        // Required to Schedule DisplayToastTimerTask for repeated execution with an interval of `1 min`
        mTimer.scheduleAtFixedRate(new MyBookingService.DisplayToastTimerTask(), TIMER_DELAY, TIMER_INTERVAL);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "MyBookingService onStartCommand: ");
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
                    if (body.getStatus() == 0) {
                        Log.d(TAG, "onResponse UnAssignedBooking: " + body.getMessage());
                        Log.d(TAG, "onResponse UnAssignedBooking Size: " + body.getBookingList().size());
                        unassignedBookingList.setValue(body.getBookingList());
                    }else if (response.body().getStatus() == 2){
                        Log.d(TAG, "onResponse UnAssignedBooking: " + body.getMessage());
                        Log.d(TAG, "onResponse UnAssignedBooking Size: " + body.getBookingList().size());
                        unassignedBookingList.setValue(body.getBookingList());
                    }
                }
            }

            @Override
            public void onFailure(Call<BookingDataResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t);
                call.cancel();
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
                        Log.d(TAG, "onResponse Assigned: " + response.body().getMessage());
                        Log.d(TAG, "onResponse Assigned Size: " + response.body().getAssignList().size());
                        assignedBookingList.setValue(response.body().getAssignList());
                    }
                }
            }

            @Override
            public void onFailure(Call<AcceptBookingResponse> call, Throwable t) {
                call.cancel();
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
                        Log.d(TAG, "onResponse Booking Activities: " + response.body().getMessage());
                        Log.d(TAG, "onResponse Booking Activities Size: " + response.body().getBookingActivitiesList().size());
                      bookingActivitiesList.setValue(response.body().getBookingActivitiesList());
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
