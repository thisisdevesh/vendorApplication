package com.gts.coordinator.Adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.gts.coordinator.Model.ContractorData.BookingActivities.BookingActivitiesList;
import com.gts.coordinator.R;
import com.gts.coordinator.utils.Utils;

import java.util.List;

public class BookingActivitiesListAdapter extends RecyclerView.Adapter<BookingActivitiesListAdapter.BookingActivitiesViewHolder> {

    private List<BookingActivitiesList> bookingLists;
    private LayoutInflater mInflater;
    private Context mContext;
    private static int CALLING_PERMISSION = 23;

    public BookingActivitiesListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext =context;
    }

    public void setBookingData(List<BookingActivitiesList> bookingActivitiesList) {
        bookingLists = bookingActivitiesList;
        Log.d("BookingActivitiesList", String.valueOf(bookingLists.size()));
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookingActivitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = mInflater.inflate(R.layout.booking_activities_list_items, parent, false);
        return new BookingActivitiesListAdapter.BookingActivitiesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingActivitiesViewHolder holder, int position) {

        BookingActivitiesList li = bookingLists.get(position);

        if (li.getPlanType().equalsIgnoreCase("Both Way")) {
            if (holder.numberOfDay.getVisibility() == View.GONE) {
                holder.numberOfDay.setVisibility(View.VISIBLE);
                holder.numberOfDay.setText(String.valueOf(li.getBookingDays()));
            }
        } else {
            if (holder.numberOfDay.getVisibility() == View.VISIBLE)
                holder.numberOfDay.setVisibility(View.GONE);
        }
        holder.packageName.setText(li.getPlanType());
        holder.cabType.setText(li.getCabType());
        holder.dateTime.setText(li.getFromDatetime());
        holder.formLocation.setText(li.getFromLocation());
        holder.toLocation.setText(li.getToLocation());
        holder.vendorNo.setText(li.getVendorPhone());
        holder.driverNo.setText(li.getDriverPhone());
        holder.vendorName.setText(li.getVendorName());
        holder.driverName.setText(li.getDriverName());
        holder.bookingId.setText(li.getBookingId());
        holder.cabNo.setText(li.getCabNumber());
        holder.sendDateAndTime.setText(li.getDeliveryTime());


        holder.driverNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calling((Activity) mContext, li.getDriverPhone());
            }
        });


        if (li.getDeliveryStatus()!=null){

            if(li.getDeliveryStatus().equals("Accepted")){
//            holder.checkBookingStatus.back
                holder.checkBookingStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_accepted, 0, 0, 0);
            }else if (li.getDeliveryStatus().equals("Rejected")){
                holder.checkBookingStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_rejected, 0, 0, 0);
            }
            else if (li.getDeliveryStatus().equals("maintenance")){
                holder.checkBookingStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_maintenance, 0, 0, 0);
            } else if (li.getDeliveryStatus().equals("NoResponse")){
                holder.checkBookingStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_noresponse, 0, 0, 0);
            }
            holder.checkBookingStatus.setText(li.getDeliveryStatus());
        }

    }

    @Override
    public int getItemCount() {
        if (bookingLists!=null)
            return bookingLists.size();
        else return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class BookingActivitiesViewHolder extends RecyclerView.ViewHolder {
        TextView packageName, cabNo,cabType, amount, bookingId, formLocation, toLocation, dateTime, numberOfDay, paidWalletAmount, driverName, vendorName, driverNo, vendorNo, checkBookingStatus,sendDateAndTime;
        public BookingActivitiesViewHolder(@NonNull View itemView) {
            super(itemView);
            packageName = itemView.findViewById(R.id.package_name);
            driverName = itemView.findViewById(R.id.driver_name);
            vendorName = itemView.findViewById(R.id.vendor_name);
            driverNo = itemView.findViewById(R.id.call_driver);
            vendorNo = itemView.findViewById(R.id.call_vendor);
            formLocation = itemView.findViewById(R.id.from_location);
            toLocation = itemView.findViewById(R.id.to_location);
            dateTime = itemView.findViewById(R.id.date_and_time);
            numberOfDay = itemView.findViewById(R.id.no_of_day);
            bookingId = itemView.findViewById(R.id.booking_id);
            cabNo = itemView.findViewById(R.id.cab_No);
            cabType = itemView.findViewById(R.id.cabType);
            checkBookingStatus = itemView.findViewById(R.id.check_booking_status);
            sendDateAndTime = itemView.findViewById(R.id.send_date_and_time);
        }
    }

    public static  void calling(Activity context, String phone) {
        if(Utils.checkPermission( ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) )){

//      Log.i("DrawerActivity","checkPermission: true");
            Utils.callContact(context,phone);

        }else {
            //    requestCallingPermission();
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.CALL_PHONE)) {
                Utils.showOkAlert(context,null,"User has to provide permission to call", true);
                //If the user has denied the permission previously your code will come to this block
                //Here you can explain why you need this permission
                //Explain here why you need this permission
//        Log.i("DrawerActivity", " if (ActivityCompat.shouldShowRequestPermissionRationale)");
            }

            //And finally ask for the permission
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CALL_PHONE}, CALLING_PERMISSION);
//      Log.i("DrawerActivity", " ActivityCompat.requestPermissions");
        }
    }
}
