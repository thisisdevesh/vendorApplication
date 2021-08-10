package com.gts.coordinator.Model.ContractorData.WalateRechage;

import com.google.gson.annotations.SerializedName;

public class PostRechargeWalateDetail {
    @SerializedName("cont_id")
    long cont_id;
    @SerializedName("amount")
    double  amount;

    public PostRechargeWalateDetail(long cont_id, double amount) {
        this.cont_id = cont_id;
        this.amount = amount;
    }


}
