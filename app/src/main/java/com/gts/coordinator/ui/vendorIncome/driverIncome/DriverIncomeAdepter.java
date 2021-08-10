package com.gts.coordinator.ui.vendorIncome.driverIncome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gts.coordinator.Model.driverIncome.Driverdatum;
import com.gts.coordinator.R;

import java.util.ArrayList;

public class DriverIncomeAdepter extends RecyclerView.Adapter<DriverIncomeAdepter.DriverIncomeViewHolder>{
   ArrayList<Driverdatum> list;
   Context context;

    public DriverIncomeAdepter(ArrayList<Driverdatum> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public DriverIncomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_incom_driver,parent,false);
        return new DriverIncomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DriverIncomeViewHolder holder, int position) {
      holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class DriverIncomeViewHolder extends RecyclerView.ViewHolder{
        TextView driverName,driverIncome,noOfBooking,cabNumber;
        public DriverIncomeViewHolder(@NonNull View itemView) {
            super(itemView);
            driverName = itemView.findViewById(R.id.driver_name);
            driverIncome = itemView.findViewById(R.id.driver_income);
            noOfBooking = itemView.findViewById(R.id.booking_counter);
            cabNumber = itemView.findViewById(R.id.cab_No);
        }

        public void bind(Driverdatum driverdatum) {
            driverName.setText(driverdatum.getDriverName());
            cabNumber.setText(driverdatum.getCabNo());
            driverIncome.setText(String.valueOf(driverdatum.getTotalAmt()));
            noOfBooking.setText(driverdatum.getTotalCount());

        }
    }
}
