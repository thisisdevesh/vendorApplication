package com.gts.coordinator.Model.ContractorData.AddVendor;

import com.google.gson.annotations.SerializedName;

public class PostVendorDetail {
    @SerializedName("vendor_name")
    String vendor_name ;
    @SerializedName("vendor_email")
    String vendor_email ;
    @SerializedName("vendor_phone")
    String vendor_phone ;
    @SerializedName("vendor_add")
    String vendor_address ;
    @SerializedName("cont_id")
    long cont_id ;
    @SerializedName("city_id")
    String city_id ;


    public PostVendorDetail(String vendor_name, String vendor_email, String vendor_phone, String vendor_address, long cont_id, String city_id) {
        this.vendor_name = vendor_name;
        this.vendor_email = vendor_email;
        this.vendor_phone = vendor_phone;
        this.vendor_address = vendor_address;
        this.cont_id = cont_id;
        this.city_id = city_id;
    }
}
