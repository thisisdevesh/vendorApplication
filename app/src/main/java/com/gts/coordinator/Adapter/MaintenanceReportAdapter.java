package com.gts.coordinator.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.gts.coordinator.R;
import com.gts.coordinator.dao.MaintenanceReportDao;

import java.util.ArrayList;

public class MaintenanceReportAdapter extends RecyclerView.Adapter<MaintenanceReportAdapter.MaintenanceViewHolder> {
        ArrayList<MaintenanceReportDao> maintenanceListVal;
        private MaintenanceDateTimeAdapter maintenanceDateTimeAdapter;
        private Context context;

        public MaintenanceReportAdapter(ArrayList<MaintenanceReportDao> maintenanceListVal,Context context) {
            this.maintenanceListVal = maintenanceListVal;
            this.context=context;

        }

        @Override
        public MaintenanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.maintenace_report_holder_view, parent, false);
            return new MaintenanceViewHolder(view);
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public void onBindViewHolder(MaintenanceViewHolder holder, int position) {

            MaintenanceReportDao maintenanceReportDao = maintenanceListVal.get(position);
//          Log.i("MaintenanceReportAdapter","  OUT date ^^^^^^^^^^^ is ="+ maintenanceReportDao.getOutTime()+"  "+maintenanceReportDao.getOutDate());
            ArrayList<MaintenanceReportDao> timeDateLiist = new ArrayList<>();
            timeDateLiist.add(maintenanceReportDao);
            String outTime = maintenanceReportDao.getOutTime();
            String inTime = maintenanceReportDao.getInTime();
            String inDate = maintenanceReportDao.getInDate();
            String outDate = maintenanceReportDao.getOutDate();
            holder.date_in.setText(inDate);
            holder.date_out.setText(outDate);
            holder.time_in.setText(inTime);
            holder.time_out.setText(outTime);
            holder.total_man_time.setText(context.getString(R.string.total_maintenance_time)+"\t"+maintenanceReportDao.getTotalMaintenanceTime());


         /*   if (inTime.equals("null")) {
                inTime = "";
            } else if (outTime.equals("null")) {
                outTime = "";
            }

            if (position > 0) {
                MaintenanceReportDao previousMaintenanceReport = maintenanceListVal.get(position - 1);
                if (!previousMaintenanceReport.getInDate().equals(maintenanceReportDao.getInDate())) {
                    holder.tvDate.setVisibility(View.VISIBLE);
                    holder.tvDate.setText(maintenanceReportDao.getInDate());
//               maintenanceDateTimeAdapter = new MaintenanceDateTimeAdapter(maintenanceListVal.get(position));
                    maintenanceDateTimeAdapter = new MaintenanceDateTimeAdapter(timeDateLiist);
                    holder.dateRecycler.setAdapter(maintenanceDateTimeAdapter);
                } else {
                    holder.tvDate.setVisibility(View.GONE);
                    maintenanceDateTimeAdapter = new MaintenanceDateTimeAdapter(timeDateLiist);
                    holder.dateRecycler.setAdapter(maintenanceDateTimeAdapter);

                }

            } else {
                holder.tvDate.setVisibility(View.VISIBLE);
                holder.tvDate.setText(maintenanceReportDao.getInDate());
                maintenanceDateTimeAdapter = new MaintenanceDateTimeAdapter(timeDateLiist);
                holder.dateRecycler.setAdapter(maintenanceDateTimeAdapter);
            }*/
        }

        @Override
        public int getItemCount() {
            return maintenanceListVal.size();
        }

        public class MaintenanceViewHolder extends RecyclerView.ViewHolder {
            TextView tvIn, tvDate,tvOut,total_man_time;
            RecyclerView dateRecycler;
            TextView date_in,date_out,time_in,time_out;


            public MaintenanceViewHolder(View itemView) {
                super(itemView);
                date_in = itemView.findViewById(R.id.man_date_in);
                date_out = itemView.findViewById(R.id.man_date_out);
                time_in = itemView.findViewById(R.id.man_time_in);
                time_out = itemView.findViewById(R.id.man_time_out);
                total_man_time = itemView.findViewById(R.id.total_maintenance_time);


              //  tvDate =  itemView.findViewById(R.id.date);
               /* dateRecycler =  itemView.findViewById(R.id.date_recycler);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                dateRecycler.setItemAnimator(new DefaultItemAnimator());
                dateRecycler.setLayoutManager(layoutManager);*/


            }
        }

    }
