package com.gts.coordinator.Model.ContractorData.TransferMoney;

import com.google.gson.annotations.SerializedName;

public class PostTransferMoneyDetail {
    @SerializedName("tr_amount")
    double ammount;
    @SerializedName("trf_id")
    long conId;
    @SerializedName("trf_name")
    String contName;
    @SerializedName("trt_id")
    long venID;
    @SerializedName("trt_name")
    String venName;
    @SerializedName("trf_phone")
    String contPhno;
    @SerializedName("trt_phone")
    String venPhno;

    public PostTransferMoneyDetail(double ammount, long conId, String contName, long venID, String venName, String contPhno, String venPhno) {
        this.ammount = ammount;
        this.conId = conId;
        this.contName = contName;
        this.venID = venID;
        this.venName = venName;
        this.contPhno = contPhno;
        this.venPhno = venPhno;
    }
}