package com.gts.coordinator.Model.ContractorData.CheckStetus.CheckDriverSatusTesting;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckDriverStetusTesting {

    @SerializedName("Response")
    @Expose
    private Response response;
    @SerializedName("driverName")
    @Expose
    private String driverName;
    @SerializedName("driverDlNumber")
    @Expose
    private String driverDlNumber;
    @SerializedName("dlExpiryDate")
    @Expose
    private String dlExpiryDate;
    @SerializedName("driverPhone")
    @Expose
    private String driverPhone;
    @SerializedName("cabid")
    @Expose
    private String cabid;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverDlNumber() {
        return driverDlNumber;
    }

    public void setDriverDlNumber(String driverDlNumber) {
        this.driverDlNumber = driverDlNumber;
    }

    public String getDlExpiryDate() {
        return dlExpiryDate;
    }

    public void setDlExpiryDate(String dlExpiryDate) {
        this.dlExpiryDate = dlExpiryDate;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getCabid() {
        return cabid;
    }

    public void setCabid(String cabid) {
        this.cabid = cabid;
    }

}