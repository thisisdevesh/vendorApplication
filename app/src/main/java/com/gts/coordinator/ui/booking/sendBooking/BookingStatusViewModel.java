package com.gts.coordinator.ui.booking.sendBooking;

import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gts.coordinator.Model.bookingStatus.PostStatus;
import com.gts.coordinator.Model.bookingStatus.StatusRespose;
import com.gts.coordinator.Retrofit.RetrofitApiInterface;
import com.gts.coordinator.Retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingStatusViewModel extends ViewModel {
    MutableLiveData<String> response;
    MutableLiveData<String> errorMessage;//= new MutableLiveData<>();
    MutableLiveData<Boolean> deliveryStatus;// = new MutableLiveData<>();

    private int seconds = 0;
    Handler handler = new Handler();
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;
    int Seconds, Minutes, Hours, count = 0;
    String bookingId, cabNo;
    boolean status;

    public MutableLiveData<String> getResponse(String bookingId, String cabNo) {
        response = new MutableLiveData<>();
        count = 0;
        Seconds = 0;
        Hours = 0;
        Minutes = 0;
        this.bookingId = bookingId;
        this.cabNo = cabNo;
        status = true;
        startTimer();
        return response;
    }

    public MutableLiveData<String> getErrorMessage() {
        errorMessage = new MutableLiveData<>();
        return errorMessage;
    }

    public MutableLiveData<Boolean> getDeliveryStatus() {
        deliveryStatus = new MutableLiveData<>();
        return deliveryStatus;
    }

    private void bookingStats(String bookingId, String cabNo) {
        Log.e("statuCou", "call: Count " + count);
        RetrofitApiInterface apiInterface = RetrofitClient.getClient().create(RetrofitApiInterface.class);
        Call<StatusRespose> call = apiInterface.checkStatus(new PostStatus(bookingId, cabNo));
        call.enqueue(new Callback<StatusRespose>() {
            @Override
            public void onResponse(Call<StatusRespose> call, Response<StatusRespose> res) {
                if (res.isSuccessful()) {
                    if (res.body().getResponse() != null) {
                        response.postValue(res.body().getResponse());
                        if (res.body().getResponse().equalsIgnoreCase("Delivered")) {
                            // resetTime();
                            pauseTime();
                            deliveryStatus.postValue(false);
                        }
                    }

                } else {
                    if (res.message()!=null){
                        errorMessage.postValue(res.message());
                    }
                    deliveryStatus.postValue(false);
                    pauseTime();
                }


            }

            @Override
            public void onFailure(Call<StatusRespose> call, Throwable t) {
                errorMessage.postValue(t.getMessage());
                deliveryStatus.postValue(false);
                pauseTime();
                status = false;


            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // resetTime();
        pauseTime();

    }

    public void startTimer() {
        StartTime = SystemClock.uptimeMillis();
        handler.postDelayed(runnable, 0);
    }

    public void pauseTime() {
        TimeBuff = 00;
        TimeBuff += MillisecondTime;
        handler.removeCallbacks(runnable);
    }

    public Runnable runnable = new Runnable() {

        public void run() {
            //  Log.e("statuCou", "run: "+count+status );
//            if (count%10==0){
//                status = false;
//                pauseTime();
//
//            }

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = TimeBuff + MillisecondTime;
            Seconds = (int) (UpdateTime / 1000);
            Hours = Seconds / 3600;
            Minutes = Seconds / 60;
            Seconds = Seconds % 60;
              Log.e("Minutes", "run: "+Minutes );
            if (Minutes % 2 == 0) {
                count++;
                if (count > 5) {
                    handler.removeCallbacks(runnable);
                    deliveryStatus.postValue(false);
                    onCleared();
                } else {
                    bookingStats(bookingId, cabNo);
                }
                Log.e("statuCou", "call " + count);

//                    if (status==true) {
//
//
//                    }
            }
            handler.postDelayed(this, 20000);

            //  int timeDetail[] = {Hours, Minutes, Seconds};
            // updateTime.postValue(timeDetail);
        }

    };


}
