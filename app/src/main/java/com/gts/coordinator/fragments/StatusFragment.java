package com.gts.coordinator.fragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gts.coordinator.Adapter.AdapterStatusFragment;
import com.gts.coordinator.R;
import com.gts.coordinator.dao.Driver;
import com.gts.coordinator.db.DriverDBInfo;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatusFragment extends Fragment  {
    private RecyclerView recyclerView;
    private ArrayList<Driver> vendorArrayList;
    private DriverDBInfo vendorDBInfo;
    private AdapterStatusFragment adapterStatusFragment;
  //  protected TabFragment mapUpdateListener;
    private long v_id;
    private byte status;
    private SearchView searchView;
    public StatusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status, container, false);
        recyclerView =view.findViewById(R.id.rv_stetus);
        searchView=view.findViewById(R.id.search_fragment);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        vendorArrayList = new ArrayList<>();
        vendorDBInfo =new DriverDBInfo(getActivity());
        vendorArrayList = vendorDBInfo.getDrivers();
        adapterStatusFragment = new AdapterStatusFragment(vendorArrayList,getActivity());
        recyclerView.setAdapter(adapterStatusFragment);
        searchView.setQueryHint("Search Here");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.isEmpty()||s.length()==0){
                    vendorArrayList = vendorDBInfo.getDrivers();
                }else {

                }
                adapterStatusFragment.getFilter().filter(s);
                return false;
            }
        });

        return view;
    }

}
