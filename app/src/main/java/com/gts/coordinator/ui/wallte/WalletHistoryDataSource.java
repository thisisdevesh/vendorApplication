package com.gts.coordinator.ui.wallte;

import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.gts.coordinator.Model.ContractorData.WallteHistory.History;
import com.gts.coordinator.Model.ContractorData.WallteHistory.HistoryDetailsResponce;
import com.gts.coordinator.Model.ContractorData.WallteHistory.PostTranstionHistory;
import com.gts.coordinator.Retrofit.RetrofitApiInterface;
import com.gts.coordinator.Retrofit.RetrofitClientWcm;
import com.gts.coordinator.utils.PreferenceData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletHistoryDataSource extends PageKeyedDataSource<Integer, History> {
    private static final int FIRST_PAGE = 1;
    Context context;
    showPrograss showPrograss;
    private ProgressBar progressBar;
    //int a =0;

    // showPrograsBar prograsBar;
    public WalletHistoryDataSource(Context context) {
        this.context = context;
        if (context instanceof ActivityWallet) {
            this.showPrograss = (ActivityWallet) context;

        }
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, History> callback) {
        try {
         //   a=1;
            // showPrograss.progressBarStatus(true);
            long contId = PreferenceData.getLong(context, "cont_id");
            Call<HistoryDetailsResponce> call = RetrofitClientWcm.getClient().create(RetrofitApiInterface.class).transferHistory(new PostTranstionHistory(contId, FIRST_PAGE));
            call.enqueue(new Callback<HistoryDetailsResponce>() {
                @Override
                public void onResponse(Call<HistoryDetailsResponce> call, Response<HistoryDetailsResponce> response) {
                    if (response.body() != null) {
                        callback.onResult(response.body().getD().getHistory(), null, FIRST_PAGE + 1);
                        Log.e("respose", response.toString());
                      //  a=2;
                    }
                }

                @Override
                public void onFailure(Call<HistoryDetailsResponce> call, Throwable t) {
                    Log.d("wallet history", "onFailure: " + t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.e("respose", e.toString());
        }
    }


    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, History> callback) {
        try {
         //   progressBar.setVisibility(View.VISIBLE);
            Call<HistoryDetailsResponce> call = RetrofitClientWcm.getClient().create(RetrofitApiInterface.class).transferHistory(new PostTranstionHistory(PreferenceData.getLong(context, "cont_id"), params.key));
            call.enqueue(new Callback<HistoryDetailsResponce>() {
                @Override
                public void onResponse(Call<HistoryDetailsResponce> call, Response<HistoryDetailsResponce> response) {

                    if (response.body() != null) {
                        Integer key = (params.key > 1) ? params.key - 1 : null;
                        callback.onResult(response.body().getD().getHistory(), key);
                 //       progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<HistoryDetailsResponce> call, Throwable t) {
                    Log.e("onFailure", t.toString());
                }
            });
        } catch (Exception e) {
            Log.e("respose", e.toString());
        }
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, History> callback) {
        try {
         //  progressBar.setVisibility(View.VISIBLE);
            Call<HistoryDetailsResponce> call = RetrofitClientWcm.getClient().create(RetrofitApiInterface.class).transferHistory(new PostTranstionHistory(PreferenceData.getLong(context, "cont_id"), params.key));
            call.enqueue(new Callback<HistoryDetailsResponce>() {
                @Override
                public void onResponse(Call<HistoryDetailsResponce> call, Response<HistoryDetailsResponce> response) {

                    if (response.body() != null) {
                        Integer key = response.body().getD().getHashmore() ? params.key + 1 : null;
                        callback.onResult(response.body().getD().getHistory(), key);
                       // progressBar.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onFailure(Call<HistoryDetailsResponce> call, Throwable t) {
                    Log.d("error", "onFailure: " + t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.e("respose", e.toString());
        }
    }

    public interface showPrograss {
        void progressBarStatus(boolean a);
    }

    public void showPro(ProgressBar progressBar) {
      this.progressBar=progressBar;
     // this.a = a;
    }
}
