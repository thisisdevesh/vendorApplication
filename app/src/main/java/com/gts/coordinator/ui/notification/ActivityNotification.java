package com.gts.coordinator.ui.notification;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.gts.coordinator.Activity.ActivityDashboard;
import com.gts.coordinator.Notification.Model.NotificationList;
import com.gts.coordinator.R;

import com.gts.coordinator.utils.Utils;

public class ActivityNotification extends AppCompatActivity implements NotificationAdapter.event {
    private RecyclerView recyclerView;
    private NotificationAdapter mAdapter;
    ProgressBar progressBar;
    boolean fromService;
    private int stetus;
    NotificationDataSource lisner;
    private ShimmerFrameLayout shimmer_container;
    RecyclerView.LayoutManager mLayoutManager;
    private int cccc = 20;
    Handler handler;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        fromService = intent.getBooleanExtra("notificationServ", false);
        setContentView(R.layout.fragment_notification);
        handler = new Handler();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            stetus = bundle.getInt("stetus");
        }
        Log.e("Stetus", "rss");
        Toolbar toolbar = findViewById(R.id.toolbar_no);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("ActivityNotification");
        recyclerView = findViewById(R.id.notification_recycler);
        progressBar = findViewById(R.id.process_bar);
        shimmer_container = findViewById(R.id.shimmer_view_container);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        lisner = new NotificationDataSource(ActivityNotification.this);
        callApiGetNotificationList();


/*
        recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {

            }
        });
*/
    }


    private void callApiGetNotificationList() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (shimmer_container.getVisibility() == View.VISIBLE) {
                    shimmer_container.setVisibility(View.GONE);
                    TextView itemnf= findViewById(R.id.empty_list);
                    itemnf.setText(getString(R.string.item_not_found));

                }

            }
        },10000);
        final NotificationViewModel notificationViewModel = ViewModelProviders.of(this).get(NotificationViewModel.class);
        mAdapter = new NotificationAdapter(this, ActivityNotification.this);
        shimmer_container.startShimmer();
        notificationViewModel.itemPagedList.observe(this, (PagedList<NotificationList> items) -> {


            mAdapter.submitList(items);
        });
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int childCount = mLayoutManager.getChildCount();
                int itemCont = mLayoutManager.getItemCount();
                if (Utils.getConnectivityStatus(getApplicationContext()) == 0) {
                    Utils.showOkAlert(ActivityNotification.this, "No Internet Connection", "Please check your internet connection", false);
                }
                if (itemCont == cccc){
                   progressBar.setVisibility(View.VISIBLE);
/*
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
*/
                    cccc = cccc*2;
                }else {
                    progressBar.setVisibility(View.GONE);
                }


                  //  Toast.makeText(ActivityNotification.this, "childCount :-" + String.valueOf(dx) + "\n itemCont" + String.valueOf(dy), Toast.LENGTH_SHORT).show();


            }
        });

    }

   /* public void showData(int a){

    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (stetus == 1) {
                    startActivity(new Intent(ActivityNotification.this, ActivityDashboard.class));
                } else {
                    onBackPressed();
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Utils.getConnectivityStatus(getApplicationContext()) == 0)
            Utils.showOkAlert(ActivityNotification.this, "No Internet Connection", "Please check your internet connection", false);
    }

    @Override
    public void onBackPressed() {
        if (stetus == 1) {
            startActivity(new Intent(ActivityNotification.this, ActivityDashboard.class));
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public void hideShimmer() {
        if (shimmer_container.getVisibility() == View.VISIBLE) {
            shimmer_container.setVisibility(View.GONE);
        }
    }

    /*@Override
    public void stetusPrograsBar(int a) {
        if (a==0)
        Toast.makeText(this, "stetusPrograsBar", Toast.LENGTH_SHORT).show();
    }*/

//    @Override
//    public void updateAvailableBalance(double amount) {
//        Toast.makeText(this, "stetusPrograsBar", Toast.LENGTH_SHORT).show();
//        Log.e("stetusPrograsBar", "" + amount);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // Toast.makeText(this, "onDestroy Notification", Toast.LENGTH_SHORT).show();
    }

}