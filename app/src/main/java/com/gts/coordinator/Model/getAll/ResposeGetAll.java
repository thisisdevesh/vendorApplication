package com.gts.coordinator.Model.getAll;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResposeGetAll {

    @SerializedName("getresponse")
    @Expose
    private Getresponse getresponse;
    @SerializedName("drivervendorlist")
    @Expose
    private List<Drivervendorlist> drivervendorlist = null;

    public Getresponse getGetresponse() {
        return getresponse;
    }

    public void setGetresponse(Getresponse getresponse) {
        this.getresponse = getresponse;
    }

    public List<Drivervendorlist> getDrivervendorlist() {
        return drivervendorlist;
    }

    public void setDrivervendorlist(List<Drivervendorlist> drivervendorlist) {
        this.drivervendorlist = drivervendorlist;
    }

}