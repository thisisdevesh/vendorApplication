package com.gts.coordinator.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.gts.coordinator.Adapter.CategoryListAdepter;
import com.gts.coordinator.Model.ContractorData.CategoryReport.CategoryList;
import com.gts.coordinator.R;
import java.util.ArrayList;
public class SelfServiceCatReport extends Fragment {

    private RecyclerView selfServCatRecyler;
    private TextView selfservNoRecord;
    private CategoryListAdepter testAdepter;
    private ArrayList<CategoryList> categoryLists;
    //  private ArrayList<PkgAttemptDetails> selfServiceCatDaos;
    boolean onCreateChecked;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_self_service_cat_report, container, false);
        onCreateChecked = true;
        selfServCatRecyler = (RecyclerView) view.findViewById(R.id.self_serv_cat_recycler);
        selfservNoRecord = (TextView) view.findViewById(R.id.self_serv_cat_noRecords);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        selfServCatRecyler.setLayoutManager(layoutManager);
        selfServCatRecyler.setItemAnimator(new DefaultItemAnimator());

        if (categoryLists == null || categoryLists.size() == 0) {
            selfservNoRecord.setText("No Records Found");
        } else {
            selfservNoRecord.setVisibility(View.GONE);
            testAdepter = new CategoryListAdepter(categoryLists, getActivity());
            selfServCatRecyler.setAdapter(testAdepter);
        }
        return view;
    }

    public void getSelfServiceCatReport(ArrayList<CategoryList> categoryLists) {
        if (categoryLists != null) {
            this.categoryLists = categoryLists;
            if (onCreateChecked) {
                selfservNoRecord.setVisibility(View.GONE);
                testAdepter = new CategoryListAdepter(categoryLists, getActivity());
                selfServCatRecyler.setAdapter(testAdepter);
            }
        }
    }

}

