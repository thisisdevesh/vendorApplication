package com.gts.coordinator.Model.ContractorData.WalateRechage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WaletRechageModel {

@SerializedName("d")
@Expose
private D d;

public D getD() {
return d;
}

public void setD(D d) {
this.d = d;
}

}