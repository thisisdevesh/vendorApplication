package com.gts.coordinator.Model.ContractorData.MaintenanceReport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Record {

@SerializedName("__type")
@Expose
private String type;
@SerializedName("in_date")
@Expose
private String inDate;
@SerializedName("in_time")
@Expose
private String inTime;
@SerializedName("out_date")
@Expose
private String outDate;
@SerializedName("out_time")
@Expose
private String outTime;
@SerializedName("total_maintenance_time")
@Expose
private String totalMaintenanceTime;

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public String getInDate() {
return inDate;
}

public void setInDate(String inDate) {
this.inDate = inDate;
}

public String getInTime() {
return inTime;
}

public void setInTime(String inTime) {
this.inTime = inTime;
}

public String getOutDate() {
return outDate;
}

public void setOutDate(String outDate) {
this.outDate = outDate;
}

public String getOutTime() {
return outTime;
}

public void setOutTime(String outTime) {
this.outTime = outTime;
}

public String getTotalMaintenanceTime() {
return totalMaintenanceTime;
}

public void setTotalMaintenanceTime(String totalMaintenanceTime) {
this.totalMaintenanceTime = totalMaintenanceTime;
}

}