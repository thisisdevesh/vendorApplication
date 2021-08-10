package com.gts.coordinator.Model.ContractorData.ContWalateData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostContid {
    @SerializedName("cont_id")
    @Expose
     long cont_id;

    public PostContid(long cont_id) {
        this.cont_id = cont_id;
    }
}
