package com.gts.coordinator.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;

import com.gts.coordinator.Adapter.VendorListAdapter;
import com.gts.coordinator.Adapter.VendorListAdapterViewE;
import com.gts.coordinator.R;
import com.gts.coordinator.dao.Vendor;
import com.gts.coordinator.db.VendorDBInfo;
import com.gts.coordinator.utils.PreferenceData;
import com.gts.coordinator.utils.Utils;

import java.util.ArrayList;

public class VendorDetailActivity extends AppCompatActivity {
    public VendorListAdapter vendorListAdapter;
  //  public ArrayList<Vendor> vendorList;
    private ProgressDialog progressDialog;
    private SearchView searchView;
    //   int stetus;
    public RecyclerView recyclerView;
    private VendorDBInfo vendorDBInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_detail);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Vendor List");
        recyclerView = findViewById(R.id.recycler_view_vendor2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vendorDBInfo = new VendorDBInfo(VendorDetailActivity.this);
        progressDialog = new ProgressDialog(this);

        getVendorList();
    }
    private void getVendorList() {
        ArrayList<Vendor> vendorNameList = vendorDBInfo.getAllVendors(VendorDetailActivity.this);
        vendorListAdapter =  new VendorListAdapter(vendorNameList, VendorDetailActivity.this,null);;
        recyclerView.setAdapter(vendorListAdapter);
      //  addElement.setOnClickListener(view -> addVendorData());
        vendorListAdapter.setOnItemClickListener(new VendorListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, ImageView free_map) {
                    long  vendorId2 = vendorNameList.get(position).getVndId();
                    byte  status2 = -1;//vendorList.get(position).getStatus();
                    Intent intent = new Intent(VendorDetailActivity.this, ActivityDashboard.class);
                    PreferenceData.saveLong(VendorDetailActivity.this,"mvid",vendorId2);
                    PreferenceData.saveInt(VendorDetailActivity.this,"msts",status2);
                    intent.putExtra("ss",1);
                    startActivity(intent);
                    finish();
                    //   (Activity)context.finesh();

                    ///
                    //  mapUpdateListener.setVendorInfo(vendorId2,status2);

            }
        });
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search2);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setIconified(false);
        searchView.setFocusable(true);
        searchView.setQueryHint("Enter Vendor Name");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //  Toast.makeText(VendorListActivity.this, "onQueryTextSubmit", Toast.LENGTH_SHORT).show();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    vendorListAdapter.getFilter().filter(newText);
                }catch (Exception e){}
                return false;
            }
        });
        return true;
    }
}
