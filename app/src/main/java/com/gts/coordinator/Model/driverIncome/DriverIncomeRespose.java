package com.gts.coordinator.Model.driverIncome;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DriverIncomeRespose {

@SerializedName("getresponse")
@Expose
private Getresponse getresponse;
@SerializedName("driverdata")
@Expose
private List<Driverdatum> driverdata = null;

public Getresponse getGetresponse() {
return getresponse;
}

public void setGetresponse(Getresponse getresponse) {
this.getresponse = getresponse;
}

public List<Driverdatum> getDriverdata() {
return driverdata;
}

public void setDriverdata(List<Driverdatum> driverdata) {
this.driverdata = driverdata;
}

}