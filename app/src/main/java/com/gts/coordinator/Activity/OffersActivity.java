package com.gts.coordinator.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gts.coordinator.Model.ContractorData.Bonus.OfferProgressModel;
import com.gts.coordinator.Model.ContractorData.Bonus.OfferProgressRequestModel;
import com.gts.coordinator.R;
import com.gts.coordinator.Retrofit.RetrofitApiInterface;
import com.gts.coordinator.Retrofit.RetrofitClient;
import com.gts.coordinator.utils.PreferenceData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OffersActivity extends AppCompatActivity {

    private ImageView ivOfferInfo, ivBackButton;
    private TextView tvOfferInfo;
    private long contractorID;
    private ConstraintLayout constraintLayout;
    private ProgressBar progressBar;
    private RetrofitApiInterface apiInterface;
    private String amount, endDate, Sedan, Hatchback, Mini, Suv, MiniSuv, description, strTotalCabs;
    private TextView tvAmount, offerEndDate, tvSedan, tvHatchback, tvMini, tvSuv, tvMiniSuv, totalCabs , tvName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
        ivOfferInfo = findViewById(R.id.offerInfo);
        tvOfferInfo = findViewById(R.id.offerInfo2);
        ivBackButton =findViewById(R.id.backButton);
        constraintLayout = findViewById(R.id.constraintLayoutOffersActivity);
        progressBar = findViewById(R.id.process_bar_offersActyivity);
        offerEndDate = findViewById(R.id.offerEndDate);
        tvSedan = findViewById(R.id.sedan);
        tvHatchback = findViewById(R.id.hatchback);
        tvMini = findViewById(R.id.mini);
        tvSuv = findViewById(R.id.suv);
        totalCabs = findViewById(R.id.totalCabs);
        tvAmount = findViewById(R.id.offer_total_amount);
        contractorID = PreferenceData.getLong(OffersActivity.this, "cont_id");

        CallApiOffersActivity(contractorID);


        ivOfferInfo.setOnClickListener(v1 ->
                startActivity(new Intent(OffersActivity.this, OfferDetailsActivity.class)));

        tvOfferInfo.setOnClickListener(v2->
            startActivity(new Intent(OffersActivity.this,OfferDetailsActivity.class)));

        ivBackButton.setOnClickListener(v3 -> onBackPressed());
    }

    private void CallApiOffersActivity(long contractorID) {
        startProcessing();
        apiInterface = RetrofitClient.getClient().create(RetrofitApiInterface.class);
        Call<OfferProgressModel> call = apiInterface.findOfferProgress(new OfferProgressRequestModel(String.valueOf(contractorID)));
        call.enqueue(new Callback<OfferProgressModel>() {
            @Override
            public void onResponse(Call<OfferProgressModel> call, Response<OfferProgressModel> response) {
                if (response.isSuccessful())
                {
                    OfferProgressModel model = response.body();
                    com.gts.coordinator.Model.ContractorData.Bonus.Getresponse response1 = model.getGetresponse();
                    int status = response1.getStatus();
                    String massage = response1.getMessage();
                    if (status == 0)
                    {
                        stopProcessing();
                        amount = response.body().getAmount();
                        endDate = response.body().getEndDate();
                        Sedan = response.body().getTotalSedan();
                        MiniSuv = response.body().getTotalMiniSuv();
                        Suv = response.body().getTotalSuv();
                        Hatchback = response.body().getTotalHatchback();
                        strTotalCabs = response.body().getTotalcab();

                        tvAmount.setText(amount);
                        offerEndDate.setText(endDate);
                        tvSedan.setText(Sedan);
                        tvHatchback.setText(Hatchback);
                        tvMini.setText(MiniSuv);
                        tvSuv.setText(Suv);
                        totalCabs.setText(strTotalCabs);
                    }
                }
            }

            @Override
            public void onFailure(Call<OfferProgressModel> call, Throwable t) {
                call.cancel();
            }
        });

    }

    private void startProcessing() {
        constraintLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void stopProcessing() {
        constraintLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

}