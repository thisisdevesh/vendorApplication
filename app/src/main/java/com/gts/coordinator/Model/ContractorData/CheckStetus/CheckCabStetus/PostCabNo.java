package com.gts.coordinator.Model.ContractorData.CheckStetus.CheckCabStetus;

import com.google.gson.annotations.SerializedName;

public class PostCabNo {
    @SerializedName("cabnumber")
    private  String cabnumber;

    public PostCabNo(String cabnumber) {
        this.cabnumber = cabnumber;
    }

    public String getCabnumber() {
        return cabnumber;
    }

    public void setCabnumber(String cabnumber) {
        this.cabnumber = cabnumber;
    }
}
