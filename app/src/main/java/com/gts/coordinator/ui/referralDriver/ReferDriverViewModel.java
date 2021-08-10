package com.gts.coordinator.ui.referralDriver;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gts.coordinator.Model.ContractorData.assignBooking.PostContData;
import com.gts.coordinator.Model.referralDriver.ReferralDriverRespose;
import com.gts.coordinator.Retrofit.RetrofitApiInterface;
import com.gts.coordinator.Retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReferDriverViewModel extends ViewModel {
    MutableLiveData<ReferralDriverRespose> driverList;
    MutableLiveData<String> message = new MutableLiveData<>() ;
    
    public MutableLiveData<ReferralDriverRespose> getDriverList(long cont_id){
            driverList = new MutableLiveData<ReferralDriverRespose>();
            callApiGetDriverList(cont_id);
            return driverList;
    }
    public MutableLiveData<String> getStatusMessage(){
        return message;
    }

    private void callApiGetDriverList(long cont_id) {
        RetrofitApiInterface apiInterface = RetrofitClient.getClient().create(RetrofitApiInterface.class);
        Call<ReferralDriverRespose> call = apiInterface.getReferralDriver(new PostContData(cont_id));
        call.enqueue(new Callback<ReferralDriverRespose>() {
            @Override
            public void onResponse(Call<ReferralDriverRespose> call, Response<ReferralDriverRespose> response) {
                if (response.isSuccessful()){
                    if (response.body().getGetresponse().getStatus()==0){
                     //   message.setValue(response.body().getGetresponse().getMessage());
                       driverList.setValue(response.body());
                    }else {
                        message.setValue(response.body().getGetresponse().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ReferralDriverRespose> call, Throwable t) {
                message.setValue("onFailure "+t.getMessage());
            }
        });
    }


}
