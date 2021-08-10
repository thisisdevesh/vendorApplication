package com.gts.coordinator.Model.income;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IncomeRespose {

@SerializedName("getresponse")
@Expose
private Getresponse getresponse;
@SerializedName("vendordata")
@Expose
private List<Vendordatum> vendordata = null;

public Getresponse getGetresponse() {
return getresponse;
}

public void setGetresponse(Getresponse getresponse) {
this.getresponse = getresponse;
}

public List<Vendordatum> getVendordata() {
return vendordata;
}

public void setVendordata(List<Vendordatum> vendordata) {
this.vendordata = vendordata;
}

}