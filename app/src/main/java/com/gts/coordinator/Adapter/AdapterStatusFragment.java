package com.gts.coordinator.Adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gts.coordinator.utils.itemNoteFound;
import android.widget.Toast;

import com.gts.coordinator.R;
import com.gts.coordinator.dao.Driver;
import com.gts.coordinator.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class AdapterStatusFragment extends RecyclerView.Adapter<AdapterStatusFragment.StatusViewHolder> implements Filterable {
    private List<Driver> vendorArrayList;
    private List<Driver> vendorArrayListFull;
    private static int CALLING_PERMISSION = 23;
    private Context context;
  //  public TabFragment mapUpdateListener;
    private itemNoteFound itemNoteFound;
    public AdapterStatusFragment(ArrayList<Driver> vendorArrayList, Context context) {
        this.vendorArrayList = vendorArrayList;
        this.context = context;
        this.itemNoteFound =(itemNoteFound)context;
       // this.mapUpdateListener =mapUpdateListener;
        vendorArrayListFull = new ArrayList<>(vendorArrayList);
    }
    @NonNull
    @Override
    public StatusViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.driver_stetus,viewGroup,false);
        return new StatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusViewHolder holder, int i) {
        Driver li = vendorArrayList.get(i);
        holder.cab_name.setText(li.getCabName());
        holder.contact.setText(li.getPhoneNo());
        holder.cab_no.setText(li.getCabNo());
        holder.name.setText(li.getName());
       final String phNo =li.getPhoneNo();
       final long vendor_id =li.getVendorId();
       final byte stetus =li.getStatus();
        if(li.isVerified()){
            holder.tvVerifiedStatus.setVisibility(View.GONE);
        }
        if (li.getStatus() == 4)
        {
            holder.driverColor.setBackground(context.getResources().getDrawable(R.drawable.ic_circle_black));
        }
        else if (li.getStatus()==1)
        {
            holder.driverColor.setBackground(context.getResources().getDrawable(R.drawable.ic_circle_green));
        }
        else if (li.getStatus()==2)
        {
            holder.driverColor.setBackground(context.getResources().getDrawable(R.drawable.ic_circle_read));

        }
        holder.driverColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calling((Activity) context, phNo);
            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    mapUpdateListener.update(-1, stetus);
            }
        });
    }

    @Override
    public int getItemCount() {
        return vendorArrayList.size();
    }

    @Override
    public Filter getFilter() {

       //return filter calls object
        return searchList;
    }

    // create filter calls object
    private Filter searchList =new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
         //create list
            List<Driver> driverList =new ArrayList<>();

            if (constraint==null||constraint.length()==0){
                driverList.addAll(vendorArrayListFull);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Driver driver: vendorArrayListFull){
                    if (driver.getPhoneNo().toLowerCase().contains(filterPattern)||driver.getCabNo().toLowerCase().contains(filterPattern)||driver.getName().toLowerCase().contains(filterPattern)){
                        driverList.add(driver);
                    }
//                    else {
//                       // Toast.makeText(context, "Item Not Found", Toast.LENGTH_SHORT).show();
//                        itemNoteFound.stetus(1);
//                    }
                }
            }
            if (driverList.size()==0){
                itemNoteFound.stetus(1);
            }else if (driverList.size()!=0) {
                itemNoteFound.stetus(0);
            }

            FilterResults results =new FilterResults();
            results.values =driverList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
           vendorArrayList.clear();
           vendorArrayList.addAll((List) results.values);
            if (((List) results.values).size()==0){
                Toast.makeText(context, "Item not Found", Toast.LENGTH_SHORT).show();
            }

            notifyDataSetChanged();

        }
    };


    public class StatusViewHolder extends RecyclerView.ViewHolder{
        TextView name,contact,cab_no,cab_name,tvVerifiedStatus;
        LinearLayout driverColor;
        ImageView imageView;
        public StatusViewHolder(@NonNull View itemView) {
            super(itemView);
            name =itemView.findViewById(R.id.vendor_name_stetus);
            contact=itemView.findViewById(R.id.phone_number_st);
            cab_no =itemView.findViewById(R.id.cabNumber_st);
            cab_name=itemView.findViewById(R.id.cabName_st);
            driverColor=itemView.findViewById(R.id.lay_background);
            tvVerifiedStatus=itemView.findViewById(R.id.verified_status);
            imageView =itemView.findViewById(R.id.view_map);

        }
    }
    private  void calling(Activity context,String phone) {
        if(Utils.checkPermission( ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) )){
            Utils.callContact(context,phone);
        }else {
            //    requestCallingPermission();
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.CALL_PHONE)) {
                Utils.showOkAlert(context,null,"User has to provide permission to call", true);
            }
            //And finally ask for the permission
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CALL_PHONE}, CALLING_PERMISSION);
//      Log.i("DrawerActivity", " ActivityCompat.requestPermissions");
        }
    }

}
