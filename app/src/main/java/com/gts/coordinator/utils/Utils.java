package com.gts.coordinator.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.MutableLiveData;

import android.text.InputFilter;
import android.text.Spanned;
import android.util.Base64;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.model.LatLng;
import com.gts.coordinator.Model.ContractorData.activeDriver.ActiveDriveDataResponse;
import com.gts.coordinator.Model.ContractorData.activeDriver.DriverDatum;
import com.gts.coordinator.Model.ContractorData.activeDriver.PostActiveDriver;
import com.gts.coordinator.Retrofit.RetrofitApiInterface;
import com.gts.coordinator.Retrofit.RetrofitClient;
import com.gts.coordinator.fragments.MessageDialogFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.os.Build.VERSION.RELEASE;
import static android.os.Build.VERSION.SDK;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES;

/**
 * Created by GTS Developer on 02-Jan-2017 @ 12:03
 */

public class Utils {

  public static int TYPE_WIFI = 1;
  public static int TYPE_MOBILE = 2;
  public static int TYPE_NOT_CONNECTED = 0;
  public Context context;
  public static int getConnectivityStatus(Context context) {
    ConnectivityManager cm = (ConnectivityManager) context
            .getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
    if (null != activeNetwork)
    {
      if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
        return TYPE_WIFI;
      if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
        return TYPE_MOBILE;
      else
        return TYPE_WIFI;
    }
    return TYPE_NOT_CONNECTED;
  }

  public static void showOkAlert(Context context, String title, String message, boolean isCancellable) {
    try {
      MessageDialogFragment msg = MessageDialogFragment.newInstance(title, message, isCancellable);
      msg.show((Activity) context);
    }catch (Exception e){}

  }
//  public static void showToast()

  public static void showOkAlert(Context context, int layout, int viewClose, boolean isCancellable) {
    MessageDialogFragment msg = MessageDialogFragment.newInstance(layout, viewClose, isCancellable);
    msg.show((Activity) context);
  }
  private String blockCharacterSet = "#@()-_;?=,.'~#^$+/|$%&*!:\"";
  public InputFilter filter = (source, start, end, dest, dstart, dend) -> {

    if (source != null && blockCharacterSet.contains(("" + source))) {
      return "";
    }
    return null;
  };
  public static int getAppVersionCode(Context context) {
    try {
      PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
      return packageInfo.versionCode;
    } catch (PackageManager.NameNotFoundException e) {
      Log.e("VersionInfo", "Error in getting Version code", e);
      return -1;
    }
  }

  public static String getAppVersionName(Context context) {
    try {
      PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
      return packageInfo.versionName;
    } catch (PackageManager.NameNotFoundException e) {
      Log.e("VersionInfo", "Error in getting Version name", e);
      return null;
    }
  }

  public static String getOSVersionName(){
    StringBuilder osName = new StringBuilder();
    if ( SDK_INT==VERSION_CODES.BASE )
      osName.append("Alpha ")  ;
    else if ( SDK_INT==VERSION_CODES.BASE_1_1)
      osName.append("Beta ")  ;
    else if ( SDK_INT==VERSION_CODES.CUPCAKE)
      osName.append("Cupcake ")  ;
    else if ( SDK_INT==VERSION_CODES.DONUT)
      osName.append("Donut ")  ;
    else if ( SDK_INT==VERSION_CODES.ECLAIR || SDK_INT==VERSION_CODES.ECLAIR_0_1 || SDK_INT==VERSION_CODES.ECLAIR_MR1 )
      osName.append("Eclair ")  ;
    else if ( SDK_INT==VERSION_CODES.FROYO)
      osName.append("Froyo ")  ;
    else if ( SDK_INT==VERSION_CODES.GINGERBREAD || SDK_INT==VERSION_CODES.GINGERBREAD_MR1 )
      osName.append("Gingerbread ")  ;
    else if ( SDK_INT==VERSION_CODES.HONEYCOMB || SDK_INT==VERSION_CODES.HONEYCOMB_MR1 || SDK_INT==VERSION_CODES.HONEYCOMB_MR2 )
      osName.append("Honeycomb ")  ;
    else if ( SDK_INT==VERSION_CODES.ICE_CREAM_SANDWICH || SDK_INT==VERSION_CODES.ICE_CREAM_SANDWICH_MR1 )
      osName.append("Ice Cream Sandwitch ")  ;
    else if ( SDK_INT==VERSION_CODES.JELLY_BEAN || SDK_INT==VERSION_CODES.JELLY_BEAN_MR1 || SDK_INT==VERSION_CODES.JELLY_BEAN_MR2 )
      osName.append("Jelly Bean ")  ;
    else if ( SDK_INT==VERSION_CODES.KITKAT || SDK_INT==VERSION_CODES.KITKAT_WATCH)
      osName.append("KitKat ")  ;
    else if ( SDK_INT==VERSION_CODES.LOLLIPOP || SDK_INT==VERSION_CODES.LOLLIPOP_MR1 )
      osName.append("Lollipop ")  ;
    else if (SDK_INT==VERSION_CODES.M)
      osName.append("Marshmallow ")  ;
    else if (SDK_INT==VERSION_CODES.N)
      osName.append("Nougat ")  ;
    else if (SDK_INT==VERSION_CODES.O)
      osName.append("Oreo");
    else if (SDK_INT==VERSION_CODES.P)
      osName.append("Pie");
      osName.append("[").append(RELEASE).append("]");
    return osName.toString();
  }

  public static String getDeviceName(){
    StringBuilder devName = new StringBuilder(Build.BRAND);
    devName.append(":").append(Build.MODEL);
    return devName.toString();
  }

  public static boolean checkPermission(int result){
    if (result == PackageManager.PERMISSION_GRANTED )
      return true;
    return false;
  }

  public static LatLng getLatLng(String strLocation)
  {
    if (strLocation==null || "".equals(strLocation) || "null".equals(strLocation))  //!strLocation.contains(",")
      return new LatLng(1000d,1000d);
    String[] strLatLng = strLocation.split(",");
    double lat = Double.parseDouble(strLatLng[0].trim());
    double lng = Double.parseDouble(strLatLng[1].trim());
//    Log.i("MapViewFragment","Utils ********* =  getLatLng()"+"  = lat"+lat+" = Lng"+lng);
    return new LatLng(lat,lng);
  }

  public static void callContact(Context context ,String phNo) {
    try {
      Intent callIntent = new Intent(Intent.ACTION_CALL);
      callIntent.setData(Uri.parse("tel:"+phNo));
      if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
        return;
      }
      context.startActivity(callIntent);
    }catch (Exception e){
      e.printStackTrace();
    }

  }



  public static void clearUploadDir(Context context){
    File uploadDir = context.getDir("upload_docs",Context.MODE_PRIVATE) ;
    if(uploadDir.exists()) {
//      Log.i("Utils", "List of files:-");
      for (File file : uploadDir.listFiles()) {
//        Log.i("Utils", "files:file deleting=" + file);
        file.delete();
      }
    }
  }

  public static void callApiFreeAndReplace(String cabNo, String bookingId, long contId, int bookingRqT){
    RetrofitApiInterface apiInterface = RetrofitClient.getClient().create(RetrofitApiInterface.class);
    Call<ActiveDriveDataResponse> call = apiInterface.getDriverDetail(new PostActiveDriver(contId,bookingId,"",bookingRqT));
    call.enqueue(new Callback<ActiveDriveDataResponse>() {
      @Override
      public void onResponse(Call<ActiveDriveDataResponse> call, Response<ActiveDriveDataResponse> response) {
        if (response.isSuccessful()){
          if (response.body().getStatus()==0){
            if (response.body().getDriverData()!=null){
            //  activeDriverList.setValue(response.body().getDriverData());
            }
          }
        }else {}
      }

      @Override
      public void onFailure(Call<ActiveDriveDataResponse> call, Throwable t) {

      }
    });

  }


}
