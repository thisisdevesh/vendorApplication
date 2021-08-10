package com.gts.coordinator.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.gts.coordinator.R;
import com.gts.coordinator.dao.MaintenanceReportDao;

import java.util.ArrayList;

public class MaintenanceDateTimeAdapter extends RecyclerView.Adapter<MaintenanceDateTimeAdapter.MaintenanceInOutViewHolder> {
        ArrayList<MaintenanceReportDao> maintenanceReportDaos;

        public MaintenanceDateTimeAdapter(ArrayList<MaintenanceReportDao> maintenanceReportDaos) {
            this.maintenanceReportDaos = maintenanceReportDaos;
        }

        @Override
        public MaintenanceInOutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.maintenance_report_date_time_view, parent, false);
            return new MaintenanceInOutViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MaintenanceInOutViewHolder holder, int position) {
            MaintenanceReportDao maintenanceReportDao = maintenanceReportDaos.get(position);
            String inTime = maintenanceReportDao.getInDate() + " " + maintenanceReportDao.getInTime();
            String outTime = maintenanceReportDao.getOutDate() + " " + maintenanceReportDao.getOutTime();
            if (maintenanceReportDao.getInDate().equals("null") | maintenanceReportDao.getInTime().equals("null")) {
                inTime = "";
            } else if (maintenanceReportDao.getOutDate().equals("null") | maintenanceReportDao.getOutTime().equals("null")) {
                outTime = "";
            }
            holder.inTime.setText(inTime);
            holder.outTime.setText(outTime);
            holder.totalTimeVal.setText(maintenanceReportDao.getTotalMaintenanceTime());
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return maintenanceReportDaos.size();
        }

        public class MaintenanceInOutViewHolder extends RecyclerView.ViewHolder {
            TextView totalTimeVal, inTime, outTime;

            public MaintenanceInOutViewHolder(View itemView) {
                super(itemView);
                totalTimeVal =  itemView.findViewById(R.id.total_time_value);
                inTime =  itemView.findViewById(R.id.in_time);
                outTime =  itemView.findViewById(R.id.out_time);
            }
        }

    }
