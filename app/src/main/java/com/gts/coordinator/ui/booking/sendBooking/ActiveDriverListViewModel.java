package com.gts.coordinator.ui.booking.sendBooking;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gts.coordinator.Model.ContractorData.activeDriver.ActiveDriveDataResponse;
import com.gts.coordinator.Model.ContractorData.activeDriver.PostActiveDriver;
import com.gts.coordinator.Retrofit.RetrofitApiInterface;
import com.gts.coordinator.Retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActiveDriverListViewModel extends ViewModel {
    private static final String TAG = "MyTag";
    MutableLiveData<Response<ActiveDriveDataResponse>> activeDriverList;
    MutableLiveData<String> errorMessage = new MutableLiveData<>();
    public LiveData<Response<ActiveDriveDataResponse>> getActiveDrivers(long vendorId, String bookingId, String cabNo, int bookingRqT){// activeDriverList =null;
            activeDriverList = new MutableLiveData<Response<ActiveDriveDataResponse>>();
            callApiDriverList(vendorId, bookingId,cabNo,bookingRqT);
        return activeDriverList;
    }
    public MutableLiveData<String> getErrorMessage(){
        return errorMessage;
    }
    public void callApiDriverList(long vendorId, String bookingId, String cabNo, int bookingRqT){
        RetrofitApiInterface apiInterface = RetrofitClient.getClient().create(RetrofitApiInterface.class);
        Call<ActiveDriveDataResponse> call = apiInterface.getDriverDetail(new PostActiveDriver(vendorId,bookingId, cabNo, bookingRqT));
        call.enqueue(new Callback<ActiveDriveDataResponse>() {
            @Override
            public void onResponse(Call<ActiveDriveDataResponse> call, Response<ActiveDriveDataResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getStatus()==0){
                            activeDriverList.setValue(response);
                    }else {errorMessage.postValue(response.body().getMessage());}
                }else {
                    errorMessage.postValue(response.body().getMessage());
                }
            }
            @Override
            public void onFailure(Call<ActiveDriveDataResponse> call, Throwable t) {
                errorMessage.postValue("onFailure "+t.getMessage());
            }
        });
    }
}
