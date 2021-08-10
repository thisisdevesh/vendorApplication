package com.gts.coordinator.BroadcastReceiver;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.gts.coordinator.Payumoneypnp.AppEnvironment;

public class MyApplication extends Application {
	public static final String CHANNEL_ID = "exampleServiceChannel";
	private static final String TAG = "MyTag";
	AppEnvironment appEnvironment;
	@Override
	public void onCreate() {
		super.onCreate();
		appEnvironment = AppEnvironment.PRODUCTION;
		createNotificationChannel();
		Log.e(TAG, "onCreate: MyApplication" );
	}
// Gloabl declaration of variable to use in whole app

	public static boolean activityVisible; // Variable that will check the
											// current activity state

	public static boolean isActivityVisible() {
		return activityVisible; // return true or false
	}

	public static void activityResumed() {
		activityVisible = true;// this will set true when activity resumed
		Log.e(TAG, "activityResumed: MyApplication");

	}

	public static void activityPaused() {
		activityVisible = false;// this will set false when activity paused

	}

	private void createNotificationChannel() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			NotificationChannel serviceChannel = new NotificationChannel(
					CHANNEL_ID,
					"NotificationResponce Service Channel",
					NotificationManager.IMPORTANCE_DEFAULT
			);

			NotificationManager manager = getSystemService(NotificationManager.class);
			manager.createNotificationChannel(serviceChannel);
		}
	}
	public AppEnvironment getAppEnvironment() {
		return appEnvironment;
	}

	public void setAppEnvironment(AppEnvironment appEnvironment) {
		this.appEnvironment = appEnvironment;
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		Log.e(TAG, "onLowMemory: MyApplication" );
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		Log.e(TAG, "onTerminate: MyApplication " );
	}

}