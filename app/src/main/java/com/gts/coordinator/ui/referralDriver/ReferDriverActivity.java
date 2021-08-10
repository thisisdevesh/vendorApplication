package com.gts.coordinator.ui.referralDriver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gts.coordinator.Model.referralDriver.Drivercablist;
import com.gts.coordinator.Model.referralDriver.ReferralDriverRespose;
import com.gts.coordinator.R;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.List;

public class ReferDriverActivity extends AppCompatActivity {
  private RecyclerView recyclerView;
  ReferDriverAdepter referDriverAdepter;
  ReferDriverViewModel referDriverViewModel;

  ProgressBar progressBar;
  TextView itemNotFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_driver);
        recyclerView =findViewById(R.id.recycler_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progressBar);
        itemNotFound = findViewById(R.id.item_not_found);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Refer Driver");
        startProgressBar();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
     //   referDriverAdepter = new ReferDriverAdepter(new ArrayList<>());
      //  recyclerView.setAdapter(referDriverAdepter);
        referDriverViewModel = ViewModelProviders.of(this).get(ReferDriverViewModel.class);
        referDriverViewModel.getDriverList(1).observe(this, referralDriverRespose -> {
            String tt = referralDriverRespose.getGetresponse().getMessage();
            Log.e("rss",tt);
            stopProgressBar();
            if (referralDriverRespose.getDrivercablist().isEmpty()){
                itemNotFound.setVisibility(View.VISIBLE);
            }else {itemNotFound.setVisibility(View.GONE);}
           // list = referralDriverRespose.getDrivercablist();
            referDriverAdepter = new ReferDriverAdepter(referralDriverRespose.getDrivercablist(),ReferDriverActivity.this);
            recyclerView.setAdapter(referDriverAdepter);
        });

       referDriverViewModel.getStatusMessage().observe(this, new Observer<String>() {
           @Override
           public void onChanged(String s) {
               if (s!=null){
                   stopProgressBar();
                   TastyToast.makeText(ReferDriverActivity.this,s,TastyToast.LENGTH_SHORT,TastyToast.INFO);
               }
           }
       });

    }
    public void startProgressBar(){
        if (progressBar.getVisibility()== View.GONE)
            progressBar.setVisibility(View.VISIBLE);

    }
 public void stopProgressBar(){
     if (progressBar.getVisibility()== View.VISIBLE)
         progressBar.setVisibility(View.GONE);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
