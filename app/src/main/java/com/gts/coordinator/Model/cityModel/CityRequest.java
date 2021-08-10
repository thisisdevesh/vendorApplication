package com.gts.coordinator.Model.cityModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityRequest {

    @SerializedName("tariffType")
    @Expose
    private String tariffType;

    public CityRequest(String out_station) {
        this.tariffType = out_station;
    }

    public String getTariffType() {
        return tariffType;
    }

    public void setTariffType(String tariffType) {
        this.tariffType = tariffType;
    }

}