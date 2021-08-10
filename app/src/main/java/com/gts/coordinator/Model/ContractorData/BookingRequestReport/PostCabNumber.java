package com.gts.coordinator.Model.ContractorData.BookingRequestReport;

import com.google.gson.annotations.SerializedName;

public class PostCabNumber {
    @SerializedName("cab_number")
    private String cab_number;
    public PostCabNumber(String cab_number) {
        this.cab_number = cab_number;
    }


}
