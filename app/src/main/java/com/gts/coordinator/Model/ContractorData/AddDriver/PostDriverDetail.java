package com.gts.coordinator.Model.ContractorData.AddDriver;

import com.google.gson.annotations.SerializedName;

public class PostDriverDetail {
    @SerializedName("vendor_id")
    long vendor_id;
    @SerializedName("cont_id")
    long cont_id;
    @SerializedName("cab_number")
    String cab_number;
    @SerializedName("driver_name")
    String driver_name;
    @SerializedName("driver_phone")
    String driver_phone;
    @SerializedName("driver_dl_number")
    String driver_dl_number;
    @SerializedName("driver_dl_expiry_date")
    String driver_dl_expiry_date;
    @SerializedName("status")
    private String status;

    public PostDriverDetail(long vendor_id, long cont_id, String cab_number, String driver_name, String driver_phone, String driver_dl_number, String driver_dl_expiry_date, String status) {
        this.vendor_id = vendor_id;
        this.cont_id = cont_id;
        this.cab_number = cab_number;
        this.driver_name = driver_name;
        this.driver_phone = driver_phone;
        this.driver_dl_number = driver_dl_number;
        this.driver_dl_expiry_date = driver_dl_expiry_date;
        this.status = status;
    }
}
