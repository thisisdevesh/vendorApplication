package com.gts.coordinator.ui.referralDriver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.gts.coordinator.Model.referralDriverNew.Drivercablistverify;
import com.gts.coordinator.R;
import java.util.List;

public class NewReferDriverAdepter extends RecyclerView.Adapter<NewReferDriverAdepter.ReferDriverViewHolder> {
    private List<Drivercablistverify> drivercablists;
    private Context context;

    public NewReferDriverAdepter(List<Drivercablistverify> drivercablists, Context context) {
        this.drivercablists = drivercablists;
        this.context = context;
    }


    @NonNull
    @Override
    public ReferDriverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_refer_new_driver, parent, false);
        return new ReferDriverViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReferDriverViewHolder holder, int position) {
        Drivercablistverify list = drivercablists.get(position);
//        if (list!=null){
//            if (holder.progressBar.getVisibility()==View.VISIBLE){
//                holder.progressBar.setVisibility(View.GONE);
//            }


   //     }
        holder.cabNo.setText(list.getCabNumber());
        holder.driverName.setText(list.getDriverName());
        holder.driverPhone.setText(list.getDriverPhone());
        holder.status.setText(list.getVerifiedstatus());

    }

    @Override
    public int getItemCount() {
        return drivercablists.size();
    }

    class ReferDriverViewHolder extends RecyclerView.ViewHolder {
        TextView driverName, driverPhone, status, cabNo;
        ProgressBar progressBar;

        public ReferDriverViewHolder(@NonNull View itemView) {
            super(itemView);
            driverName = itemView.findViewById(R.id.driverName);
            cabNo = itemView.findViewById(R.id.cabNumber);
            status = itemView.findViewById(R.id.booking_counter);
            driverPhone = itemView.findViewById(R.id.driverNumber);

            //  recyclerView = itemView.findViewById(R.id.recycler_view);
        }
    }
}
