package com.gts.coordinator.Model.ContractorData.Bonus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Instructionlist {

    @SerializedName("instructionlist")
    @Expose
    private String instructionlist;

    public String getInstructionlist() {
        return instructionlist;
    }

    public void setInstructionlist(String instructionlist) {
        this.instructionlist = instructionlist;
    }

}