package com.gts.coordinator.Model.referralDriver;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReferralDriverRespose {

@SerializedName("getresponse")
@Expose
private Getresponse getresponse;
@SerializedName("drivercablist")
@Expose
private List<Drivercablist> drivercablist = null;

public Getresponse getGetresponse() {
return getresponse;
}

public void setGetresponse(Getresponse getresponse) {
this.getresponse = getresponse;
}

public List<Drivercablist> getDrivercablist() {
return drivercablist;
}

public void setDrivercablist(List<Drivercablist> drivercablist) {
this.drivercablist = drivercablist;
}

}