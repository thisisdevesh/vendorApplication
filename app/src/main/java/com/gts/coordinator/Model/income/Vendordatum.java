package com.gts.coordinator.Model.income;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Vendordatum {

@SerializedName("vid")
@Expose
private String vid;
@SerializedName("vendorname")
@Expose
private String vendorname;
@SerializedName("totalincome")
@Expose
private Double totalincome;
@SerializedName("totalbooking")
@Expose
private String totalbooking;

public String getVid() {
return vid;
}

public void setVid(String vid) {
this.vid = vid;
}

public String getVendorname() {
return vendorname;
}

public void setVendorname(String vendorname) {
this.vendorname = vendorname;
}

public Double getTotalincome() {
return totalincome;
}

public void setTotalincome(Double totalincome) {
this.totalincome = totalincome;
}

public String getTotalbooking() {
return totalbooking;
}

public void setTotalbooking(String totalbooking) {
this.totalbooking = totalbooking;
}

    public Vendordatum(String vid, String vendorname, Double totalincome, String totalbooking) {
        this.vid = vid;
        this.vendorname = vendorname;
        this.totalincome = totalincome;
        this.totalbooking = totalbooking;
    }
}