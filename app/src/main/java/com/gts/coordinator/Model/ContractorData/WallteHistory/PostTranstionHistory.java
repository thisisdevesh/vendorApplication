package com.gts.coordinator.Model.ContractorData.WallteHistory;

import com.google.gson.annotations.SerializedName;

public class PostTranstionHistory {
    @SerializedName("cont_id")
   private long cont_id ;
    @SerializedName("pageno")
    private int pageno;

    public PostTranstionHistory(long cont_id, int pageno) {
        this.cont_id = cont_id;
        this.pageno = pageno;
    }

    public long getCont_id() {
        return cont_id;
    }

    public void setCont_id(long cont_id) {
        this.cont_id = cont_id;
    }

    public int getPageno() {
        return pageno;
    }

    public void setPageno(int pageno) {
        this.pageno = pageno;
    }
}
