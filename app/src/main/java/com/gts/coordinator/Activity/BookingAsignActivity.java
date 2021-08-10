package com.gts.coordinator.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.gts.coordinator.Adapter.PageAdepter;
import com.gts.coordinator.R;
import com.gts.coordinator.service.MyBookingService;

public class BookingAsignActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private FloatingActionButton backButton;
    private int pageStetus;
    private static final String TAG = "MyTag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_asign);

        viewPager = findViewById(R.id.viewPager2);
        backButton = findViewById(R.id.back_to_fragment);
        tabLayout = findViewById(R.id.tabView_booking);
        tabLayout.setTabTextColors(Color.GRAY, Color.WHITE); // set the tab text colors for the both states of the tab.
        viewPager.setAdapter(new PageAdepter(this));
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Unassigned Booking");
                        break;
                    case 1:
                        tab.setText("Assigned Booking");
                        break;
                    case 2:
                        tab.setText("Booking Activities");
                        break;
                }
            }
        });
        tabLayoutMediator.attach();
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                //  Toast.makeText(MainActivity.this, "onPageScrolled"+position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
//                BadgeDrawable badgeDrawable = tabLayout.getTabAt(position).getOrCreateBadge();
                //badgeDrawable.setVisible(false);
                pageStetus = position;
                // Toast.makeText(getApplicationContext(), "onPageSelected  "+position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        backButton.setOnClickListener(v -> {
            backPreshActivity();
        });
    }
    private void backPreshActivity() {
        if (pageStetus == 0) {
            onBackPressed();
        } else {
            tabLayout.getTabAt(0).select();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

       // stopService(new Intent(getApplicationContext(), MySimpleService.class));
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent mIntent = new Intent(this, MyBookingService.class);
        MyBookingService.enqueueWork(this, mIntent);
        /*  new Thread(() -> startService(new Intent(getApplicationContext(), MyBookingService.class))).start();*/
        Log.e(TAG, "onResumed: MYBookingService Start" );
    }

    @Override
    protected void onPause() {
        super.onPause();
 /*       new Thread(() -> stopService(new Intent(getApplicationContext(), MySimpleService.class))).start();*/
       stopService(new Intent(getApplicationContext(), MyBookingService.class));
        Log.e(TAG, "onDestroy: MYBookingService Stop" );
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

}
