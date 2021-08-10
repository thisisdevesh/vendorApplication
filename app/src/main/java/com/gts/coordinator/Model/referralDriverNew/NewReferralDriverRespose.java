package com.gts.coordinator.Model.referralDriverNew;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewReferralDriverRespose {

@SerializedName("getresponse")
@Expose
private Getresponse getresponse;
@SerializedName("drivercablistverify")
@Expose
private List<Drivercablistverify> drivercablistverify = null;

public Getresponse getGetresponse() {
return getresponse;
}

public void setGetresponse(Getresponse getresponse) {
this.getresponse = getresponse;
}

public List<Drivercablistverify> getDrivercablistverify() {
return drivercablistverify;
}

public void setDrivercablistverify(List<Drivercablistverify> drivercablistverify) {
this.drivercablistverify = drivercablistverify;
}

}