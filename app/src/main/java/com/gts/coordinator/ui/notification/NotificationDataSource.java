package com.gts.coordinator.ui.notification;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.gts.coordinator.Notification.Model.Contid;
import com.gts.coordinator.Notification.Model.NotificationList;
import com.gts.coordinator.Notification.Model.NotificationResponce;
import com.gts.coordinator.Retrofit.RetrofitApiInterface;
import com.gts.coordinator.Retrofit.RetrofitClient;
import com.gts.coordinator.utils.PreferenceData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationDataSource extends PageKeyedDataSource<Integer, NotificationList> {

    private static final int FIRST_PAGE = 1;
    Context context;
    public NotificationDataSource(Context context) {
        this.context = context;

    }
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, NotificationList> callback) {
        try {
            long contId = PreferenceData.getLong(context, "cont_id");
            Call<NotificationResponce> call = RetrofitClient.getClient().create(RetrofitApiInterface.class).contId(new Contid((int) contId, FIRST_PAGE));
            call.enqueue(new Callback<NotificationResponce>() {
                @Override
                public void onResponse(Call<NotificationResponce> call, Response<NotificationResponce> response) {
                    if (response.body() != null) {
                        callback.onResult(response.body().getNoti(), null, FIRST_PAGE + 1);
                        Log.e("respose", response.toString());
                      //  mListener.updateAvailableBalance(1);
                    }
                }

                @Override
                public void onFailure(Call<NotificationResponce> call, Throwable t) {
                    Log.d("error", "onFailure: " + t.getMessage());

                }
            });
        } catch (Exception e) {
            Log.e("respose", e.toString());
        }
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, NotificationList> callback) {
        try {

            long contId = PreferenceData.getLong(context, "cont_id");
            Call<NotificationResponce> call = RetrofitClient.getClient().create(RetrofitApiInterface.class).contId(new Contid((int) contId, params.key));
            call.enqueue(new Callback<NotificationResponce>() {
                @Override
                public void onResponse(Call<NotificationResponce> call, Response<NotificationResponce> response) {
                    if (response.body() != null) {
                        Integer key = (params.key > 1) ? params.key - 1 : null;
                        callback.onResult(response.body().getNoti(), key);

                    }
                }

                @Override
                public void onFailure(Call<NotificationResponce> call, Throwable t) {
                    Log.d("error", "onFailure: " + t.getMessage());

                }
            });

        } catch (Exception e) {
            Log.e("respose", e.toString());
        }
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, NotificationList> callback) {
        try {
           // ProgressDialog progressDialog;
           // progressDialog = new ProgressDialog(context);
           // progressDialog.show();
           // progressDialog.setMessage(context.getString(R.string.lod));
           //  prograsBar.stetusPrograsBar(0);
            long contId = PreferenceData.getLong(context, "cont_id");
            Call<NotificationResponce> call = RetrofitClient.getClient().create(RetrofitApiInterface.class).contId(new Contid((int) contId, params.key));
            call.enqueue(new Callback<NotificationResponce>() {
                @Override
                public void onResponse(Call<NotificationResponce> call, Response<NotificationResponce> response) {
                    if (response.body() != null) {
                        Integer key = response.body().getHashmore() ? params.key + 1 : null;
                        callback.onResult(response.body().getNoti(), key);
                    }
                }

                @Override
                public void onFailure(Call<NotificationResponce> call, Throwable t) {
                    Log.d("error", "onFailure: " + t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.e("respose", e.toString());
        }
    }

    public interface Update2{
        void updateAvailableBalance(double amount);
    }
}

