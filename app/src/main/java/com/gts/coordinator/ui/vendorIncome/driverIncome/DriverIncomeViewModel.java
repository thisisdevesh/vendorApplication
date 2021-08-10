package com.gts.coordinator.ui.vendorIncome.driverIncome;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gts.coordinator.Model.driverIncome.DriverIncomeRespose;
import com.gts.coordinator.Model.driverIncome.PostDataDrIncome;
import com.gts.coordinator.Model.income.IncomeRespose;
import com.gts.coordinator.Model.income.PostDataIncome;
import com.gts.coordinator.Retrofit.RetrofitApiInterface;
import com.gts.coordinator.Retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverIncomeViewModel extends ViewModel {
    MutableLiveData<DriverIncomeRespose> incomeRespose;
    MutableLiveData<String> errorMessage;

   public MutableLiveData<DriverIncomeRespose> getIncomeRespose(String vid,String fDate,String toDate){
        incomeRespose =new MutableLiveData<>();
        callApi(vid,fDate,toDate);
        return incomeRespose;
    }
    public MutableLiveData<String> getErrorMessage(){
       errorMessage =new MutableLiveData<>();
       return errorMessage;
    }

    private void callApi(String vid, String fDate, String toDate) {
        RetrofitApiInterface apiInterface = RetrofitClient.getClient().create(RetrofitApiInterface.class);
        Call<DriverIncomeRespose> call = apiInterface.getDriverIncome(new PostDataDrIncome(vid,fDate,toDate));
        call.enqueue(new Callback<DriverIncomeRespose>() {
            @Override
            public void onResponse(Call<DriverIncomeRespose> call, Response<DriverIncomeRespose> response) {
              if (response.isSuccessful()){
                  incomeRespose.postValue(response.body());
              }else {
                  errorMessage.postValue(response.message());
              }
            }

            @Override
            public void onFailure(Call<DriverIncomeRespose> call, Throwable t) {
                errorMessage.postValue(t.getMessage());
            }
        });
    }
}
