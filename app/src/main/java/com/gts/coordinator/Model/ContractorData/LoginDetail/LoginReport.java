package com.gts.coordinator.Model.ContractorData.LoginDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginReport {

@SerializedName("d")
@Expose
public D d;

public D getD() {
return d;
}

public void setD(D d) {
this.d = d;
}

}