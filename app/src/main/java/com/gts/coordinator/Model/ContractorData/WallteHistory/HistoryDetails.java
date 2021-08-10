package com.gts.coordinator.Model.ContractorData.WallteHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HistoryDetails {
    @SerializedName("amount")
    @Expose
    private double amount;
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("net_balance")
    @Expose
    private double netBalance;
    @SerializedName("payment_summary")
    @Expose
    private String paymentSummary;
    @SerializedName("trans_date")
    @Expose
    private String transDate;
    @SerializedName("trans_id")
    @Expose
    private String transId;
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getNetBalance() {
        return netBalance;
    }

    public void setNetBalance(double netBalance) {
        this.netBalance = netBalance;
    }

    public String getPaymentSummary() {
        return paymentSummary;
    }

    public void setPaymentSummary(String paymentSummary) {
        this.paymentSummary = paymentSummary;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

}