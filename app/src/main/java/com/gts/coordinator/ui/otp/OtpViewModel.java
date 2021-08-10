package com.gts.coordinator.ui.otp;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.gts.coordinator.Model.ContractorData.GetOtpForgotPasswod.PostUserName;
import com.gts.coordinator.Model.ContractorData.GetOtpForgotPasswod.ResponseResetOtp;
import com.gts.coordinator.Model.ContractorData.assignBooking.PostContData;
import com.gts.coordinator.Model.en.EnOtpRespose;
import com.gts.coordinator.Retrofit.RetrofitApiInterface;
import com.gts.coordinator.Retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OtpViewModel extends ViewModel {

MutableLiveData<ResponseResetOtp> responseApi ;
MutableLiveData<String> errorMessage = new MutableLiveData<>();


 public MutableLiveData<ResponseResetOtp> getApiResponse(String userName){
         responseApi =new MutableLiveData<>();
         callApi(userName);
     return responseApi;
 }
 public MutableLiveData<String> getErrorMessage(){
     return errorMessage;
    }


    public void callApi(String userName) {
        RetrofitApiInterface apiInterface = RetrofitClient.getClient().create(RetrofitApiInterface.class);
        Call<ResponseResetOtp> call = apiInterface.postUserName(new PostUserName(userName));
        call.enqueue(new Callback<ResponseResetOtp>() {
            @Override
            public void onResponse(Call<ResponseResetOtp> call, Response<ResponseResetOtp> response) {
                if (response.isSuccessful()) {
                    try {
                        ResponseResetOtp getOtpModel = response.body();
                        responseApi.setValue(getOtpModel);
                    } catch (Exception e) {
                        errorMessage.setValue("Exception :- "+ e.getMessage());
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseResetOtp> call, Throwable t) {
                errorMessage.setValue("onFailure :- "+t.getMessage());
            }
        });
    }
    /*EncryptionUtils en = new EncryptionUtils();
        RetrofitApiInterface apiInterface = RetrofitClient.getClient().create(RetrofitApiInterface.class);
        Call<EnOtpRespose> call = apiInterface.postOtp(new PostContData(1));
        call.enqueue(new Callback<EnOtpRespose>() {
            @Override
            public void onResponse(Call<EnOtpRespose> call, Response<EnOtpRespose> response) {
                if (response.isSuccessful()){
                    EnOtpRespose res = response.body();
                    res.getOtp();
                    try {
                        en.decrypt(res.getOtp(),"1121212121212");
                        String otp = en.decT;
                        Log.e("rssddddd", "onResponse:OTP "+otp );

                    }catch (Exception e){}
                }
            }

            @Override
            public void onFailure(Call<EnOtpRespose> call, Throwable t) {

            }
        });*/
}
