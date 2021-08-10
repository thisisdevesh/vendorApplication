package com.gts.coordinator.Adapter;

import android.app.Activity;
import android.content.Context;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gts.coordinator.Model.ContractorData.assignBooking.BookingList;
import com.gts.coordinator.R;
import com.gts.coordinator.ui.booking.sendBooking.BookingAssignDialogFragment;
import com.gts.coordinator.utils.PreferenceData;
import com.gts.coordinator.utils.Utils;

import java.util.ArrayList;
import java.util.List;
public class BookingListAdepter extends RecyclerView.Adapter<BookingListAdepter.BookingViewHolder> {
    private List<BookingList> bookingLists = new ArrayList<>();
    private  LayoutInflater mInflater;
    private Context mContext;
    private static final String TAG = "MyTag";

    public BookingListAdepter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext =context;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = mInflater.inflate(R.layout.item_view_booking_list, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        BookingList li = bookingLists.get(position);
        if(li.getPlanType().equalsIgnoreCase("Both Way")){
            if (holder.numberOfDay.getVisibility()==View.GONE) {
                holder.numberOfDay.setVisibility(View.VISIBLE);
                holder.numberOfDay.setText(String.valueOf(li.getBookingDays()));
            }
        }else {
            if (holder.numberOfDay.getVisibility()==View.VISIBLE)
            holder.numberOfDay.setVisibility(View.GONE);
           }
        if (li.getPlanType().equals(li.getTerrifType())){
            if (holder.palanTyupe.getVisibility()==View.VISIBLE)
            holder.palanTyupe.setVisibility(View.GONE);
        }else { if (holder.palanTyupe.getVisibility()==View.GONE)
            holder.palanTyupe.setVisibility(View.VISIBLE);}
        holder.packageName.setText(li.getPlanType());
        holder.cabCategory.setText(li.getCabCategory());
        holder.dateTime.setText(li.getDatetime());
        holder.formLocation.setText(li.getFromLocation());
        holder.toLocation.setText(li.getToLocation());
        holder.amount.setText(String.valueOf(li.getTotalAmount()));
        holder.paidWalletAmount.setText(String.valueOf(li.getPaidWalletAmount()));
        holder.assignBooking.setOnClickListener(v -> {
            if (Utils.getConnectivityStatus(mContext) == 0) {
                Utils.showOkAlert(mContext,mContext.getString(R.string.info), "Please check your internet connection", false);
            }else {
                BookingAssignDialogFragment updateDriver = BookingAssignDialogFragment.newInstance(li.getBookingId(), PreferenceData.getLong(mContext,"cont_id"),0,"");
                updateDriver.show((Activity) mContext);

            }
        });
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

    public void setBookingData(List<BookingList> bookingData) {
        bookingLists = bookingData;
        Log.d(TAG, "UnassignedBooking List Adapter ListSize "+bookingLists.size());
        notifyDataSetChanged();
    }
    class BookingViewHolder extends RecyclerView.ViewHolder{
     TextView packageName,palanTyupe,cabCategory,amount,formLocation,toLocation,dateTime,numberOfDay,paidWalletAmount;
     ImageView assignBooking,acceptBooking;
        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            packageName = itemView.findViewById(R.id.package_name);
            palanTyupe = itemView.findViewById(R.id.plan_type);
            cabCategory = itemView.findViewById(R.id.cab_category);
            amount = itemView.findViewById(R.id.amount);
            formLocation = itemView.findViewById(R.id.from_location);
            toLocation = itemView.findViewById(R.id.to_location);
            dateTime = itemView.findViewById(R.id.date_and_time);
            numberOfDay = itemView.findViewById(R.id.no_of_day);
            assignBooking = itemView.findViewById(R.id.assign_booking);
            paidWalletAmount = itemView.findViewById(R.id.paid_wallet_amount);
            acceptBooking =itemView.findViewById(R.id.accept_booking);


        }
    }
}
