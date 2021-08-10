package com.gts.coordinator.Adapter;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gts.coordinator.Activity.CabDetailActivity;
import com.gts.coordinator.Model.getAll.Drivervendorlist;
import com.gts.coordinator.R;
import com.gts.coordinator.dao.Driver;
import com.gts.coordinator.utils.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//todo ok report edit by Ravinder 20.05.2019
public class DriverListAdapter extends RecyclerView.Adapter<DriverListAdapter.MyViewHolder> implements Filterable {
    private Context activity;
    private ArrayList<Drivervendorlist> driverList;
    private ArrayList<Drivervendorlist> driverListAll;
    private static int CALLING_PERMISSION = 23;

    public DriverListAdapter(Context context, ArrayList<Drivervendorlist> driverList) {
        this.activity = context;
        this.driverList = driverList;
        driverListAll = new ArrayList<>(driverList);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.driver_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Drivervendorlist driver = driverList.get(position);
        final String phNO = driver.getDriverNo();
        holder.driverName.setText(driver.getDriverName());
        holder.phoneNo.setText(driver.getDriverNo());
        holder.cabNumber.setText(driver.getCabNo());
        String ss = driver.getCabStatus();
        // holder.category.setText(driver.getCategory());
        holder.cabName.setText(driver.getCabType());
//        if (driver.isVerified()) {
//            holder.tvVerifiedStatus.setVisibility(View.GONE);
//        }
        if (ss.equalsIgnoreCase("Logout")) {
            ///  Drawable image = activity.getResources().getDrawable(R.drawable.call_white);
            //  holder.callingImage.setImageDrawable(image);
            holder.drivercalling.setBackground(activity.getResources().getDrawable(R.drawable.ic_circle_black));
            //   holder.driverName.setTextColor(activity.getResources().getColor(R.color.black));
        } else if (ss.equalsIgnoreCase("Free")) {
            holder.drivercalling.setBackground(activity.getResources().getDrawable(R.drawable.ic_circle_green));
            holder.driverName.setTextColor(activity.getResources().getColor(R.color.black));
        } else if (ss.equalsIgnoreCase("Busy")) {
            holder.drivercalling.setBackground(activity.getResources().getDrawable(R.drawable.ic_circle_read));
            holder.driverName.setTextColor(activity.getResources().getColor(R.color.black));
        } else if (driver.getMaintenance() == 1) {
            holder.drivercalling.setBackground(activity.getResources().getDrawable(R.drawable.ic_circle_deactivate));
            holder.driverName.setTextColor(activity.getResources().getColor(R.color.black));
        }
// updateDriver

        holder.drivercalling.setOnClickListener(v -> {
//         Log.i("DriverListFragment"," *********ONclick phoneNO = "+phNO);
            calling((Activity) activity, phNO);
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
//    public void progressBar(ProgressDialog pd){
//        this. pds =pd;
//    }


    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Drivervendorlist> filteredList = new ArrayList<>();
            //List<Driver> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(driverListAll);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Drivervendorlist item : driverListAll) {

                    if (item.getDriverName().toLowerCase().contains(filterPattern) || item.getDriverNo().toLowerCase().contains(filterPattern) ||
                            item.getCabNo().toLowerCase().contains(filterPattern)||item.getVendorNo().toLowerCase().equals(filterPattern)) {
                        filteredList.add(item);
                    } else {
                        Toast.makeText(activity, "Item Not Found", Toast.LENGTH_SHORT).show();
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
            try {
                driverList.clear();
                driverList.addAll((List) results.values);
                if (((List) results.values).size() == 0) {
                    Utils.showOkAlert(activity, activity.getString(R.string.error), "Item not Found Driver List", false);
                }
                notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView phoneNo, driverName, cabNumber, category, cabName, tvVerifiedStatus;
        public ImageView callingImage;
        RelativeLayout drivercalling;

        public MyViewHolder(View itemView) {
            super(itemView);
            driverName = itemView.findViewById(R.id.driver_name);
            phoneNo = itemView.findViewById(R.id.phoneNO);
            tvVerifiedStatus = itemView.findViewById(R.id.verified_status);
            cabNumber = itemView.findViewById(R.id.cabNumber);
            cabName = itemView.findViewById(R.id.cabName);
            callingImage = itemView.findViewById(R.id.driverCallImg);
            drivercalling = itemView.findViewById(R.id.drvCallingLay);

        }
    }

    public static void calling(Activity context, String phone) {
        if (Utils.checkPermission(ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE))) {
            Utils.callContact(context, phone);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.CALL_PHONE)) {
                Utils.showOkAlert(context, null, "User has to provide permission to call", true);
            }
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CALL_PHONE}, CALLING_PERMISSION);
        }
    }

}
