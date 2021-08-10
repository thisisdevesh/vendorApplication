package com.gts.coordinator.Model.ContractorData.PayU;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PayuApiResponce {

@SerializedName("getresponse")
@Expose
private Getresponse getresponse;

public Getresponse getGetresponse() {
return getresponse;
}

public void setGetresponse(Getresponse getresponse) {
this.getresponse = getresponse;
}

}