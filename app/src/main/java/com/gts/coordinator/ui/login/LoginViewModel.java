package com.gts.coordinator.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gts.coordinator.Activity.SplashActivity;
import com.gts.coordinator.Model.ContractorData.Login.LoginModel;
import com.gts.coordinator.Model.ContractorData.Login.PostLogin;
import com.gts.coordinator.Retrofit.RetrofitApiInterface;
import com.gts.coordinator.Retrofit.RetrofitClientWcm;
import com.gts.coordinator.utils.PreferenceData;
import com.gts.coordinator.utils.Utils;
import com.sdsmdg.tastytoast.TastyToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {
MutableLiveData<String> errorMessage = new MutableLiveData<>();
MutableLiveData<Integer> apiStatus = new MutableLiveData<>();
//    private String userName, password,versionName,osVersionName,deviceName,pleateformName,fcmRegId,deviceId;
//    private int appVersionCode,sdkVersion;
    Context context;

    public LoginViewModel(@NonNull Application application) {
        super(application);

        context=application;
    }


    public MutableLiveData<String> getErrorMessage(){

    return errorMessage;
    }

    public MutableLiveData<Integer> getApiStatus(String userName, String password, String versionName, String osVersionName, String deviceName,
                                                 String pleateformName, String fcmRegId, int appVersionCode, int sdkVersion, String deviceId){

      callLoginApi(userName,password,versionName,osVersionName,deviceName,pleateformName,fcmRegId,appVersionCode,sdkVersion,deviceId);
    return apiStatus;
    }

    private void callLoginApi(String userName, String password, String versionName, String osVersionName, String deviceName, String pleateformName, String fcmRegId, int appVersionCode, int sdkVersion, String deviceId) {
        try {
          RetrofitApiInterface  apiInterface = RetrofitClientWcm.getClient().create(RetrofitApiInterface.class);
            Call<LoginModel> call = apiInterface.contLogin(new PostLogin(userName, password, versionName, appVersionCode, pleateformName, fcmRegId, sdkVersion, osVersionName, deviceName, deviceId));
            Log.e("rss", call.toString());
            call.enqueue(new Callback<LoginModel>() {
                @Override
                public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                    if (response.isSuccessful()) {
                        LoginModel contDetail = response.body();
                        String respMsg = contDetail.getD().getResponse().getMessage();
                        int ste = contDetail.getD().getResponse().getStatus();
                        if (ste == 0) {
                            apiStatus.setValue(ste);
                            errorMessage.setValue(respMsg);
                            getLoginDatail(contDetail);
                        } else {
                            apiStatus.setValue(ste);
                            errorMessage.setValue(respMsg);
                        }
                    } else {
                        errorMessage.setValue(response.message());
                    }
                }

                @Override
                public void onFailure(Call<LoginModel> call, Throwable t) {
                    errorMessage.setValue("onFailure "+t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            errorMessage.setValue("Exception "+e.getMessage());
        }
    }
    private void getLoginDatail(LoginModel contDetail) {

       long coordinatorId = Long.parseLong(String.valueOf(contDetail.getD().getDetails().getID()));
        PreferenceData.saveLong(context, "cont_id", coordinatorId);
        String email = contDetail.getD().getDetails().getUserName();
        PreferenceData.saveString(context, "con_email", email);
        String versionName = contDetail.getD().getDetails().getVersionName();
        PreferenceData.saveString(context, "ver_name", versionName);
        int city_id = contDetail.getD().getDetails().getCityId();
        PreferenceData.saveInt(context, "city_id", city_id);
        String address = contDetail.getD().getDetails().getContAddress1();
        PreferenceData.saveString(context, "cont_address", address);
        String conName = contDetail.getD().getDetails().getContName();
        PreferenceData.saveString(context, "cont_name", conName);
        String phoneNo = contDetail.getD().getDetails().getContPhone1();
        PreferenceData.saveString(context, "cont_phone1", phoneNo);
        String psswords = contDetail.getD().getDetails().getPassword();
        PreferenceData.saveString(context, "password", psswords);
        PreferenceData.saveInt(context, "is_synced", contDetail.getD().getDetails().getIsSynced());
        PreferenceData.saveBoolean(context, "login_status", true);
        apiStatus.setValue(2);
    }
}