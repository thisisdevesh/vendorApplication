package com.gts.coordinator.Adapter;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gts.coordinator.R;
import com.gts.coordinator.dao.Vendor;
import com.gts.coordinator.otp.UpdateVendorNumber.OtpFragmentUpdateDriver;
import com.gts.coordinator.utils.Utils;
import java.util.ArrayList;
import java.util.List;
//todo ok report edit by Ravinder 18.05.2019
public class VendorListAdapterViewE extends RecyclerView.Adapter<VendorListAdapterViewE.VendorListViewHolder> implements Filterable {
    private LinearLayout linearLayout;
    private Context context;
    private List<Vendor> vendorList;
    private List<Vendor> vendorListAll;
    private TextView text_item_notfound;
    private static int CALLING_PERMISSION = 23;
    public VendorListAdapterViewE(ArrayList<Vendor> vendorList, Context context,TextView text_item_notfound) {
        this.vendorList = vendorList;
        this.context = context;
        this.text_item_notfound =text_item_notfound;
        vendorListAll = new ArrayList<>(vendorList);
    }


    @Override
    public VendorListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.update_vendor_row, parent, false);
        linearLayout =  view.findViewById(R.id.card_linear);
        return new VendorListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VendorListViewHolder holder, final int position) {
      //  holder =holder2;
        final Vendor vdr = vendorList.get(position);
        final String phNo = vdr.getPhno();
        final String vendorName = vdr.getName();
        final String vendorAddress = vdr.getAddress();
        final boolean isVerified = vdr.isVerified();
        holder.vendorPhno.setText(phNo);
        byte statusVen = vdr.getStatus();
        if (statusVen == 0) {
            holder.callingImage.setImageDrawable(context.getResources().getDrawable(R.drawable.call_white));
            holder.vendorName.setText(vendorName);
            holder.vndorColor.setBackground(context.getResources().getDrawable(R.drawable.ic_circle_green));

        } else {
            Drawable image = context.getResources().getDrawable(R.drawable.call_black);
            holder.callingImage.setImageDrawable(image);
            holder.vndorColor.setBackground(context.getResources().getDrawable(R.drawable.ic_circle_deactivate));
        }
        if (vdr.isVerified()) {
            holder.lay_rel.setVisibility(View.GONE);
            holder.tvVerifiedStatus.setVisibility(View.GONE);
        }
        holder.vndorColor.setOnClickListener(v -> {
//         Log.i("DriverListFragment"," *********ONclick phoneNO = "+phNO);
            calling((Activity) context, phNo);
        });

        holder.vendorPhno.setOnClickListener(view -> {

            if (Utils.getConnectivityStatus(context) == 0) {
                Utils.showOkAlert(context, context.getString(R.string.info), "Please check your internet connection", false);
            } else {

                OtpFragmentUpdateDriver otpFragment;
                otpFragment = OtpFragmentUpdateDriver.newInstance(vdr.getVndId(),vendorName,vendorAddress,phNo,isVerified);
                otpFragment.setCancelable(false);
                FragmentTransaction ft =((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                Fragment prev = ((AppCompatActivity) context).getSupportFragmentManager().findFragmentByTag("dialog");
                ft = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                prev = ((AppCompatActivity) context).getSupportFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                otpFragment.show(ft, "dialog");

            }
        });
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
                filteredList.addAll(vendorListAll);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Vendor item : vendorListAll) {
                    if (item.getName().toLowerCase().contains(filterPattern)
                            || item.getPhno().toLowerCase().contains(filterPattern)) {
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
//            if (results.count!=0){
            Log.e("rss", "publishResults: "+results );
            Log.e("rss", "publishResults: "+constraint );

                vendorList.clear();
                vendorList.addAll((List) results.values);
                if (((List) results.values).size()==0){
                    text_item_notfound.setVisibility(View.VISIBLE);
               // Toast.makeText(context, "Item not Found", Toast.LENGTH_SHORT).show();
                //    Utils.showOkAlert(context,context.getString(R.string.error),"Item not Found",false);
                   // itemNotFound(1);
            }else {
                    text_item_notfound.setVisibility(View.GONE);
                }
                notifyDataSetChanged();
//            }else {
//                Toast.makeText(context, "No results...", Toast.LENGTH_SHORT).show();
//            }

        }
    };


    public void sendOpt(String phoneNo)
    {

    }


/*    public void itemNotFound(int i) {

    }
    */

    public class VendorListViewHolder extends RecyclerView.ViewHolder {
        public TextView vendorPhno, vendorName, busy, free, logout, deactivate, tvVerifiedStatus;
        public LinearLayout vendorCalling;
        public LinearLayout vndorColor;
        public RelativeLayout lay_rel;
        public ImageView callingImage;
        public Button update;
        public VendorListViewHolder(View itemView) {
            super(itemView);
            vendorName = (TextView) itemView.findViewById(R.id.image_view);
            tvVerifiedStatus = (TextView) itemView.findViewById(R.id.verified_status);
            vendorPhno = (TextView) itemView.findViewById(R.id.text_vdr_phoneNum);
            free = (TextView) itemView.findViewById(R.id.free);
            busy = (TextView) itemView.findViewById(R.id.busy);
            logout = (TextView) itemView.findViewById(R.id.logout);
            deactivate = (TextView) itemView.findViewById(R.id.deactivate);
            vndorColor = itemView.findViewById(R.id.vendorColor);
            vendorCalling = (LinearLayout) itemView.findViewById(R.id.vdrCallingLay);
            callingImage = (ImageView) itemView.findViewById(R.id.vendorCallImg);
            lay_rel = itemView.findViewById(R.id.lay_rel);

        }
    }
    public static  void calling(Activity context,String phone) {
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
