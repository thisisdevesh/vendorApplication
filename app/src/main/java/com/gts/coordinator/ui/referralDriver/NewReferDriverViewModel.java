package com.gts.coordinator.ui.referralDriver;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gts.coordinator.Model.referralDriver.PostNo;
import com.gts.coordinator.Model.referralDriverNew.NewReferralDriverRespose;
import com.gts.coordinator.Retrofit.RetrofitApiInterface;
import com.gts.coordinator.Retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewReferDriverViewModel extends ViewModel {
    MutableLiveData<NewReferralDriverRespose> driverList;
    MutableLiveData<String> message = new MutableLiveData<>();

    public MutableLiveData<NewReferralDriverRespose> getApiResponse(String no) {
            driverList = new MutableLiveData<NewReferralDriverRespose>();
            callApiGetDriverList(no);
        return driverList;
    }

    public MutableLiveData<String> getStatusMessage() {
        return message;
    }

    private void callApiGetDriverList(String no) {
        RetrofitApiInterface apiInterface = RetrofitClient.getClient().create(RetrofitApiInterface.class);
        Call<NewReferralDriverRespose> call = apiInterface.getNewReferralDriver(new PostNo(no));
        call.enqueue(new Callback<NewReferralDriverRespose>() {
            @Override
            public void onResponse(Call<NewReferralDriverRespose> call, Response<NewReferralDriverRespose> response) {
                if (response.isSuccessful()) {
                    if (response.body().getGetresponse().getStatus() == 0) {
                      //  message.setValue(response.body().getGetresponse().getMessage());
                        driverList.postValue(response.body());
                    } else {
                        message.setValue(response.body().getGetresponse().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<NewReferralDriverRespose> call, Throwable t) {
                message.setValue("onFailure " + t.getMessage());
            }
        });
    }


}
