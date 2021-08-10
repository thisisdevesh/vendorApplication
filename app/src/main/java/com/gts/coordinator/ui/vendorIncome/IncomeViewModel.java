package com.gts.coordinator.ui.vendorIncome;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gts.coordinator.Model.income.IncomeRespose;
import com.gts.coordinator.Model.income.PostDataIncome;
import com.gts.coordinator.Retrofit.RetrofitApiInterface;
import com.gts.coordinator.Retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IncomeViewModel extends ViewModel {
    MutableLiveData<IncomeRespose> resposeLiveData;
    MutableLiveData<String> errorMessage = new MutableLiveData<>();


    MutableLiveData<IncomeRespose>getIncom(String contid,String fromDate,String toDate){
        resposeLiveData = new MutableLiveData<>();
        callApiIncome(contid,fromDate,toDate);
        return resposeLiveData;
    }
    MutableLiveData<String>getErrorMessage(){
       // resposeLiveData = new MutableLiveData<>();
       // callApiIncome(contid,fromDate,toDate);
        return errorMessage;
    }

    private void callApiIncome(String contid, String fromDate, String toDate) {
        RetrofitApiInterface apiInterface = RetrofitClient.getClient().create(RetrofitApiInterface.class);
        Call<IncomeRespose> call = apiInterface.getVendorIncome(new PostDataIncome(contid,fromDate,toDate));
        call.enqueue(new Callback<IncomeRespose>() {
            @Override
            public void onResponse(Call<IncomeRespose> call, Response<IncomeRespose> response) {
                if (response.isSuccessful()){
                    resposeLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<IncomeRespose> call, Throwable t) {
                errorMessage.postValue(t.getMessage());

            }
        });
    }
}
