package com.gts.coordinator.Model.ContractorData.AddCab;

import com.google.gson.annotations.SerializedName;

public class PostCabDetail {
    @SerializedName("cont_id")
    long  cont_id;
    @SerializedName("vendor_id")
    long  vendor_id;
    @SerializedName("cab_number")
    String cab_number  ;
    @SerializedName("model_id")
    long model_id ;
    @SerializedName("city_id")
    String city_id  ;
    public PostCabDetail(long cont_id, long vendor_id, String cab_number, long model_id, String city_id) {
        this.cont_id = cont_id;
        this.vendor_id = vendor_id;
        this.cab_number = cab_number;
        this.model_id = model_id;
        this.city_id = city_id;
    }
}
