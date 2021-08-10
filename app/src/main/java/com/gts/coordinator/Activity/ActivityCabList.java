package com.gts.coordinator.Activity;

import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.gts.coordinator.Activity.From.AddCabFrom;
import com.gts.coordinator.Adapter.CabListAdapter;
import com.gts.coordinator.R;
import com.gts.coordinator.dao.Driver;
import com.gts.coordinator.db.DriverDBInfo;

import java.util.ArrayList;
//todo create by ravindra 27.05.2019 ok testing
public class ActivityCabList extends AppCompatActivity {
    private DriverDBInfo driverDBInfo;
    private CabListAdapter cabListAdapter;
    private RecyclerView viewRecyclerView;
    private FloatingActionButton addElement;
    private TextView text_item_notfound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cab_list);
        text_item_notfound = findViewById(R.id.text_item_notfound);
        Toolbar toolbar =  findViewById(R.id.toolbar_cab_list);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cab List");
        addElement = findViewById(R.id.add_element_cab_list);
        driverDBInfo = new DriverDBInfo(ActivityCabList.this);
        viewRecyclerView =  findViewById(R.id.view_activity_recycler_cab_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        viewRecyclerView.setLayoutManager(linearLayoutManager);
        getCabs();
    }
    private void getCabs() {
        ArrayList<Driver> driverList = driverDBInfo.getAllDriverData();
        cabListAdapter = new CabListAdapter(ActivityCabList.this, driverList,text_item_notfound);
        viewRecyclerView.setAdapter(cabListAdapter);
        cabListAdapter.notifyDataSetChanged();
        addElement.setOnClickListener(view -> addcabdata());
    }
    private void addcabdata() {
        Intent intent = new Intent(ActivityCabList.this, AddCabFrom.class);
        intent.putExtra("page_type", "ADD");
        startActivity(intent);
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
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
             cabListAdapter.getFilter().filter(newText);
              return false;
            }
        });
        return true;
    }
    @Override
    protected void onStart() {
        getCabs();
        super.onStart();
    }
}