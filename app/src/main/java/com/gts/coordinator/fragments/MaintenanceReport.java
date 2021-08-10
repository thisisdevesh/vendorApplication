package com.gts.coordinator.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gts.coordinator.Activity.ReportActivity;
import com.gts.coordinator.Adapter.MaintenanceDateTimeAdapter;
import com.gts.coordinator.Adapter.MaintenanceReportAdapter;
import com.gts.coordinator.R;
import com.gts.coordinator.dao.MaintenanceReportDao;

import java.util.ArrayList;


public class MaintenanceReport extends Fragment  {
    RecyclerView maintenanceReportRecycler;
    MaintenanceReportAdapter maintenanceReportAdapter;
    ArrayList<MaintenanceReportDao> maintenanceReportDaosList;
    private TextView selfServNoRecord;
    boolean maintenanceChecked;
  //  MaintenanceDateTimeAdapter maintenanceDateTimeAdapter;

    public MaintenanceReport() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container , Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_maintenance_report, container, false);
        maintenanceReportRecycler =  view.findViewById(R.id.maintenace_recycler);
        selfServNoRecord =  view.findViewById(R.id.maintenace_noRecords);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        maintenanceReportRecycler.setItemAnimator(new DefaultItemAnimator());
        maintenanceReportRecycler.setLayoutManager(layoutManager);
        maintenanceChecked = true;
        if (maintenanceReportDaosList == null || maintenanceReportDaosList.size() == 0) {
            selfServNoRecord.setText("No Records Found");
        } else {
            maintenanceReportAdapter = new MaintenanceReportAdapter(maintenanceReportDaosList,getActivity());
            maintenanceReportRecycler.setAdapter(maintenanceReportAdapter);
        }
        return view;
    }


    public void getMaintenaceReport(ArrayList<MaintenanceReportDao> maintenanceReportDaosList) {
        this.maintenanceReportDaosList = maintenanceReportDaosList;
        if (maintenanceChecked) {
            if (maintenanceReportDaosList.size() == 0) {
                selfServNoRecord.setVisibility(View.VISIBLE);
                selfServNoRecord.setText(" No Records Found ");
            } else {
                selfServNoRecord.setVisibility(View.GONE);
            }

            maintenanceReportAdapter = new MaintenanceReportAdapter(maintenanceReportDaosList,getActivity());
            maintenanceReportRecycler.setAdapter(maintenanceReportAdapter);


        }
    }




}
