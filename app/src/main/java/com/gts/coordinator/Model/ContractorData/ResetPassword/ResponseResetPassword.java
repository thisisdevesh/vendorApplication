package com.gts.coordinator.Model.ContractorData.ResetPassword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseResetPassword {

@SerializedName("Message")
@Expose
private String message;
@SerializedName("Status")
@Expose
private Integer status;

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public Integer getStatus() {
return status;
}

public void setStatus(Integer status) {
this.status = status;
}

}