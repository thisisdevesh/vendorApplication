package com.gts.coordinator.ui.notification;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gts.coordinator.Notification.Model.NotificationList;
import com.gts.coordinator.Notification.Model.NotificationStetus;
import com.gts.coordinator.Notification.Model.StetusNotificationModel;
import com.gts.coordinator.R;
import com.gts.coordinator.Retrofit.RetrofitApiInterface;
import com.gts.coordinator.Retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificatioItemViewHolder extends RecyclerView.ViewHolder {
    private TextView tvTitile, tvMessage, tvTime, tvMessageFull;
   private ImageView butShowMor;
  private   RelativeLayout notification_layout;
  private NotificationList list;
  private RetrofitApiInterface apiInterface;
    private Context mCtx;
    private boolean status;

    public NotificatioItemViewHolder(@NonNull View itemView) {
        super(itemView);
        tvTitile = itemView.findViewById(R.id.tv_title);
        tvMessage = itemView.findViewById(R.id.tv_message);
        tvTime = itemView.findViewById(R.id.tv_time);
        butShowMor = itemView.findViewById(R.id.show_more);
        tvMessageFull = itemView.findViewById(R.id.tv_message_full);
        notification_layout = itemView.findViewById(R.id.notification_layout);
    }

    public void bindTo(NotificationList list,Context context) {
        this.list = list;
        status = list.getIsRead();


        if (list==list)

        try {
            if (status == true) {
                notification_layout.setBackgroundColor(context.getResources().getColor(R.color.white));
            } else {
                notification_layout.setBackgroundColor(context.getResources().getColor(R.color.noti_color));
            }
        }catch (Exception e){}

        //
        int length = list.getDescription().length();
        if (length > 80) {
            butShowMor.setVisibility(View.VISIBLE);
        } else {
            butShowMor.setVisibility(View.GONE);
        }
        tvTitile.setText(list.getTitle());//String.valueOf(re)
        tvMessage.setText(list.getDescription());//+"\n length = "+String.valueOf(length)
        tvMessageFull.setText(list.getDescription());
        tvTime.setText(list.getDatetime());
        butShowMor.setOnClickListener(view -> {
            if (tvMessage.getVisibility() == View.VISIBLE) {
                butShowMor.setRotation(180);
                tvMessage.setVisibility(View.GONE);
                tvMessageFull.setVisibility(View.VISIBLE);
            } else if (tvMessageFull.getVisibility() == View.VISIBLE) {
                butShowMor.setRotation(360);
                tvMessageFull.setVisibility(View.GONE);
                tvMessage.setVisibility(View.VISIBLE);
            }
        });

        notification_layout.setOnClickListener(view -> {
            if (status==false){
                apiInterface = RetrofitClient.getClient().create(RetrofitApiInterface.class);
                Call<StetusNotificationModel> call = apiInterface.contStetus(new NotificationStetus(1, list.getId()));
                call.enqueue(new Callback<StetusNotificationModel>() {
                    @Override
                    public void onResponse(Call<StetusNotificationModel> call, Response<StetusNotificationModel> response) {
                        try {
                            if (response.isSuccessful()) {
                                StetusNotificationModel model = response.body();
                                int status = model.getStatus();
                                if (status == 0) {
                                    notification_layout.setBackgroundColor(context.getResources().getColor(R.color.white));
                                    Toast.makeText(context, "ActivityNotification is Red", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }catch (Exception e){
                        }
                    }
                    @Override
                    public void onFailure(Call<StetusNotificationModel> call, Throwable t) {
                        Toast.makeText(context, "" + t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}
