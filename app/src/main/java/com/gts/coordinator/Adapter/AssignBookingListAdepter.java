package com.gts.coordinator.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.gts.coordinator.Model.ContractorData.acceptBooking.AssignList;
import com.gts.coordinator.Model.ContractorData.activeDriver.ActiveDriveDataResponse;
import com.gts.coordinator.Model.ContractorData.activeDriver.PostActiveDriver;
import com.gts.coordinator.Model.cancelBooking.CancelBookingRespose;
import com.gts.coordinator.Model.cancelBooking.PostCancelBookingData;
import com.gts.coordinator.R;
import com.gts.coordinator.Retrofit.RetrofitApiInterface;
import com.gts.coordinator.Retrofit.RetrofitClient;
import com.gts.coordinator.service.MyBookingService;
import com.gts.coordinator.ui.booking.sendBooking.BookingAssignDialogFragment;
import com.gts.coordinator.utils.PreferenceData;
import com.gts.coordinator.utils.Utils;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignBookingListAdepter extends RecyclerView.Adapter<AssignBookingListAdepter.BookingViewHolder> {
    private List<AssignList> bookingLists;
    private LayoutInflater mInflater;
    private Context mContext;
    private String penalty;
    public AssignBookingListAdepter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }
    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_view_accept_booking, parent, false);
        return new BookingViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        AssignList li = bookingLists.get(position);
        if (li.getPenaltyAmount() != null){
            penalty = li.getPenaltyAmount();
        }
             Log.d("MyTags",penalty+"");
        if (li.getPlanType().equalsIgnoreCase("Both Way")) {
            if (holder.numberOfDay.getVisibility() == View.GONE) {
                holder.numberOfDay.setVisibility(View.VISIBLE);
                holder.numberOfDay.setText(String.valueOf(li.getBookingDays()));
            }
        } else {
            if (holder.numberOfDay.getVisibility() == View.VISIBLE)
                holder.numberOfDay.setVisibility(View.GONE);
        }
        // holder.palanTyupe.setText(li.getTerrifType());
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
        holder.paidWalletAmount.setText(String.valueOf(li.getPaidWalletAmount()));
        holder.amount.setText(String.valueOf(li.getTotalAmount()));
        if (li.getIsReplace()) {
            holder.replaceBooking.setVisibility(View.VISIBLE);
            holder.replaceBooking.setOnClickListener(v -> {
                if (Utils.getConnectivityStatus(mContext) == 0) {
                    Utils.showOkAlert(mContext,mContext.getString(R.string.info), "Please check your internet connection", false);
                } else {
                    BookingAssignDialogFragment updateDriver = BookingAssignDialogFragment.newInstance(li.getBookingId(), PreferenceData.getLong(mContext, "cont_id"), 1, li.getCabNumber());
                    updateDriver.show((Activity) mContext);
                }
                //
/*
                new MaterialAlertDialogBuilder(mContext, R.style.AlertDialogTheme)
                        .setMessage("इस बुकिंग के लिए  आप केवल एक बार ड्राइवर बदल सकते हैं। ")
                        .setPositiveButton("हाँ", (dialog, which) -> {
                            BookingAssignDialogFragment updateDriver = BookingAssignDialogFragment.newInstance(li.getBookingId(), PreferenceData.getLong(mContext, "cont_id"), 1, li.getCabNumber());
                            updateDriver.show((Activity) mContext);
                            dialog.dismiss();
                            // cancelBooking();
                            // finish();
                        })
                        .setNegativeButton("नहीं", (dialog, which) -> dialog.dismiss())
                        .show();
*/

                //0 new ,1 replace, 2 free
                //PreferenceData.saveString(mContext,"r_cab_no",li.getCabNumber());
                // PreferenceData.saveInt(mContext,"rqt",1);

                //   holder.showPanal.setVisibility(View.GONE);
            });

        }
        if (li.getIsFree()) {
            holder.freeDriver.setVisibility(View.VISIBLE);
            holder.freeDriver.setText("Free");
            holder.freeDriver.setOnClickListener(v -> {
                //   status for free driver
                if (Utils.getConnectivityStatus(mContext) == 0) {
                    Utils.showOkAlert(mContext,mContext.getString(R.string.info), "Please check your internet connection", false);
                } else {
                    callApiFreeDriver(li.getCabNumber(), li.getBookingId(), PreferenceData.getLong(mContext, "cont_id"), 2);
                }
                //  holder.showPanal.setVisibility(View.GONE);
            });

/* आप केवल एक बार ड्राइवर बदल सकते हैं। */
        }
        if (li.getIsCancel()) {
            holder.freeDriver.setVisibility(View.VISIBLE);
            holder.freeDriver.setText("Cancel");
            holder.freeDriver.setOnClickListener(v -> {
                if (Utils.getConnectivityStatus(mContext) == 0) {
                    Utils.showOkAlert(mContext,mContext.getString(R.string.info), "Please check your internet connection", false);
                } else {
                    new MaterialAlertDialogBuilder(mContext, R.style.AlertDialogTheme)
                            .setTitle("यदि आप बुकिंग रद्द कर रहे हैं तो आपको "+penalty+" रुपया जुर्माना देना होगा।")
                            .setMessage("क्या आप जुर्माना के लिए तैयार हैं\n" +
                                    "यह जुर्माना आपके खाते से काट लिया जाएगा")
                            .setPositiveButton("हाँ", (dialog, which) -> {
                                dialog.dismiss();
                                cancelBooking(li, dialog);
                            })
                            .setNegativeButton("नहीं", (dialog, which) -> dialog.dismiss())
                            .show();
                }
            });
        }
    }

    private void cancelBooking(AssignList li, DialogInterface dialog) {
     RetrofitApiInterface apiInterface =RetrofitClient.getClient().create(RetrofitApiInterface.class);
     Call<CancelBookingRespose> call =apiInterface.cancelBooking(new PostCancelBookingData(li.getBookingId(),li.getCabNumber()));
     call.enqueue(new Callback<CancelBookingRespose>() {
         @Override
         public void onResponse(Call<CancelBookingRespose> call, Response<CancelBookingRespose> response) {
             if (response.isSuccessful()){
                 if (response.body().getStatus()==0){
                     Toast.makeText(mContext, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                     dialog.dismiss();
                     mContext.stopService(new Intent(mContext, MyBookingService.class));
                     mContext.startService(new Intent(mContext,MyBookingService.class));
                 }
             }else {
                 Toast.makeText(mContext, "Server Error", Toast.LENGTH_SHORT).show();
             }
         }

         @Override
         public void onFailure(Call<CancelBookingRespose> call, Throwable t) {
             Toast.makeText(mContext, "Time Out", Toast.LENGTH_SHORT).show();
         }
     });

    }

    private void callApiFreeDriver(String cabNumber, String bookingId, Long cont_id, int i) {
        RetrofitApiInterface apiInterface = RetrofitClient.getClient().create(RetrofitApiInterface.class);
        Call<ActiveDriveDataResponse> call = apiInterface.getDriverDetail(new PostActiveDriver(cont_id, bookingId, cabNumber, i));
        call.enqueue(new Callback<ActiveDriveDataResponse>() {
            @Override
            public void onResponse(Call<ActiveDriveDataResponse> call, Response<ActiveDriveDataResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 0) {
                        if (response.body().getDriverData() != null) {
                            TastyToast.makeText(mContext, "Driver " + response.body().getMessage(), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                            mContext.stopService(new Intent(mContext,MyBookingService.class));
                            mContext.startService(new Intent(mContext,MyBookingService.class));
                        }
                    }
                } else {
                    TastyToast.makeText(mContext, "Some Thing Went wrong ", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                }
            }

            @Override
            public void onFailure(Call<ActiveDriveDataResponse> call, Throwable t) {
                TastyToast.makeText(mContext, "" + t.getMessage(), TastyToast.LENGTH_LONG, TastyToast.ERROR);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (bookingLists != null)
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

    public void setBookingData(List<AssignList> bookingData) {
        bookingLists = bookingData;
        Log.d("BookingAssignList", String.valueOf(bookingLists.size()));
        notifyDataSetChanged();
    }

    class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView packageName, cabNo,cabType, replaceBooking, freeDriver, cabCategory, amount, bookingId, formLocation, toLocation, dateTime, numberOfDay, paidWalletAmount, driverName, vendorName, driverNo, vendorNo;

        // ImageView assignBooking;
        //   LinearLayout showPanal;
        //2004049449
        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            packageName = itemView.findViewById(R.id.package_name);
            /* palanTyupe = itemView.findViewById(R.id.booking_id);*/
            cabCategory = itemView.findViewById(R.id.cab_category);
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
            paidWalletAmount = itemView.findViewById(R.id.paid_wallet_amount);
            amount = itemView.findViewById(R.id.amount);
            // showPanal = itemView.findViewById(R.id.lay_show_panel);
            replaceBooking = itemView.findViewById(R.id.replace);
            freeDriver = itemView.findViewById(R.id.free);
            cabType = itemView.findViewById(R.id.cabType);


        }
    }

}
