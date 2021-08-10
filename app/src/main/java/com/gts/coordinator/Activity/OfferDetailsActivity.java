package com.gts.coordinator.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.gts.coordinator.Adapter.OfferInstructionAdapter;
import com.gts.coordinator.Model.ContractorData.Bonus.Instructionlist;
import com.gts.coordinator.Model.ContractorData.Bonus.OfferProgressModel;
import com.gts.coordinator.Model.ContractorData.Bonus.OfferProgressRequestModel;
import com.gts.coordinator.R;
import com.gts.coordinator.Retrofit.RetrofitApiInterface;
import com.gts.coordinator.Retrofit.RetrofitClient;
import com.gts.coordinator.utils.PreferenceData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfferDetailsActivity extends AppCompatActivity {


    private RetrofitApiInterface apiInterface;
    OfferInstructionAdapter adapter;
    RecyclerView offerRecyclerView;
    private long contractorID;
    TextView t1;
    private List<Instructionlist> instructionlists = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_details);
        Toolbar toolbar =  findViewById(R.id.toolbar_offer_details);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Offer Instructions");
        offerRecyclerView = findViewById(R.id.rv_offer_inst);
        contractorID = PreferenceData.getLong(OfferDetailsActivity.this, "cont_id");

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

                        instructionlists = new ArrayList<>();
                        instructionlists = model.getInstructionlist();
                        Log.d("InstructionListSize", String.valueOf(instructionlists.size()));
                        Log.d("InstructionListSizeS", String.valueOf(model.getInstructionlist().size()));

                        setAdapter();
                    }
                }
            }
            @Override
            public void onFailure(Call<OfferProgressModel> call, Throwable t) {
                call.cancel();
            }
        });

    }

    private void setAdapter() {

        adapter = new OfferInstructionAdapter(this, instructionlists);
        offerRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false));
        offerRecyclerView.setItemAnimator(new DefaultItemAnimator());
        offerRecyclerView.setAdapter(adapter);
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