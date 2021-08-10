package com.gts.coordinator.Activity;

import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.gts.coordinator.Activity.From.AddVendorForm;
import com.gts.coordinator.Adapter.CabListAdapter;
import com.gts.coordinator.Adapter.VendorListAdapterViewE;
import com.gts.coordinator.R;
import com.gts.coordinator.dao.Vendor;
import com.gts.coordinator.db.VendorDBInfo;
import com.gts.coordinator.service.DriverDataService;

import java.util.ArrayList;

//todo create by ravindra 27.05.2019 ok testing
public class VendorListActivity extends AppCompatActivity {
    private VendorDBInfo vendorDBInfo;
    private RecyclerView viewRecyclerView;
    private VendorListAdapterViewE vendorListAdapter;
    private FloatingActionButton addElement;
    private SearchView searchView;
    private TextView text_item_notfound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_list);
        text_item_notfound = findViewById(R.id.text_item_notfound);
        Toolbar toolbar =  findViewById(R.id.toolbar_vendor_list);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Vendor List");
        addElement = findViewById(R.id.add_element_vendor_list);
        vendorDBInfo = new VendorDBInfo(VendorListActivity.this);
        viewRecyclerView =  findViewById(R.id.view_activity_recycler_vendor_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        viewRecyclerView.setLayoutManager(linearLayoutManager);
        getVendorList();
      //  vendorListAdapter.itemNotFound();
    }
    private void getVendorList() {
        ArrayList<Vendor> vendorNameList = vendorDBInfo.getAllVendors(VendorListActivity.this);
        vendorListAdapter = new VendorListAdapterViewE(vendorNameList, VendorListActivity.this,text_item_notfound);
        viewRecyclerView.setAdapter(vendorListAdapter);
        addElement.setOnClickListener(view -> addVendorData());
    }
    private void addVendorData() {
        Intent intent = new Intent(VendorListActivity.this, AddVendorForm.class);
        intent.putExtra("page_type", "ADD"); // not being used probably (in AddVendorForm)
        intent.putExtra("isVerified", "");
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent mIntent = new Intent(VendorListActivity.this, DriverDataService.class);
        DriverDataService.enqueueWork(this, mIntent);
        getVendorList();
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