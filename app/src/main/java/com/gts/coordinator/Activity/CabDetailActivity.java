package com.gts.coordinator.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;
import com.gts.coordinator.Adapter.DriverListAdapter;
import com.gts.coordinator.Model.getAll.Drivervendorlist;
import com.gts.coordinator.R;
import com.gts.coordinator.roomDB.MyDatabase;
import com.gts.coordinator.service.DriverDataService;

import java.util.ArrayList;

public class CabDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
     DriverListAdapter cabListAdapter;
    public ArrayList<Drivervendorlist> driverList = new ArrayList<>();
    private SearchView searchView;
    String cabStatus = "";
    MyDatabase appDatabase;
    MaterialButton buttonLogin, buttonLogout, buttonBusy, buttonDeactivate, buttonMaintenance, buttonAll;
    TextView itemNotfound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cab_detail);
        Intent intent = new Intent(getApplicationContext(), DriverDataService.class);
        DriverDataService.enqueueWork(this, intent);
        bindView();
        Listener();
        new GetDriversDetails().execute(0);

    }


    private void bindView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cab List");
        recyclerView = findViewById(R.id.recycler_view);
        buttonLogout = findViewById(R.id.logout);
        buttonLogin = findViewById(R.id.loading);
        buttonBusy = findViewById(R.id.busy);
        buttonAll = findViewById(R.id.all);
        buttonMaintenance = findViewById(R.id.maintenance);
        buttonDeactivate = findViewById(R.id.deactivate);
        itemNotfound = findViewById(R.id.item_not_found);

    }

    private void Listener() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        appDatabase = MyDatabase.getInstance(this);
        buttonBusy.setOnClickListener(this);
        buttonDeactivate.setOnClickListener(this);
        buttonLogin.setOnClickListener(this);
        buttonAll.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);
        buttonMaintenance.setOnClickListener(this);
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
                    cabListAdapter.getFilter().filter(newText);
                } catch (Exception e) {
                }
                return false;
            }
        });
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loading:

              //  driverList.clear();
                cabStatus = "Free";
               // cabListAdapter.notifyDataSetChanged();
                if (searchView.getQuery().toString().equals("")){
                }else {
                    searchView.setQuery("",true);
                }
                new GetDriversDetails().execute(1);
                buttonAll.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.uncheck_button_color));
                buttonLogin.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.check_button_color));
                buttonBusy.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.uncheck_button_color));
                buttonLogout.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.uncheck_button_color));
                buttonDeactivate.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.uncheck_button_color));
                buttonMaintenance.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.uncheck_button_color));

                break;
            case R.id.logout:
               // driverList.clear();
                cabStatus = "Logout";
               // cabListAdapter.notifyDataSetChanged();
               // searchView.setQuery("",true);
                if (searchView.getQuery().toString().equals("")){
                }else {
                    searchView.setQuery("",true);
                }
                new GetDriversDetails().execute(1);
                buttonAll.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.uncheck_button_color));
                buttonLogin.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.uncheck_button_color));
                buttonBusy.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.uncheck_button_color));
                buttonLogout.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.check_button_color));
                buttonDeactivate.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.uncheck_button_color));
                buttonMaintenance.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.uncheck_button_color));

               // getCabList(1);
                break;
            case R.id.busy:
               // driverList.clear();
                cabStatus = "Busy";
              //  cabListAdapter.notifyDataSetChanged();
              //  searchView.setQuery("",false);
                if (searchView.getQuery().toString().equals("")){
                }else {
                    searchView.setQuery("",true);
                }
                new GetDriversDetails().execute(1);
                // getCabList(1);
                buttonAll.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.uncheck_button_color));
                buttonLogin.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.uncheck_button_color));
                buttonBusy.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.check_button_color));
                buttonLogout.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.uncheck_button_color));
                buttonDeactivate.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.uncheck_button_color));
                buttonMaintenance.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.uncheck_button_color));

                break;
            case R.id.deactivate:
               // driverList.clear();
               // cabListAdapter.notifyDataSetChanged();
               // searchView.setQuery("",false);

                new GetDriversDetails().execute(2);
                buttonAll.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.uncheck_button_color));
                buttonLogin.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.uncheck_button_color));
                buttonBusy.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.uncheck_button_color));
                buttonLogout.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.uncheck_button_color));
                buttonDeactivate.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.check_button_color));
                buttonMaintenance.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.uncheck_button_color));

               // getCabList(3);
                break;
            case R.id.maintenance:
//                driverList.clear();
//                cabListAdapter.notifyDataSetChanged();
             //   searchView.setQuery("",false);
                new GetDriversDetails().execute(3);
                buttonAll.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.uncheck_button_color));
                buttonLogin.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.uncheck_button_color));
                buttonBusy.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.uncheck_button_color));
                buttonLogout.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.uncheck_button_color));
                buttonDeactivate.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.uncheck_button_color));
                buttonMaintenance.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.check_button_color));
               // getCabList(4);
                break;
            case R.id.all:
              //  driverList.clear();
             ///   cabListAdapter.notifyDataSetChanged();
              //  searchView.setQuery("",false);
                new GetDriversDetails().execute(0);
                buttonAll.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.check_button_color));
                buttonLogin.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.uncheck_button_color));
                buttonBusy.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.uncheck_button_color));
                buttonLogout.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.uncheck_button_color));
                buttonDeactivate.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.uncheck_button_color));
                buttonMaintenance.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.uncheck_button_color));

                //getCabList(0);
                break;
        }
    }

    class GetDriversDetails extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... integers) {

            if (integers[0] == 1) {
                driverList.addAll(appDatabase.myDao().getCabStatus(cabStatus));
            } else if (integers[0] == 0) {
                driverList.addAll(appDatabase.myDao().getAllCab());
            } else if (integers[0] == 2) {
                driverList.addAll(appDatabase.myDao().getDeactivateStatus("1"));
            } else if (integers[0] == 3) {
                driverList.addAll(appDatabase.myDao().getMaintenaceStatus(1));
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            driverList.clear();
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (driverList.isEmpty()){
                itemNotfound.setText(getString(R.string.item_not_found));
            }else {
                itemNotfound.setText("");
                cabListAdapter = new DriverListAdapter(CabDetailActivity.this, driverList);
                recyclerView.setAdapter(cabListAdapter);
                cabListAdapter.notifyDataSetChanged();
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
