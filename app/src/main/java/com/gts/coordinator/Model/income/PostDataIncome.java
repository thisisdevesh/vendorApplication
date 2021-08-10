package com.gts.coordinator.Model.income;

public class PostDataIncome {
    String contid;
    String fromDate;
    String toDate;

    public PostDataIncome(String contid, String fromDate, String toDate) {
        this.contid = contid;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public String getContid() {
        return contid;
    }

    public void setContid(String contid) {
        this.contid = contid;
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
