package com.gts.coordinator.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.gts.coordinator.Adapter.OfferAdapter;
import com.gts.coordinator.Model.ContractorData.Bonus.OfferProgressModel;
import com.gts.coordinator.Model.ContractorData.Bonus.OfferProgressRequestModel;
import com.gts.coordinator.R;
import com.gts.coordinator.Retrofit.RetrofitApiInterface;
import com.gts.coordinator.Retrofit.RetrofitClient;
import com.gts.coordinator.utils.PreferenceData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityOfferList extends AppCompatActivity {

    private RecyclerView offersRecyclerView;
    private OfferAdapter offerAdapter;
   /* private ArrayList<String> list;*/
    private TextView tvNoOffert;
    private String imageUrl;
    private long contractorID;
    private RetrofitApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_list);

        Toolbar toolbar = findViewById(R.id.toolbar_offers);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Offers");
        tvNoOffert = findViewById(R.id.noOffer);
        offersRecyclerView = findViewById(R.id.offers_recycler_view);

        contractorID = PreferenceData.getLong(ActivityOfferList.this, "cont_id");
        CallApiOffersActivity(contractorID);


    }


    private void CallApiOffersActivity(long contractorID) {
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
                        if (tvNoOffert.getVisibility()==View.VISIBLE){
                            tvNoOffert.setVisibility(View.GONE);
                        }
                        imageUrl = model.getImageUrl();
                        setRecyclerView();
                    }
                    else if (status == 2)
                    {
                        if (tvNoOffert.getVisibility()==View.GONE){
                            tvNoOffert.setVisibility(View.VISIBLE);
                        }
                    }


                }
            }

            @Override
            public void onFailure(Call<OfferProgressModel> call, Throwable t) {
                call.cancel();
            }
        });

    }

    public void setRecyclerView()
    {
        offerAdapter =new OfferAdapter(this,imageUrl);
        offersRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        offersRecyclerView.setItemAnimator(new DefaultItemAnimator());
        offersRecyclerView.setAdapter(offerAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}