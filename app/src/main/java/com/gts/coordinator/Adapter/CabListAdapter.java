package com.gts.coordinator.Adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gts.coordinator.Activity.From.AddCabFrom;
import com.gts.coordinator.R;
import com.gts.coordinator.dao.Driver;
import com.gts.coordinator.fragments.UpdateDriver;
import com.gts.coordinator.utils.Utils;

import java.util.ArrayList;
import java.util.List;

//todo ok report edit by Ravinder 20.05.2019
public class CabListAdapter extends RecyclerView.Adapter<CabListAdapter.MyViewHolder> implements Filterable {
    private Context activity;
    private TextView text_item_notfound;
    private List<Driver> driverList;
    private List<Driver> driverListAll;
    private static int CALLING_PERMISSION = 23;
    public CabListAdapter(Context context, ArrayList<Driver> driverList, TextView text_item_notfound) {
        this.activity = context;
        this.driverList = driverList;
        this.text_item_notfound = text_item_notfound;
        driverListAll = new ArrayList<>(driverList);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.update_cab_row, parent, false);
        LinearLayout linearLayout =  view.findViewById(R.id.card_linear);
        linearLayout.setOnClickListener(v -> {
        });
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Driver driver = driverList.get(position);
        final String phNO = driver.getPhoneNo();

        holder.cabNumber.setText(driver.getCabNo());
        holder.category.setText(driver.getCategory());
        holder.cabName.setText(driver.getCabName());
        if (driver.isVerified()) {
            holder.tvVerifiedStatus.setVisibility(View.GONE);
            /*holder.tvVerifiedStatus.setText(R.string.verified);
            holder.tvVerifiedStatus.setTextColor(Color.BLUE);*/
        }

        if (driver.getStatus() == 4) {
            holder.driverColor.setBackground(activity.getResources().getDrawable(R.drawable.ic_circle_black));
            holder.driverName.setTextColor(activity.getResources().getColor(R.color.black));
        } else if (driver.getStatus() == 1) {
            holder.driverColor.setBackground(activity.getResources().getDrawable(R.drawable.ic_circle_green));
            holder.driverName.setTextColor(activity.getResources().getColor(R.color.black));
        } else if (driver.getStatus() == 2) {
            holder.driverColor.setBackground(activity.getResources().getDrawable(R.drawable.ic_circle_read));
            holder.driverName.setTextColor(activity.getResources().getColor(R.color.black));
        } else {
            holder.driverColor.setBackground(activity.getResources().getDrawable(R.drawable.ic_circle_black));
            holder.driverName.setTextColor(activity.getResources().getColor(R.color.black));
        }
        holder.updateCab.setOnClickListener(view -> {
            Intent intent = new Intent(activity, AddCabFrom.class);
            intent.putExtra("page_type", "EDIT"); //EDITCAB
            intent.putExtra("cabNumber", driver.getCabNo());
            intent.putExtra("vcabId", driver.getDriverId());
            intent.putExtra("earlierVenID", driver.getVendorId());
            intent.putExtra("isVerified", driver.isVerified());
            intent.putExtra("status", driver.getStatus());
            activity.startActivity(intent);
        });

        Log.d("Drivernumber",String.valueOf(driver.getPhoneNo()));

        if (driver.getName().equals("")){
            holder.driverName.setText("No Driver");
            holder.driverName.setTextColor(Color.RED);
        }else{
            holder.driverName.setText(driver.getName());
        }

        if (driver.getPhoneNo().equals("")){
            holder.phoneNo.setVisibility(View.GONE);
        }else{
            holder.phoneNo.setText(driver.getPhoneNo());


            //todo: update driver info not required
        /*    holder.phoneNo.setOnClickListener(view -> {
                UpdateDriver updateDriver = UpdateDriver.newInstance(driver.getName(), driver.getDriverId(), driver.getVendorId(), driver.getPhoneNo(), driver.isVerified());
                updateDriver.show((Activity) activity);
            });*/
        }

        holder.driverColor.setOnClickListener(v -> {
            try {
                calling((Activity) activity, phNO);
            }catch (Exception e){}
        });
    }

    @Override
    public int getItemCount() {
        return driverList.size();
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
            List<Driver> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(driverListAll);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Driver item : driverListAll) {
                    if (item.getCabNo().toLowerCase().contains(filterPattern)
                            || item.getName().toLowerCase().contains(filterPattern) ||
                            item.getPhoneNo().toLowerCase().contains(filterPattern)||item.getCabName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    } else {
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
            driverList.clear();
            driverList.addAll((List) results.values);
            if (((List) results.values).size() == 0) {
                text_item_notfound.setVisibility(View.VISIBLE);
            } else {
                text_item_notfound.setVisibility(View.GONE);
            }
            notifyDataSetChanged();
        }
    };
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView phoneNo, driverName, cabNumber, category, cabName, tvVerifiedStatus;
        public ImageView callingImage;
        public Button updateCab, updateDriver;
        public LinearLayout driverColor, drivercalling;

        public MyViewHolder(View itemView) {
            super(itemView);
            driverName = itemView.findViewById(R.id.image_view22);
            phoneNo = itemView.findViewById(R.id.phoneNO);
            tvVerifiedStatus = itemView.findViewById(R.id.verified_status);
            driverColor = itemView.findViewById(R.id.driverColor);
            cabNumber = itemView.findViewById(R.id.cabNumber);
            cabName = itemView.findViewById(R.id.cabName);
            category = itemView.findViewById(R.id.cabCategory);
            callingImage = itemView.findViewById(R.id.driverCallImg);
            drivercalling = itemView.findViewById(R.id.drvCallingLay);
            updateCab = itemView.findViewById(R.id.update_cab);
            updateDriver = itemView.findViewById(R.id.update_driver);
        }
    }
    public static void calling(Activity context, String phone) {
        try {
            if (Utils.checkPermission(ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE))) {

                Utils.callContact(context, phone);

            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.CALL_PHONE)) {
                    Utils.showOkAlert(context, null, "User has to provide permission to call", true);
                }
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CALL_PHONE}, CALLING_PERMISSION);
            }
        }catch (Exception e){}
    }

}
