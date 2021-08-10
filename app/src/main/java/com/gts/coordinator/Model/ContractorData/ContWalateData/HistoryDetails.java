package com.gts.coordinator.Model.ContractorData.ContWalateData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HistoryDetails {

@SerializedName("__type")
@Expose
private String type;
@SerializedName("amount")
@Expose
private double amount;
@SerializedName("cont_id")
@Expose
private double contId;
@SerializedName("id")
@Expose
private double id;
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

    public HistoryDetails(long netAmount, long amount, String transtion_id, String transtion_date, String summary) {
        this.netBalance =netAmount;
        this.amount=amount;
        this.transId=transtion_id;
        this.transDate=transtion_date;
        this.paymentSummary=summary;
    }

    public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public double getAmount() {
return amount;
}

public void setAmount(double amount) {
this.amount = amount;
}

public double getContId() {
return contId;
}

public void setContId(double contId) {
this.contId = contId;
}

public double getId() {
return id;
}

public void setId(double id) {
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