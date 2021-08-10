package com.gts.coordinator.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gts.coordinator.Model.ContractorData.assignDriver.DriverDatum;
import com.gts.coordinator.R;

import java.util.List;

public class AdapterAssignedDriver extends RecyclerView.Adapter<AdapterAssignedDriver.AssignedDriverViewHolder> {
    private List<DriverDatum> driverData;
    private Context mContext;

    public AdapterAssignedDriver(List<DriverDatum> driverData, Context mContext) {
        this.driverData = driverData;
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public AssignedDriverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_asign_driver, parent, false);
        return new AssignedDriverViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignedDriverViewHolder holder, int position) {
        DriverDatum list = driverData.get(position);
        holder.dateTime.setText(list.getRideTime());
        holder.formLocation.setText(list.getFromLocation());
        holder.toLocation.setText(list.getToLocation());
        holder.packageType.setText(list.getPackageType());


    }

    @Override
    public int getItemCount() {
        return driverData.size();
    }

    public class AssignedDriverViewHolder extends RecyclerView.ViewHolder {
        TextView formLocation,toLocation,dateTime,packageType;
        public AssignedDriverViewHolder(@NonNull View itemView) {
            super(itemView);
            formLocation = itemView.findViewById(R.id.location_from);
            toLocation = itemView.findViewById(R.id.location_to);
            dateTime = itemView.findViewById(R.id.date_and_time);
            packageType = itemView.findViewById(R.id.package_type);

        }
    }
}
