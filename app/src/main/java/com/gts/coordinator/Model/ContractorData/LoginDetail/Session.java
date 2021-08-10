package com.gts.coordinator.Model.ContractorData.LoginDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Session {

@SerializedName("__type")
@Expose
private String type;
@SerializedName("date")
@Expose
private String date;
@SerializedName("status")
@Expose
private String status;
@SerializedName("time")
@Expose
private String time;

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public String getDate() {
return date;
}

public void setDate(String date) {
this.date = date;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getTime() {
return time;
}

public void setTime(String time) {
this.time = time;
}

    public Session(String date, String status, String time) {
        this.date = date;
        this.status = status;
        this.time = time;
    }
}