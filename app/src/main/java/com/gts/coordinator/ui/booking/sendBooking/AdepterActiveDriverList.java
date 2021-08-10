package com.gts.coordinator.ui.booking.sendBooking;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gts.coordinator.Adapter.AdapterAssignedDriver;
import com.gts.coordinator.Model.ContractorData.activeDriver.DriverDatum;
import com.gts.coordinator.Model.ContractorData.assignDriver.PostCabNo;
import com.gts.coordinator.Model.ContractorData.assignDriver.ReposeAssignDriver;
import com.gts.coordinator.Model.ContractorData.bookingSent.PostSentBooking;
import com.gts.coordinator.Model.ContractorData.bookingSent.ReposeBookingSent;
import com.gts.coordinator.Model.bookingStatus.StatusRespose;
import com.gts.coordinator.R;
import com.gts.coordinator.Retrofit.RetrofitApiInterface;
import com.gts.coordinator.Retrofit.RetrofitClient;
import com.gts.coordinator.service.MyBookingService;
import com.gts.coordinator.utils.Utils;

import com.sdsmdg.tastytoast.TastyToast;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdepterActiveDriverList extends RecyclerView.Adapter<AdepterActiveDriverList.ActiveDriverViewModel> implements Filterable {
    private Context activity;
    private List<DriverDatum> driverList;
    private List<DriverDatum> driverListAll;
    private BookingStatusViewModel bookingStatusViewModel;
    private String bookingId;
    private boolean bookingSendStatus = true;
    int bookingRqT;
    private AdapterAssignedDriver adapterAssignedDriver;
    Dialog dialog;

    public AdepterActiveDriverList(Context activity, List<DriverDatum> driverList, Dialog dialog, String bookingId, int bookingRqT) {
        this.activity = activity;
        this.driverList = driverList;
        this.dialog = dialog;
        this.bookingId = bookingId;
        this.bookingRqT = bookingRqT;
        driverListAll = new ArrayList<>(driverList);
        bookingStatusViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(BookingStatusViewModel.class);
    }

    @NonNull
    @Override
    public ActiveDriverViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_active_driver, parent, false);
        return new ActiveDriverViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveDriverViewModel holder, int position) {
        final DriverDatum list = driverList.get(position);
        holder.vendorPhone.setText(list.getDriverPhone());
        holder.vendorName.setText(list.getVendorName());
        holder.driverName.setText(list.getDriverName());
        holder.driverPhone.setText(list.getDriverPhone());
        holder.cabNumber.setText(list.getCabNumber());
        holder.driverPhone.setOnClickListener(v -> {
            if (Utils.getConnectivityStatus(activity) == 0) {
                Utils.showOkAlert(activity, activity.getString(R.string.info), "Please check your internet connection", false);
            } else {
                Utils.callContact(activity, list.getDriverPhone());
            }

        });
        holder.vendorPhone.setOnClickListener(v -> {
            if (Utils.getConnectivityStatus(activity) == 0) {
                Utils.showOkAlert(activity, activity.getString(R.string.info), "Please check your internet connection", false);
            } else {
                Utils.callContact(activity, list.getVendorPhone());
            }
        });
        holder.sentBooking.setOnClickListener(v -> {

            if (bookingSendStatus){
                if (Utils.getConnectivityStatus(activity) == 0) {
                    TastyToast.makeText(activity, "Please check your internet connection", TastyToast.LENGTH_LONG, TastyToast.INFO);
                }
                else {
                    holder.sentBooking.setVisibility(View.GONE);
                    if (holder.progressBar.getVisibility() == View.GONE)
                        holder.progressBar.setVisibility(View.VISIBLE);
                    RetrofitApiInterface apiInterface = RetrofitClient.getClient().create(RetrofitApiInterface.class);
                    Call<ReposeBookingSent> call = apiInterface.sentBooking(new PostSentBooking(bookingId, list.getCabNumber(), bookingRqT));
                    call.enqueue(new Callback<ReposeBookingSent>() {
                        @Override
                        public void onResponse(Call<ReposeBookingSent> call, Response<ReposeBookingSent> response) {
                            if (response.isSuccessful()) {
                                Log.d("rss", "onResponse: booking  " + response.body());
                                if (response.body().getStatus() == 0) {
                                    // bookingStatusViewModel.onCleared();

                                    if (response.body().getMessage() != null) {
                                        String mess = response.body().getMessage();
                                        holder.progressBar.setVisibility(View.GONE);
                                        new Thread(() -> activity.startService(new Intent(activity, MyBookingService.class))).start();

                                        if (mess != null)
                                            TastyToast.makeText(activity, "" + mess, TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
//                                    mutableLiveData.setValue(mess);
                                    }
                                } else {
                                    TastyToast.makeText(activity, "Some thing went Wrong ", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                    holder.progressBar.setVisibility(View.GONE);
                                }
                            } else {
                                TastyToast.makeText(activity, "Some thing went Wrong ", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                holder.progressBar.setVisibility(View.GONE);
//                            mutableLiveData.setValue(response.errorBody().toString());
                            }

                            bookingStatusViewModel.getResponse(bookingId,list.getCabNumber()).observe((LifecycleOwner) activity, statusRespose -> {
                                bookingSendStatus =false;
                                if (statusRespose!=null){

                                    if (holder.bookingStatus.getVisibility()==View.GONE){
                                        holder.sentBooking.setVisibility(View.GONE);
                                        holder.bookingStatus.setVisibility(View.VISIBLE);
                                        //  holder.bookingStatus.setText("Ravi"+statusRespose);
                                    }
//                                    if (statusRespose. equalsIgnoreCase("Delivered")){
//                                        if (holder.bookingStatus.getVisibility()==View.VISIBLE){
//                                            holder.bookingStatus.setVisibility(View.GONE);
//                                            holder.sentBooking.setVisibility(View.VISIBLE);
//                                            bookingStatusViewModel.pauseTime();
//                                        }
//                                        // dialog.dismiss();
//                                    }
                                    //Delivered
                                    //  holder.bookingStatus.setText(statusRespose);
                                    TastyToast.makeText(activity, ""+statusRespose, TastyToast.LENGTH_SHORT,TastyToast.INFO).show();
                                }
                            });
                            bookingStatusViewModel.getDeliveryStatus().observe((LifecycleOwner) activity, status -> {
                                if (!status){
                                    dialog.dismiss();
                                }
                            });
                            bookingStatusViewModel.getErrorMessage().observe((LifecycleOwner) activity, error -> {
                                if (error!=null){
                                    bookingSendStatus = true;
                                    if (holder.bookingStatus.getVisibility()==View.VISIBLE){
                                        holder.bookingStatus.setVisibility(View.GONE);
                                        holder.sentBooking.setVisibility(View.VISIBLE);
                                        bookingStatusViewModel.pauseTime();
                                    }

                                    TastyToast.makeText(activity, ""+error, TastyToast.LENGTH_SHORT,TastyToast.INFO).show();
                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<ReposeBookingSent> call, Throwable t) {
//                        mutableLiveData.setValue(t.getMessage());
                            holder.progressBar.setVisibility(View.GONE);
                            TastyToast.makeText(activity, "Some thing went Wrong ", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        }
                    });
                }
            }else {
                TastyToast.makeText(activity, "Please Wait", TastyToast.LENGTH_LONG, TastyToast.INFO);
            }


        });
        holder.booking_counter.setText(list.getAssignedBooking());
        holder.booking_counter.setOnClickListener(v -> {
            if (Utils.getConnectivityStatus(activity) == 0) {
                TastyToast.makeText(activity, "Please check your internet connection", TastyToast.LENGTH_LONG, TastyToast.INFO);
            } else {

                if (holder.driver_list.getVisibility() == View.GONE) {
                    holder.driver_list.setVisibility(View.VISIBLE);
                    holder.driver_list.setHasFixedSize(true);
                    holder.driver_list.setLayoutManager(new LinearLayoutManager(activity));
                    //rss
                    callApi(holder, list);
                } else {
                    holder.driver_list.setVisibility(View.GONE);
                }
            }

           // TastyToast.makeText(activity, "click ", TastyToast.LENGTH_LONG, TastyToast.CONFUSING);
        });


    }

    private void callApi(ActiveDriverViewModel holder, DriverDatum list) {
        RetrofitApiInterface apiInterface = RetrofitClient.getClient().create(RetrofitApiInterface.class);
        Call<ReposeAssignDriver> call = apiInterface.getDriver(new PostCabNo(list.getCabNumber()));
        call.enqueue(new Callback<ReposeAssignDriver>() {
            @Override
            public void onResponse(Call<ReposeAssignDriver> call, Response<ReposeAssignDriver> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 0) {
                        Toast.makeText(activity, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        adapterAssignedDriver = new AdapterAssignedDriver(response.body().getDriverData(), activity);
                        holder.driver_list.setAdapter(adapterAssignedDriver);
                    }
                }
            }

            @Override
            public void onFailure(Call<ReposeAssignDriver> call, Throwable t) {
                Toast.makeText(activity, "onFailure " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        //    Call<ReposeBookingSent> call = apiInterface.sentBooking(new PostSentBooking(bookingId,list.getCabNumber(),bookingRqT));

    }

    @Override
    public int getItemCount() {
        return driverList.size();
    }

    @Override
    public Filter getFilter() {

        return filterDriverList;
    }

    private Filter filterDriverList = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<DriverDatum> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(driverListAll);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (DriverDatum item : driverListAll) {
                    if (item.getCabNumber().toLowerCase().contains(filterPattern)
                            || item.getDriverName().toLowerCase().contains(filterPattern) ||
                            item.getDriverPhone().toLowerCase().contains(filterPattern) ||
                            item.getVendorName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    } else {
                        //  Toast.makeText(activity, "Item Not Found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            driverList.clear();
            driverList.addAll((List) results.values);
            if (((List) results.values).size() == 0) {
          /*  if (select_list_type!=1){
                Utils.showOkAlert(activity,activity.getString(R.string.error),"Item not Found Driver List",false);
            }*/
            }
            notifyDataSetChanged();
        }
    };

    public class ActiveDriverViewModel extends RecyclerView.ViewHolder {
        TextView cabNumber, driverName, vendorName, driverPhone, vendorPhone, booking_counter;
        ImageView sentBooking, okBooking;
        ProgressBar progressBar;
        RecyclerView driver_list;
        AVLoadingIndicatorView bookingStatus;

        public ActiveDriverViewModel(@NonNull View itemView) {
            super(itemView);
            cabNumber = itemView.findViewById(R.id.cabNumber);
            driverName = itemView.findViewById(R.id.driverName);
            driverPhone = itemView.findViewById(R.id.driverNumber);
            vendorName = itemView.findViewById(R.id.vendorName);
            vendorPhone = itemView.findViewById(R.id.vendorNumber);
            sentBooking = itemView.findViewById(R.id.sentBooking);
            okBooking = itemView.findViewById(R.id.checkImage);
            progressBar = itemView.findViewById(R.id.process_bar_row);
            booking_counter = itemView.findViewById(R.id.booking_counter);
            driver_list = itemView.findViewById(R.id.driver_list);
           // bookingStatus = itemView.findViewById(R.id.bookingStatus);
            bookingStatus = itemView.findViewById(R.id.loader);

        }
    }
}
