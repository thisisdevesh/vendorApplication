package com.gts.coordinator.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.gts.coordinator.Activity.SplashActivity;
import com.gts.coordinator.Model.ContractorData.ContWalateData.PostContid;
import com.gts.coordinator.Model.ContractorData.ContractorDetail.ContDetail;
import com.gts.coordinator.Model.ContractorData.ContractorDetail.DriverList;
import com.gts.coordinator.Model.ContractorData.ContractorDetail.VendorList;
import com.gts.coordinator.Retrofit.RetrofitApiInterface;
import com.gts.coordinator.Retrofit.RetrofitClientWcm;
import com.gts.coordinator.callback.TaskListener;
import com.gts.coordinator.db.CoordinatorDbHelper;
import com.gts.coordinator.db.DriverDBInfo;
import com.gts.coordinator.db.VendorDBInfo;
import com.gts.coordinator.roomDB.MyDatabase;
import com.gts.coordinator.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceProcessor {

  // public List<DriverList> driverLists ;

    public static void downloadCoordinatorData(final Context context, long coordinatorId, final TaskListener taskListener) {
    //    MyDatabase appDatabase;

        if (taskListener != null) {
            taskListener.onStart();
        }
       // appDatabase = MyDatabase.getInstance(context.getApplicationContext());
        RetrofitApiInterface apiInterface = RetrofitClientWcm.getClient().create(RetrofitApiInterface.class);
        Call<ContDetail> call = apiInterface.contIdcon(new PostContid(coordinatorId));
        call.enqueue(new Callback<ContDetail>() {
            @Override
            public void onResponse(Call<ContDetail> call, Response<ContDetail> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> {
                        try {
                            ContDetail contDetail = response.body();
                            int statusContrator = contDetail.getD().getResponse().getStatus();
                            String message = contDetail.getD().getResponse().getMessage();
                            Log.e("rss", message);
                            if (statusContrator == 0) {
                                //splashActivity.removeDbInfo();
                              //  activity.removeDbInfo();
                                //  JSONArray vendorList = rootObject.getJSONArray("VendorList");
                            //    appDatabase.myDao().deleteDriverList();
                                List<VendorList> vendorList;
                                vendorList = contDetail.getD().getVendorList();
                                CoordinatorDbHelper dbHelper = new CoordinatorDbHelper(context);
                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                int vendorsCount = vendorList.size();
                                Log.i("MenuActivity", "vendorsCount=" + vendorsCount);
                                db.beginTransaction();
                                DriverDBInfo.deleteAllDrivers(db);
                                VendorDBInfo.deleteAllVendors(db);
                              //  db.close();
                              //  dbHelper.close();
                                for (int i = 0; i < vendorsCount; i++) {
                                    List<DriverList> driverList = vendorList.get(i).getDriverList();

                                    //  JSONArray driverList = venElement.getJSONArray("DriverList");
                                    String vendorName = vendorList.get(i).getVendorName();
                                    int venStatus2 = vendorList.get(i).getVendorStatus();
                                    byte venStatus = (byte) venStatus2;
                                    boolean isVeryfiedVend = vendorList.get(i).getIsVerifiedVndor();
                                    boolean isCabAvailable = vendorList.get(i).getIsCabAvailable();
                                    double vendorBalance2 = vendorList.get(i).getVendorBalance();
                                    long vendorBalance = (long) vendorBalance2;
                                    String phNo = vendorList.get(i).getPhone();
                                    String address = vendorList.get(i).getAddress();
                                    long venId = vendorList.get(i).getVendorId();
                                    VendorDBInfo.insertVendor(db, vendorName, venStatus, phNo, address, venId, isVeryfiedVend, vendorBalance);
                                    Log.i("ServiceProcessor", " address------------------- = " + address);
                                    if (isCabAvailable) {
                                        for (int j = 0; j < driverList.size(); j++) {
                                            String drvrPhno = driverList.get(j).getDriverPhone();
                                            String drvrName = driverList.get(j).getDriverName();
                                            String category = driverList.get(j).getCategoryName();
                                            String drvrLocation = driverList.get(j).getCurrentLocation();
                                            String cabNumber = driverList.get(j).getCabNumber();
                                            String cabName = driverList.get(j).getCarName();
                                            boolean isVerifiedCab = driverList.get(j).getIsVerifiedCab();
                                            LatLng latLng = Utils.getLatLng(drvrLocation);
                                            int drvrStatus2 = driverList.get(j).getCabStatus();
                                            byte drvrStatus = (byte) drvrStatus2;
                                            long drvrId = driverList.get(j).getDriverId();
                                            long cabCatId = driverList.get(j).getCabCategoryId();
                                            long vendorId = vendorList.get(i).getVendorId();
                                            boolean underMaintenence = driverList.get(j).getMaintenance();
                                            DriverDBInfo.insertCab(db, drvrPhno, drvrName, latLng.latitude, latLng.longitude, drvrStatus, drvrId, underMaintenence, vendorId, cabNumber, category, cabName, cabCatId, isVerifiedCab);
                                        }

                                    } else {

                                    }
//                                    new Thread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            appDatabase.myDao().insertDriver(driverList);
//                                        }
//                                    }).start();

                                }
                                db.setTransactionSuccessful();
                                db.endTransaction();
                                db.close();
                                dbHelper.close();

                                if (taskListener != null) {
                                    Handler handler = new Handler(Looper.getMainLooper());
                                    handler.post(() -> {
                                        taskListener.onSuccess(message);
                                    });
                                }

                            } else {
                                if (taskListener != null) {
                                    Handler handler = new Handler(Looper.getMainLooper());
                                    handler.post(() -> {
                                        taskListener.onFailure(message);
                                    });
                                }
                            }
                        } catch (IndexOutOfBoundsException e) {
                            if (taskListener != null) {
                                Handler handler = new Handler(Looper.getMainLooper());
                                handler.post(() -> {
                                    taskListener.onFailure("Exception Improper Result" + e.toString());
                                });


                            }

                        }

                    }).start();
                }
            }

            @Override
            public void onFailure(Call<ContDetail> call, Throwable t) {
                if (taskListener != null) {
                    taskListener.onFailure("onFailure Improper Result");
                    //
                }
            }
        });


    }

}
