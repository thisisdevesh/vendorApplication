package com.gts.coordinator.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.gts.coordinator.Activity.SplashActivity;

public class InternetConnector_Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try{
            boolean isVisible = MyApplication.isActivityVisible();

            if (isVisible==true){
                ConnectivityManager connectivityManager =(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if (networkInfo !=null && networkInfo.isConnected()){
                    new SplashActivity().changeTextStatus(true);
                }else {
                    new SplashActivity().changeTextStatus(false);

                }

            }
        }catch (Exception e){}
    }
}
