package com.gts.coordinator.otp.OtpFragmentAddVendor;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gts.coordinator.Model.ContractorData.GetOtp.PostMobileNo;
import com.gts.coordinator.Model.en.EnOtpRespose;
import com.gts.coordinator.Retrofit.RetrofitApiInterface;
import com.gts.coordinator.Retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpAddVendorViewModel extends ViewModel {

    MutableLiveData<EnOtpRespose> responseApi ;
    MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public MutableLiveData<EnOtpRespose> getApiResponse(String phoneNo){
        responseApi =new MutableLiveData<>();
        callApi(phoneNo);
        return responseApi;
    }

    public MutableLiveData<String> getErrorMessage(){
        return errorMessage;
    }


    public void callApi(String phoneNo) {
        RetrofitApiInterface apiInterface = RetrofitClient.getClient().create(RetrofitApiInterface.class);
        Call<EnOtpRespose> call = apiInterface.postOtp(new PostMobileNo(phoneNo));
        call.enqueue(new Callback<EnOtpRespose>() {
            @Override
            public void onResponse(Call<EnOtpRespose> call, Response<EnOtpRespose> response) {
                if (response.isSuccessful()){
                    EnOtpRespose res = response.body();
                    try {
                        responseApi.setValue(res);
                    }catch (Exception e){}
                }
            }
            @Override
            public void onFailure(Call<EnOtpRespose> call, Throwable t) {

            }
        });
    }
}
