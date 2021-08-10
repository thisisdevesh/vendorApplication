package com.gts.coordinator.Adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gts.coordinator.R;
import com.gts.coordinator.dao.Driver;
import com.gts.coordinator.utils.Utils;

import java.util.List;

public class AdepterStetusCabActivity extends RecyclerView.Adapter<AdepterStetusCabActivity.MyViewHolder> {
    private List<Driver> drivers;
    private Context context;
    private static int CALLING_PERMISSION = 23;

    public AdepterStetusCabActivity(List<Driver> drivers, Context context) {
        this.drivers = drivers;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.stetus_cab_activity, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        final Driver dr = drivers.get(i);
        holder.text1.setText(dr.getName());
        holder.text2.setText(dr.getCabNo());
        holder.text3.setText(dr.getPhoneNo());
        holder.text4.setText(dr.getCabName());
        byte rr = dr.getStatus();
        final String phNo = dr.getPhoneNo();
        if (rr == (byte) 5) {
            holder.vndorColor.setBackground(context.getResources().getDrawable(R.drawable.ic_circle_deactivate));
            holder.callingImage.setImageDrawable(context.getResources().getDrawable(R.drawable.call_black));
            holder.cabStatus.setText("Maintenance");
        } else if (rr == (byte) 3) {
            holder.cabStatus.setText("Logout");
            holder.vndorColor.setBackground(context.getResources().getDrawable(R.drawable.ic_circle_black));
        } else if (rr == (byte) 1) {
            holder.vndorColor.setBackground(context.getResources().getDrawable(R.drawable.ic_circle_green));
            holder.cabStatus.setText("Free");
        } else if (rr == (byte) 2) {
            holder.vndorColor.setBackground(context.getResources().getDrawable(R.drawable.ic_circle_read));
            holder.cabStatus.setText("Busy");
        }
        holder.vndorColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(context, "Call Activity", Toast.LENGTH_SHORT).show();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phNo));//change the number
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.

                    return;
                }
                context.startActivity(callIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return drivers.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView text1,text2,text3,text4, cabStatus;
        RelativeLayout vndorColor;
        ImageView callingImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 =itemView.findViewById(R.id.driver_name);
            text2 =itemView.findViewById(R.id.driver_cab_no);
            text3 =itemView.findViewById(R.id.driver_number);
            text4 =itemView.findViewById(R.id.driver_cab_name);
            vndorColor =itemView.findViewById(R.id.vendorColor_driver);
            cabStatus = itemView.findViewById(R.id.cab_status);
            callingImage =itemView.findViewById(R.id.vendorCallImg);
        }
    }
    public static  void calling(Activity context,String phone) {
        if(Utils.checkPermission( ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) )){
            Utils.callContact(context,phone);
        }else {
            //    requestCallingPermission();
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.CALL_PHONE)) {
                Utils.showOkAlert(context,null,"User has to provide permission to call", true);
            }
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CALL_PHONE}, CALLING_PERMISSION);
        }
    }
}
