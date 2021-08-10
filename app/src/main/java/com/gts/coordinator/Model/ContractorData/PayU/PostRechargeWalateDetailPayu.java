package com.gts.coordinator.Model.ContractorData.PayU;

import com.google.gson.annotations.SerializedName;

public class PostRechargeWalateDetailPayu {
    @SerializedName("cont_id")
    long cont_id;
    @SerializedName("amount")
    double  amount;
    @SerializedName("trans_id")
    String trans_id;
    @SerializedName("bank_ref_no")
    String bank_ref_no;

    public PostRechargeWalateDetailPayu(long cont_id, double amount, String trans_id, String bank_ref_no) {
        this.cont_id = cont_id;
        this.amount = amount;
        this.trans_id = trans_id;
        this.bank_ref_no = bank_ref_no;
    }

}
