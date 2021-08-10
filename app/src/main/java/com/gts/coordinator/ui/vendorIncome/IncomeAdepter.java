package com.gts.coordinator.ui.vendorIncome;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gts.coordinator.Model.driverIncome.DriverIncomeRespose;
import com.gts.coordinator.Model.driverIncome.Driverdatum;
import com.gts.coordinator.Model.income.Vendordatum;
import com.gts.coordinator.R;
import com.gts.coordinator.dao.Driver;
import com.gts.coordinator.ui.vendorIncome.driverIncome.DriverIncomeAdepter;
import com.gts.coordinator.ui.vendorIncome.driverIncome.DriverIncomeViewModel;
import com.gts.coordinator.utils.Utils;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.List;


public class IncomeAdepter extends RecyclerView.Adapter<IncomeAdepter.IncomeViewHolder> implements Filterable {
     ArrayList<Vendordatum> list;
     ArrayList<Vendordatum> listAll;
     Context context;
     DriverIncomeViewModel viewModel;
     DriverIncomeAdepter driverIncomeAdepter;
     String fromdateData,todateData;

    public IncomeAdepter(ArrayList<Vendordatum> list, String fromdateData, String todateData, Context context) {
        this.list = list;
        this.context = context;
        this.fromdateData=fromdateData;
        this.todateData=todateData;
        listAll = new ArrayList<>(list);
    }

    @NonNull
    @Override
    public IncomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_incom,parent,false);
        return new IncomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeViewHolder holder, int position) {
                Vendordatum vv = list.get(position);

//                    if (vv.getTotalincome()<=0){
//                    holder.incomeCard.setVisibility(View.GONE);
//                    }


        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    class IncomeViewHolder extends RecyclerView.ViewHolder{
        TextView vendorName,vendorIncome,noOfBooking,itemNotFound;
        RelativeLayout layoutItemView,recyclerViewLay;
        CardView incomeCard;
        RecyclerView recyclerView;
        ProgressBar progressBar;
        public IncomeViewHolder(@NonNull View itemView) {
            super(itemView);
            vendorName = itemView.findViewById(R.id.vendor_name);
            vendorIncome = itemView.findViewById(R.id.ven_income);
            incomeCard = itemView.findViewById(R.id.income_card);
            itemNotFound = itemView.findViewById(R.id.item_not_found);
            noOfBooking = itemView.findViewById(R.id.booking_counter);
            layoutItemView = itemView.findViewById(R.id.lay_item_view);
            recyclerView = itemView.findViewById(R.id.recycler_view);
            progressBar = itemView.findViewById(R.id.process_bar);
            recyclerViewLay = itemView.findViewById(R.id.recycler_view_lay);
        }

        public void bind(Vendordatum vendordatum) {
//            if (vendordatum.getTotalincome()<=0){
//                incomeCard.setVisibility(View.GONE);
//            }
            vendorName.setText(vendordatum.getVendorname());
            vendorIncome.setText(String.valueOf(vendordatum.getTotalincome()));
            noOfBooking.setText(vendordatum.getTotalbooking());
            layoutItemView.setOnClickListener(view -> {
               // previousPo = getAdapterPosition();
                if (recyclerViewLay.getVisibility()==View.GONE) {
                    recyclerViewLay.setVisibility(View.VISIBLE);

                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    viewModel = new ViewModelProvider((FragmentActivity) context).get(DriverIncomeViewModel.class);
                    viewModel.getIncomeRespose(vendordatum.getVid(),fromdateData,todateData).observe((LifecycleOwner) context, driverIncomeRespose -> {
                        //"2020/01/01","2020/01/29"
                        if (progressBar.getVisibility()==View.GONE)
                            progressBar.setVisibility(View.VISIBLE);
                        if (driverIncomeRespose.getGetresponse().getStatus()==0){
                            if (driverIncomeRespose.getDriverdata().isEmpty()){
                                if (itemNotFound.getVisibility()==View.GONE){
                                    itemNotFound.setVisibility(View.VISIBLE);
                                    itemNotFound.setGravity(Gravity.CENTER);
                                    recyclerView.setVisibility(View.GONE);
                                    if (progressBar.getVisibility()==View.VISIBLE)
                                        progressBar.setVisibility(View.GONE);
                                }
                            }else {
                                if (progressBar.getVisibility()==View.VISIBLE)
                                    progressBar.setVisibility(View.GONE);
                                driverIncomeAdepter = new DriverIncomeAdepter((ArrayList<Driverdatum>) driverIncomeRespose.getDriverdata(),context);
                                recyclerView.setAdapter(driverIncomeAdepter);
                            }
                        }
                    });
                    viewModel.getErrorMessage().observe((LifecycleOwner) context, s -> {
                        if (s!=null){
                            TastyToast.makeText(context,s,TastyToast.LENGTH_SHORT,TastyToast.INFO).show();
                            if (progressBar.getVisibility()==View.VISIBLE)
                                progressBar.setVisibility(View.GONE);
                        }
                    });

                }
                else
                    recyclerViewLay.setVisibility(View.GONE);
            });

        }

    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Vendordatum> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(listAll);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Vendordatum item : listAll) {
                    if (item.getVendorname().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }else {
                        //  Toast.makeText(activity, "Item Not Found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }
        //rss
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List) results.values);
            if (((List) results.values).size()==0){
//                if (select_list_type!=1){
//                    Utils.showOkAlert(activity,activity.getString(R.string.error),"Item not Found Driver List",false);
//                }
            }
            notifyDataSetChanged();
        }
    };

}
