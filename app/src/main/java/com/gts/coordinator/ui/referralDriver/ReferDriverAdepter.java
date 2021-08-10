package com.gts.coordinator.ui.referralDriver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.gts.coordinator.Model.referralDriver.Drivercablist;
import com.gts.coordinator.R;
import com.sdsmdg.tastytoast.TastyToast;
import java.util.List;

public class ReferDriverAdepter extends RecyclerView.Adapter<ReferDriverAdepter.ReferDriverViewHolder> {
    private List<Drivercablist> drivercablists;
    private Context context;
    private NewReferDriverViewModel viewModel;
    private NewReferDriverAdepter driverAdepter;

    public ReferDriverAdepter(List<Drivercablist> drivercablists, Context context) {
        this.drivercablists = drivercablists;
        this.context = context;
    }


    @NonNull
    @Override
    public ReferDriverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_refer_driver, parent, false);
        return new ReferDriverViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReferDriverViewHolder holder, int position) {
        Drivercablist list = drivercablists.get(position);
        bind(holder, list);

    }

    private void bind(@NonNull ReferDriverViewHolder holder, Drivercablist list) {
        holder.cabNo.setText(list.getCabNumber());
        holder.driverName.setText(list.getDriverName());
        holder.noOfRefer.setText(list.getTotalcount());

        holder.lay_rv.setOnClickListener(view -> {
           //  notifyDataSetChanged();
            if (holder.rev_lay.getVisibility() == View.GONE) {
                holder.rev_lay.setVisibility(View.VISIBLE);
            } else {
                holder.rev_lay.setVisibility(View.GONE);
            }
            holder.recyclerView.setHasFixedSize(true);
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
            viewModel = ViewModelProviders.of((FragmentActivity) context).get(NewReferDriverViewModel.class);
            viewModel.getApiResponse(list.getDriverPhone()).observe((LifecycleOwner) context, respose -> {
                if (holder.progressBar.getVisibility()==View.VISIBLE)
                holder.progressBar.setVisibility(View.GONE);
                driverAdepter = new NewReferDriverAdepter(respose.getDrivercablistverify(), context);
                holder.recyclerView.setAdapter(driverAdepter);

            });
            viewModel.getStatusMessage().observe((LifecycleOwner) context, s -> {
                if (s != null) {
                    TastyToast.makeText(context, s, TastyToast.LENGTH_SHORT, TastyToast.INFO).show();
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return drivercablists.size();
    }

    class ReferDriverViewHolder extends RecyclerView.ViewHolder {
        TextView driverName, driverPhone, noOfRefer, cabNo;
        RecyclerView recyclerView;
        //lay_rv
        LinearLayout lay_rv;
        RelativeLayout rev_lay;
        ProgressBar progressBar;


        public ReferDriverViewHolder(@NonNull View itemView) {
            super(itemView);
            driverName = itemView.findViewById(R.id.driverName);
            cabNo = itemView.findViewById(R.id.cabNumber);
            noOfRefer = itemView.findViewById(R.id.booking_counter);
            lay_rv = itemView.findViewById(R.id.lay_rv);
            recyclerView = itemView.findViewById(R.id.recycler_view);
            rev_lay = itemView.findViewById(R.id.recycler_view_lay);
            progressBar = itemView.findViewById(R.id.process_bar);
        }
    }
}
