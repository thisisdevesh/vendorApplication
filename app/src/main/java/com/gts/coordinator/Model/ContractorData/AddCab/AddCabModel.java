package com.gts.coordinator.Model.ContractorData.AddCab;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddCabModel {

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