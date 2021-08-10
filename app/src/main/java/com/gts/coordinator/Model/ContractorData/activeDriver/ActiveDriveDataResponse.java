package com.gts.coordinator.Model.ContractorData.activeDriver;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ActiveDriveDataResponse {

@SerializedName("Message")
@Expose
private String message;
@SerializedName("Status")
@Expose
private Integer status;
@SerializedName("driverData")
@Expose
private List<DriverDatum> driverData = null;

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public Integer getStatus() {
return status;
}

public void setStatus(Integer status) {
this.status = status;
}

public List<DriverDatum> getDriverData() {
return driverData;
}

public void setDriverData(List<DriverDatum> driverData) {
this.driverData = driverData;
}

}