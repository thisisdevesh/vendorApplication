package com.gts.coordinator.Activity;

import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLngBounds;
import com.gts.coordinator.Adapter.AdepterStetusCabActivity;
import com.gts.coordinator.R;
import com.gts.coordinator.dao.Driver;
import com.gts.coordinator.db.DriverDBInfo;

import java.util.ArrayList;
// todo crete by ravinder 31-05-2019
public class StetusCabActivity extends AppCompatActivity {
    private RecyclerView recyclerViewl;
    private long vendorId;
    private byte status;
    TextView v_name;
    private String name;
    private AdepterStetusCabActivity adepterStetusCabActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stetus_cab);
        recyclerViewl = findViewById(R.id.id_rev_stetus_cab);
        v_name =findViewById(R.id.id_vendor_name);
        recyclerViewl.setHasFixedSize(true);
        recyclerViewl.setLayoutManager(new LinearLayoutManager(this));
        Bundle bundle =getIntent().getExtras();
        if (bundle!=null){
            vendorId = bundle.getLong("vid",0);
            status = bundle.getByte("sta");
            name =bundle.getString("name","");
            Log.e("", "onCreate: "+status );
        }
        v_name.setText("Vendor Name :- "+name);
        new GetDriversDetails().execute();
    }

    public void onBackPrese(View view) {
        onBackPressed();
    }

    public  class GetDriversDetails extends AsyncTask<Void, Void, LatLngBounds> {
        //        public boolean isProcessRunning;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("rss","GetDriversDetails:onPreExecute()");


          //  mClusterManager.clearItems();
//            map.clear();
          //  processBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected LatLngBounds doInBackground(Void... voids) {

            return null;
        }
     @Override
     protected void onPostExecute(LatLngBounds bounds) {
         super.onPostExecute(bounds);
         ArrayList<Driver> driverList = DriverDBInfo.getDrivers(getApplicationContext(), vendorId, status);
         if (driverList.size()>0 ) {
//                LatLngBounds.Builder builder = LatLngBounds.builder();
             for (Driver driver : driverList) {
                 //  LatLng position = new LatLng(driver.getLat(), driver.getLng());
                 // builder.include(position);
                 adepterStetusCabActivity = new AdepterStetusCabActivity(driverList, getApplicationContext());
                 recyclerViewl.setAdapter(adepterStetusCabActivity);
                 //  mClusterManager.addItem(new MarkerItems(position, driver.getName(), driver.getCabNo(), driver.getPhoneNo(), driver.getStatus()));
             }
         }/*else {
             Utils.showOkAlert(StetusCabActivity.this,"Error","Empty Driver List",false);
             //Toast.makeText(StetusCabActivity.this, "Empty List", Toast.LENGTH_SHORT).show();
             Handler handler =new Handler();
             handler.postDelayed(new Runnable() {
                 @Override
                 public void run() {
                     finish();
                 }
             },2000);
             // finish();
         }*/
         //   loadData();
         Log.i("rss","GetDriversDetails:onPostExecute()");
         //  setCameraPosition(bounds);
         //  processBar.setVisibility(View.GONE);
     }
        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.i("rss","GetDriversDetails:doInBackground(): got driver list");
          //  processBar.setVisibility(View.GONE);
        }
    }

}
