package com.gts.coordinator.Model.ContractorData.UpdateVendor;

import com.google.gson.annotations.SerializedName;

public class PostUpdateVendorDetail {
    @SerializedName("vendor_id")
    long vendor_id;
    @SerializedName("vendor_phone")
    String vendor_phone ;

    public PostUpdateVendorDetail(long vendor_id, String vendor_phone) {
        this.vendor_id = vendor_id;
        this.vendor_phone = vendor_phone;
    }
}
