package com.gts.coordinator.Model.ContractorData.WalateRechage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class D {

@SerializedName("__type")
@Expose
private String type;
@SerializedName("Response")
@Expose
private Response response;
@SerializedName("payment_url")
@Expose
private String paymentUrl;

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public Response getResponse() {
return response;
}

public void setResponse(Response response) {
this.response = response;
}

public String getPaymentUrl() {
return paymentUrl;
}

public void setPaymentUrl(String paymentUrl) {
this.paymentUrl = paymentUrl;
}

}