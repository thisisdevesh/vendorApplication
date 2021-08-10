
package com.gts.coordinator.Model.ContractorData.CheckStetus.CheckVendorStetus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckVendorStetus {

    @SerializedName("d")
    @Expose
    private D d;

    public D getD() {
        return d;
    }

    public void setD(D d) {
        this.d = d;
    }

}
