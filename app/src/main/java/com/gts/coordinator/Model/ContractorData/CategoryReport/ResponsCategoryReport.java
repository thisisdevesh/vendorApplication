package com.gts.coordinator.Model.ContractorData.CategoryReport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponsCategoryReport {

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