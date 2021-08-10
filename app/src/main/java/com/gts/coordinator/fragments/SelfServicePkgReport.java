package com.gts.coordinator.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gts.coordinator.Activity.ReportActivity;
import com.gts.coordinator.Adapter.SelfServPkgAdapter;
import com.gts.coordinator.R;
import com.gts.coordinator.dao.SelfServicePkgDao.PkgAttemptDetails;
import com.gts.coordinator.dao.SelfServicePkgDao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class SelfServicePkgReport extends Fragment {
    private RecyclerView selfServPkgRecyler;
    private SelfServPkgAdapter selfServPkgAdapter;
    private TextView selfservNoRecord;
    //  private ArrayList<PkgAttemptDetails> selfServicePkgDaos;
    private ArrayList<SelfServicePkgDao> selfServicePkgDaos;
    boolean onCreateChecked;
    Calendar prevTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_self_service_report, container, false);
        onCreateChecked = true;
        selfServPkgRecyler = view.findViewById(R.id.self_pkg_recycler);
        selfservNoRecord = view.findViewById(R.id.self_serv_pkg_noRecords);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        selfServPkgRecyler.setLayoutManager(layoutManager);
        selfServPkgRecyler.setItemAnimator(new DefaultItemAnimator());
        if (selfServicePkgDaos == null || selfServicePkgDaos.size() == 0) {
            selfservNoRecord.setText("No Records Found");
        } else {
            selfservNoRecord.setVisibility(View.GONE);
            selfServPkgAdapter = new SelfServPkgAdapter(selfServicePkgDaos, getActivity());
            selfServPkgRecyler.setAdapter(selfServPkgAdapter);
        }
        return view;
    }

    //  public void getSelfServicepkgReport(ArrayList<PkgAttemptDetails> selfServicePkgDaos)
    public void getSelfServicepkgReport(ArrayList<SelfServicePkgDao> selfServicePkgDaos) {
        if (selfServicePkgDaos != null) {
            this.selfServicePkgDaos = selfServicePkgDaos;
            if (onCreateChecked) {
                selfservNoRecord.setVisibility(View.GONE);
                selfServPkgAdapter = new SelfServPkgAdapter(selfServicePkgDaos, getActivity());
                selfServPkgRecyler.setAdapter(selfServPkgAdapter);
            }
        }

    }


}
