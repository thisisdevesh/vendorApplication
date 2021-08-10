package com.gts.coordinator.Model.ContractorData.UpdateCab;

import com.google.gson.annotations.SerializedName;

public class PostUpdateCabDetail {
    @SerializedName("vcab_id")
    long  vcab_id;
    @SerializedName("vendor_id")
    long  vendor_id;

    public PostUpdateCabDetail(long vcab_id, long vendor_id) {
        this.vcab_id = vcab_id;
        this.vendor_id = vendor_id;
    }
}
