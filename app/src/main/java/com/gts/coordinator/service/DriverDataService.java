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

import com.gts.coordinator.Model.ContractorData.acceptBooking.AcceptBookingResponse;
import com.gts.coordinator.Model.ContractorData.assignBooking.BookingDataResponse;
import com.gts.coordinator.Model.ContractorData.assignBooking.PostContData;
import com.gts.coordinator.Model.getAll.Drivervendorlist;
import com.gts.coordinator.Model.getAll.ResposeGetAll;
import com.gts.coordinator.R;
import com.gts.coordinator.Retrofit.RetrofitApiInterface;
import com.gts.coordinator.Retrofit.RetrofitClient;
import com.gts.coordinator.roomDB.MyDatabase;
import com.gts.coordinator.utils.PreferenceData;
import com.gts.coordinator.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverDataService extends JobIntentService {
    private static final String TAG = "MyTag";
    private Timer mTimer;
    private Handler mHandler = new Handler();
    private static final int TIMER_INTERVAL = 30000; // 2 Minute
    private static final int TIMER_DELAY = 0;
    private MyDatabase appDatabase;


    public static final int JOB_ID = 1;
    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, DriverDataService.class, JOB_ID, work);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        appDatabase = MyDatabase.getInstance(getApplicationContext());
        Log.d(TAG, "DriveDataService onCreate: ");
        if (mTimer != null)
            mTimer = null;
        // Create new Timer
        mTimer = new Timer();
        // Required to Schedule DisplayToastTimerTask for repeated execution with an interval of `1 min`
        mTimer.scheduleAtFixedRate(new DisplayToastTimerTask(), TIMER_DELAY, TIMER_INTERVAL);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "DriveDataService onStartCommand: ");
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

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
    }


    private class DisplayToastTimerTask extends TimerTask {
        @Override
        public void run() {
            mHandler.post(() -> {
                if (Utils.getConnectivityStatus(getApplicationContext()) == 0) {
                    Utils.showOkAlert(getApplicationContext(), getString(R.string.info), "Please check your internet connection", false);
                } else {
                    new Thread(() -> getDriver()).start();
                   // new Thread(() -> callApiAssignList()).start();
                }
            });
        }
    }

    private void getDriver() {

        RetrofitApiInterface apiInterface = RetrofitClient.getClient().create(RetrofitApiInterface.class);
        Call<ResposeGetAll> call = apiInterface.getData(new PostContData(PreferenceData.getLong(getApplicationContext(), "cont_id")));
        call.enqueue(new Callback<ResposeGetAll>() {
            @Override
            public void onResponse(Call<ResposeGetAll> call, Response<ResposeGetAll> response) {
                Log.e("TAG", "onResponse: "+response );

                if (response.isSuccessful()){
                    if (response.body().getGetresponse().getStatus()==0){
                        appDatabase.myDao().deleteDriverList2();
                        List<Drivervendorlist> list =new ArrayList<>();
                        list = response.body().getDrivervendorlist();
                        List<Drivervendorlist> finalList = list;
                        new Thread(() -> {
                            appDatabase.myDao().insertDriverVen(finalList);
                        }).start();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResposeGetAll> call, Throwable t) {
                Log.e("TAG", "onFailure: "+t.getMessage() );
            }
        });

    }
}