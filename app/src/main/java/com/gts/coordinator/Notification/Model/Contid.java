package com.gts.coordinator.Notification.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Contid {
    @SerializedName("contid")
    @Expose
    private int cont_id;
    @SerializedName("pageno")
    @Expose
    private int pageno;


    public Contid(int cont_id, int pageno) {
        this.cont_id = cont_id;
        this.pageno = pageno;
    }

    public int getCont_id() {
        return cont_id;
    }

    public void setCont_id(int cont_id) {
        this.cont_id = cont_id;
    }

    public int getPageno() {
        return pageno;
    }

    public void setPageno(int pageno) {
        this.pageno = pageno;
    }
}
