package com.gts.coordinator.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gts.coordinator.BroadcastReceiver.MyApplication;
import com.gts.coordinator.Model.ContractorData.CabCategory.CabCategoryModel;
import com.gts.coordinator.Model.ContractorData.CabCategory.Category;
import com.gts.coordinator.Model.ContractorData.CabCategory.ModelList;
import com.gts.coordinator.R;
import com.gts.coordinator.Retrofit.RetrofitApiInterface;
import com.gts.coordinator.Retrofit.RetrofitClientWcm;
import com.gts.coordinator.callback.TaskListener;
import com.gts.coordinator.db.CabCategoryDBInfo;
import com.gts.coordinator.db.CabModelDbInfo;
import com.gts.coordinator.db.CoordinatorDbHelper;
import com.gts.coordinator.utils.PreferenceData;
import com.gts.coordinator.utils.Utils;
import com.gts.coordinator.service.ServiceProcessor;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    private ImageView imageView;
    private CabCategoryDBInfo cabCategoryDbInfo;
    private CabModelDbInfo cabModelDbInfo;
    private TextView stetus;
    private AlertDialog dialog;
    private String message;
    private SplashActivity splashActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        stetus = findViewById(R.id.id_stetus);
        imageView = findViewById(R.id.image_view);

        PreferenceData.saveLong(this,"mvid",-1);
        PreferenceData.saveInt(this,"msts",1);
        Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        imageView.startAnimation(animFadeIn);
        cabCategoryDbInfo = new CabCategoryDBInfo(this);
        cabModelDbInfo = new CabModelDbInfo(this);

         ConnectivityManager connectivityManager =(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo!=null&& networkInfo.isConnected()){
            changeTextStatus(true);
        }else {
            changeTextStatus(false);
        }
        if (Utils.getConnectivityStatus(getApplicationContext()) == 0) {
            message ="Please check your internet connection";
         //   showAlertDialog(message);
           // Utils.showOkAlert(SplashActivity.this, getString(R.string.info), "Please check your internet connection", false);
        }else {
          //  removeDbInfo();
            startMyService();
            getCabCategoryData();

        }

    }

    private void startMyService() {
        ServiceProcessor.downloadCoordinatorData(SplashActivity.this, PreferenceData.getLong(this, "cont_id"), new TaskListener<String>() {
            @Override
            public void onStart() {
                //  startProcessing();
            }

            @Override
            public void onSuccess(String message) {

                Intent intent = new Intent(SplashActivity.this, ActivityDashboard.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(String message) {
               // message ="Please Try Again";
              //  showAlertDialog(message);
                TastyToast.makeText(getApplicationContext(), " Server is not responding ", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                Intent intent = new Intent(SplashActivity.this, ActivityDashboard.class);
                startActivity(intent);
                finish();
                //  Utils.showOkAlert(SplashActivity.this, getString(R.string.info), message, true);
            }
        });

    }

    private void getCabCategoryData() {
        RetrofitApiInterface apiInterface = RetrofitClientWcm.getClient().create(RetrofitApiInterface.class);
        Call<CabCategoryModel> call = apiInterface.cabCategorydetal();
        call.enqueue(new Callback<CabCategoryModel>() {
            @Override
            public void onResponse(Call<CabCategoryModel> call, Response<CabCategoryModel> response) {
                if (response.isSuccessful()) {
                    try {
                        CabCategoryModel cabCategoryModel = response.body();
                        int stetus = cabCategoryModel.getD().getResponse().getStatus();
                        //   String mess = (String) cabCategoryModel.getD().getResponse().getMessage();
                        if (stetus == 0) {
                            runOnUiThread(() -> {
                                CoordinatorDbHelper dbHelper = new CoordinatorDbHelper(SplashActivity.this);
                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                CabCategoryDBInfo.deleteAllCabCategory(db);
                                CabModelDbInfo.deleteAllCabModels(db);
                                db.close();
                                dbHelper.close();
                                List<Category> list = new ArrayList<>();
                                list = cabCategoryModel.getD().getCategories();
                                for (int i = 0; i < list.size(); i++) {
                                    List<ModelList> modelLists = new ArrayList<>();
                                    modelLists = list.get(i).getModelList();
                                    String categoryCode = list.get(i).getCatId();
                                    String categoryName = list.get(i).getCatName();
                                    long categoryId = (long) list.get(i).getId();
                                    cabCategoryDbInfo.insertCabCategory(categoryName, categoryCode, categoryId);
                                    for (int j = 0; j < modelLists.size(); j++) {
                                        String modelName = modelLists.get(j).getModel();
                                        long modelId = modelLists.get(j).getModelId();
                                        cabModelDbInfo.insertCabModel(modelName, modelId, categoryId);
                                    }
                                }

                            });

                        } else {
                         //   Utils.showOkAlert(SplashActivity.this, getString(R.string.error), "Error In CabCategory List" + "\n ", true);
                          //  showLayout();
                        }
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onFailure(Call<CabCategoryModel> call, Throwable t) {
                Toast.makeText(SplashActivity.this, "onFailure CabCategoryModel", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void removeDbInfo() {


       // DriverDBInfo.deleteAllDrivers(db);
      //  VendorDBInfo.deleteAllVendors(db);



        Toast.makeText(splashActivity, "DataBase Remove", Toast.LENGTH_SHORT).show();
    }
    public void changeTextStatus(boolean isConnected){
        if (isConnected){
        }else {
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MyApplication.activityPaused();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.activityResumed();
    }
}

/*


*/
