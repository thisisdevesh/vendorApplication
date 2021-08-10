package com.gts.coordinator.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.gts.coordinator.Model.ContractorData.acceptBooking.AcceptBookingResponse;
import com.gts.coordinator.Model.ContractorData.assignBooking.BookingDataResponse;
import com.gts.coordinator.Model.ContractorData.assignBooking.PostContData;
import com.gts.coordinator.Model.bookingStatus.PostStatus;
import com.gts.coordinator.Model.bookingStatus.StatusRespose;
import com.gts.coordinator.R;
import com.gts.coordinator.Retrofit.RetrofitApiInterface;
import com.gts.coordinator.Retrofit.RetrofitClient;
import com.gts.coordinator.roomDB.MyDatabase;
import com.gts.coordinator.utils.PreferenceData;
import com.gts.coordinator.utils.Utils;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingService extends Service {
    private static final String TAG = "MyTag";
    private Timer mTimer;
    private Handler mHandler = new Handler();
    private static final int TIMER_INTERVAL = 10000; // 2 Minute
    private static final int TIMER_DELAY = 0;
    private MyDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        appDatabase = MyDatabase.getInstance(getApplicationContext());
        Log.d(TAG, "BookingService onCreate: ");
        if (mTimer != null)
            mTimer = null;
        // Create new Timer
        mTimer = new Timer();
        // Required to Schedule DisplayToastTimerTask for repeated execution with an interval of `1 min`
        mTimer.scheduleAtFixedRate(new DisplayToastTimerTask(), TIMER_DELAY, TIMER_INTERVAL);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "BookingService onStartCommand: ");
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
                    new Thread(() -> callbookingStats()).start();
                   // new Thread(() -> callApiAssignList()).start();
                }
            });
        }
    }

    private  void callbookingStats() {
        RetrofitApiInterface apiInterface = RetrofitClient.getClient().create(RetrofitApiInterface.class);
        Call<StatusRespose> call = apiInterface.checkStatus(new PostStatus("",""));
        call.enqueue(new Callback<StatusRespose>() {
            @Override
            public void onResponse(Call<StatusRespose> call, Response<StatusRespose> response) {

            }

            @Override
            public void onFailure(Call<StatusRespose> call, Throwable t) {

            }
        });
//        call.enqueue(new Callback<BookingDataResponse>() {
//            @Override
//            public void onResponse(Call<BookingDataResponse> call, Response<BookingDataResponse> response) {
//                if (response.isSuccessful()) {
//                    BookingDataResponse body = response.body();
//                    appDatabase.myDao().deleteBookingList();
//                    if (body.getStatus() == 0) {
//                        Log.d(TAG, "onResponse: " + body.getMessage());
//                            new Thread(() -> {
//                                try {
//                                    appDatabase.myDao().insertBooking(body.getBookingList());
//                                } catch (Exception e) {
//                                }
//                            }).start();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<BookingDataResponse> call, Throwable t) {
//                Log.e(TAG, "onFailure: " + t);
//            }
//        });
    }

}