package com.gts.coordinator.Activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.gts.coordinator.Activity.From.AddDriverForm;
import com.gts.coordinator.R;

public class RegistationActivity extends AppCompatActivity {

    private CardView addVendor,addCab,addDriver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_registration_form);
        Toolbar toolbar = findViewById(R.id.toolbar_regis);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Registration");
        addCab =findViewById(R.id.addcab);
        addVendor=findViewById(R.id.addVendor);
        addDriver=findViewById(R.id.addDriver);
        addCab.setOnClickListener(v -> {
            Intent cabIntent = new Intent(getApplicationContext(), ActivityCabList.class);
            startActivity(cabIntent);
        });
        addVendor.setOnClickListener(v -> {
            Intent vendorIntent = new Intent(getApplicationContext(), VendorListActivity.class);
            startActivity(vendorIntent);
        });
        addDriver.setOnClickListener(v -> {
            Intent driverIntent = new Intent(getApplicationContext(), AddDriverForm.class);
            driverIntent.putExtra("key", "DRIVER");
            startActivity(driverIntent);
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
