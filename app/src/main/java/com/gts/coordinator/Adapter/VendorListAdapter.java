package com.gts.coordinator.Adapter;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gts.coordinator.Activity.ActivityDashboard;
import com.gts.coordinator.Activity.StetusCabActivity;
import com.gts.coordinator.R;
import com.gts.coordinator.dao.Vendor;
import com.gts.coordinator.db.DriverDBInfo;
import com.gts.coordinator.fragments.MapViewFragment;
import com.gts.coordinator.utils.PreferenceData;
import com.gts.coordinator.utils.Utils;

import java.util.ArrayList;
import java.util.List;


// todo create by ravindra
public class VendorListAdapter extends RecyclerView.Adapter<VendorListAdapter.MyVeiwHolder> implements Filterable{
    private List<Vendor> vendorList;
    private LinearLayout linearLayout;
    private List<Vendor> vendorListFull;

    private Context context;
    private byte status;
    public ProgressDialog pds;
    private long vendorId;
    private String v_name;

    private int select_list_type;
    private OnItemClickListener mListener;
    public VendorListAdapter(List<Vendor> vendorList, Activity context, MapViewFragment.OnMapUpdateListener mapUpdateListener) {
        this.vendorList = vendorList;
        this.context = context;
       // this.mapUpdateListener = mapUpdateListener;
        vendorListFull = new ArrayList<>(vendorList);
    }
    public interface OnItemClickListener{
        void onItemClick(int position, ImageView free_map);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
    @Override
    public MyVeiwHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vendor_list_adapter_layout, parent, false);
        linearLayout = (LinearLayout) view.findViewById(R.id.card_linear);
        return new MyVeiwHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(final MyVeiwHolder holder, final int position) {
        final Vendor vdr = vendorList.get(position);
        v_name =vdr.getName();
        holder.vendorName.setText(v_name);
        holder.vendorName.setTextColor(Color.WHITE);
        long vendorBalance = vdr.getVenBal();
        if (vendorBalance > 0) {
            holder.venBalance.setTextColor(context.getResources().getColor(R.color.dark_green));
            holder.venBalance.setText("₹ " + String.valueOf(vendorBalance));
        } else if (vendorBalance < 0) {
            holder.venBalance.setTextColor(context.getResources().getColor(R.color.dark_red));
            holder.venBalance.setText("₹ " + String.valueOf(vendorBalance));
        } else {
            holder.venBalance.setTextColor(context.getResources().getColor(R.color.black));
            holder.venBalance.setText("₹ " + String.valueOf(vendorBalance));
        }
//      holder.phoneNo.setCabNumbers( vdr.getEmail() );
        final String phNo = vdr.getPhno();
        holder.vendorPhno.setText(phNo);
        byte statusVen = vdr.getStatus();
        if (statusVen == 0) {
            Drawable image = context.getResources().getDrawable(R.drawable.call_white);
            holder.callingImage.setImageDrawable(image);
            holder.vndorColor.setBackground(context.getResources().getDrawable(R.drawable.ic_circle_green));
            holder.vendorName.setTextColor(context.getResources().getColor(R.color.black));
        } else {
            Drawable image = context.getResources().getDrawable(R.drawable.call_black);
            holder.callingImage.setImageDrawable(image);
            holder.vndorColor.setBackground(context.getResources().getDrawable(R.drawable.ic_circle_deactivate));
            holder.vendorName.setTextColor(context.getResources().getColor(R.color.black));
        }
        Log.i("VendorListFragment", "onBindViewHolder(): vendor= " + vendorList.get(position));
        final String freeDrivers = String.valueOf(DriverDBInfo.getCabCount(context, vendorList.get(position).getVndId(), (byte) 1));
        final String busyDrivers = String.valueOf(DriverDBInfo.getCabCount(context, vendorList.get(position).getVndId(), (byte) 2));
        final String logoutDrivers = String.valueOf(DriverDBInfo.getCabCount(context, vendorList.get(position).getVndId(), (byte) 3));
        final String maintenanceDrivers = String.valueOf(DriverDBInfo.getCabCount(context, vendorList.get(position).getVndId(), (byte) 5));
        holder.free.setText(freeDrivers);
        holder.busy.setText(busyDrivers);
        holder.logout.setText(logoutDrivers);
        holder.deactivate.setText(maintenanceDrivers);
        if (vdr.isVerified()) {
            holder.tvVerifiedStatus.setVisibility(View.GONE);
        }

        holder.free.setOnClickListener(v -> {
            if (!freeDrivers.equals("0")) {
                status = 1;
                vendorId = vendorList.get(position).getVndId();
                Intent intent = new Intent(context, StetusCabActivity.class);
                intent.putExtra("vid", vendorId);
                intent.putExtra("sta", status);
                intent.putExtra("name",vendorList.get(position).getName());
                context.startActivity(intent);
            }else {
                Utils.showOkAlert(context,"Info","No Driver is Free",false);
            }
        });
        holder.busy.setOnClickListener(v -> {
            if (!busyDrivers.equals("0")) {
                    status = 2;
                    vendorId = vendorList.get(position).getVndId();
                    Intent intent = new Intent(context, StetusCabActivity.class);
                    intent.putExtra("vid", vendorId);
                    intent.putExtra("sta", status);
                    intent.putExtra("name",vendorList.get(position).getName());
                    context.startActivity(intent);

            }else {
                Utils.showOkAlert(context,"Info","No Driver is Busy",false);
            }
        });
        holder.logout.setOnClickListener(v -> {
            if (!logoutDrivers.equals("0")) {
                status = 3;
                vendorId = vendorList.get(position).getVndId();
                Intent intent = new Intent(context, StetusCabActivity.class);
                intent.putExtra("vid", vendorId);
                intent.putExtra("sta", status);
                intent.putExtra("name",vendorList.get(position).getName());
                context.startActivity(intent);
            }else {
                Utils.showOkAlert(context,"Info","No drivers is Logout",false);
            }
        });
        holder.deactivate.setOnClickListener(v -> {
            if (!maintenanceDrivers.equals("0")) {
                status = 5;
//            if (maintenanceDrivers.equals(""))
                vendorId = vendorList.get(position).getVndId();
                Intent intent = new Intent(context, StetusCabActivity.class);
                intent.putExtra("vid", vendorId);
                intent.putExtra("sta", status);
                intent.putExtra("name",vendorList.get(position).getName());
                context.startActivity(intent);
            }else {
                Utils.showOkAlert(context,"Info","Empty Driver List!! No driver under maintenance",false);
            }
        });
        holder.all_drive_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!maintenanceDrivers.equals("0")) {
                    status = -1;
//            if (maintenanceDrivers.equals(""))
                    vendorId = vendorList.get(position).getVndId();
                    Intent intent = new Intent(context, StetusCabActivity.class);
                    intent.putExtra("vid", vendorId);
                    intent.putExtra("sta", status);
                    intent.putExtra("name",vendorList.get(position).getName());
                    context.startActivity(intent);
                }else {
                    Utils.showOkAlert(context,"Error","Empty Driver List",false);
                }
            }
        });
        holder.list_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!maintenanceDrivers.equals("0")||!logoutDrivers.equals("0")||!freeDrivers.equals("0")||!busyDrivers.equals("0")) {
                    status = -1;
//            if (maintenanceDrivers.equals(""))
                    vendorId = vdr.getVndId();
                    Intent intent = new Intent(context, StetusCabActivity.class);
                    intent.putExtra("vid", vendorId);
                    intent.putExtra("sta", status);
                    intent.putExtra("name",vdr.getName());
                    context.startActivity(intent);
                }else {
                    Utils.showOkAlert(context,"Error","Empty Driver List",false);
                }
            }
        });

        holder.vndorColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calling(phNo);
            }
        });
//        holder.free_map.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (holder.free.getText().toString().equals("0")&&holder.busy.getText().toString().equals("0")
//                        &&holder.logout.getText().toString().equals("0")&&holder.deactivate.getText().toString().equals("0")){
//                    Utils.showOkAlert(context,"Error","Empty Driver List",false);
//                }else {
//                    long  vendorId2 = vendorList.get(position).getVndId();
//                    byte  status2 = -1;//vendorList.get(position).getStatus();
//                    Intent intent = new Intent(context, ActivityDashboard.class);
//                    PreferenceData.saveLong(context,"mvid",vendorId2);
//                    PreferenceData.saveInt(context,"msts",status2);
//                    intent.putExtra("ss",1);
//                   // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                  //
////                    intent.putExtra("vid",vendorId2);
////                    intent.putExtra("sts",status2);
//                    context.startActivity(intent);
//                 //   (Activity)context.finesh();
//
//                  ///
//                  //  mapUpdateListener.setVendorInfo(vendorId2,status2);
//                }//937,-1
//            }
//        });
    }
    public void progressBar(ProgressDialog pd){
       this. pds =pd;
    }
    public void showAllData(int st){
        if (st==20){
            this.vendorList.clear();
            vendorList =new ArrayList<>();
            this.vendorList.addAll(vendorListFull);
            Log.e("rss",vendorListFull.toString());
        }
    }
    @Override
    public int getItemCount() {
        return vendorList.size();
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Vendor> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(vendorListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Vendor item : vendorListFull) {
                    if (item.getPhno().toLowerCase().contains(filterPattern)
                            || item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
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
           // pds.cancel();
            vendorList.clear();
            vendorList.addAll((List) results.values);
            if (((List) results.values).size() == 0) {
                if (select_list_type!=0){
                    Utils.showOkAlert(context,context.getString(R.string.error),"Item not Found",false);
                }
            }
            notifyDataSetChanged();
        }
    };
    public void showStetusMessage(int select_list_type) {
       this.select_list_type =select_list_type;
    }
    public class MyVeiwHolder extends RecyclerView.ViewHolder {
        public TextView vendorPhno, vendorName, busy, free, logout, deactivate, tvVerifiedStatus, venBalance;
        public TextView v_name, v_contect, v_cab_no, v_cab_name;
        public LinearLayout vendorCalling,all_drive_list;
        RelativeLayout vndorColor;
        public ImageView callingImage,free_map,list_all;
        public MyVeiwHolder(View itemView,final OnItemClickListener listener) {
            super(itemView);
            v_name = itemView.findViewById(R.id.vendor_name_stetus);
            v_contect = itemView.findViewById(R.id.phone_number_st);
            v_cab_name = itemView.findViewById(R.id.cabName_st);
            v_cab_no = itemView.findViewById(R.id.cabNumber_st);
            vendorName =  itemView.findViewById(R.id.vendor_name_ll);
            vendorPhno =  itemView.findViewById(R.id.text_vdr_phoneNum);
            tvVerifiedStatus =  itemView.findViewById(R.id.verified_status_vv);
            free =  itemView.findViewById(R.id.free);
            busy =  itemView.findViewById(R.id.busy);
            logout =  itemView.findViewById(R.id.logout);
            deactivate =  itemView.findViewById(R.id.deactivate);
            free_map =  itemView.findViewById(R.id.free_map);
            list_all =  itemView.findViewById(R.id.list_all);
            vndorColor = itemView.findViewById(R.id.vendorColor);
            vendorCalling =  itemView.findViewById(R.id.vdrCallingLay);
            callingImage =  itemView.findViewById(R.id.vendorCallImg);
            venBalance =  itemView.findViewById(R.id.ven_balance);
            all_drive_list = itemView.findViewById(R.id.gfd);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener !=null){
                        int position = getAdapterPosition();
                        if (position !=RecyclerView.NO_POSITION){
                            listener.onItemClick(position,free_map);
                        }
                    }
                }
            });

        }
    }

    public void calling(String phone) {
        if (Utils.checkPermission(ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE))) {
            Utils.callContact(context, phone);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CALL_PHONE)) {
                Utils.showOkAlert(context, null, "User has to provide permission to call", true);
            }

        }
    }
}
//318