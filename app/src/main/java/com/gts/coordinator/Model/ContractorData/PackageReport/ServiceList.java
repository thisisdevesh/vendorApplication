package com.gts.coordinator.Model.ContractorData.PackageReport;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceList {

@SerializedName("__type")
@Expose
private String type;
@SerializedName("attempt")
@Expose
@Nullable
private Integer attempt;
@SerializedName("date")
@Expose
private String date;
@SerializedName("plan_type")
@Expose
private String planType;
@SerializedName("time")
@Expose
private String time;

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public Integer getAttempt() {
return attempt;
}

public void setAttempt(Integer attempt) {
this.attempt = attempt;
}

public String getDate() {
return date;
}

public void setDate(String date) {
this.date = date;
}

public String getPlanType() {
return planType;
}

public void setPlanType(String planType) {
this.planType = planType;
}

public String getTime() {
return time;
}

public void setTime(String time) {
this.time = time;
}

}