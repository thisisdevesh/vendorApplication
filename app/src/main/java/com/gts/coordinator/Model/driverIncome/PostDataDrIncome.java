package com.gts.coordinator.Model.driverIncome;

public class PostDataDrIncome {
    String vid;
    String fromDate;
    String toDate;

    public PostDataDrIncome(String vid, String fromDate, String toDate) {
        this.vid = vid;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
}
