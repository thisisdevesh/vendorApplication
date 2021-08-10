package com.gts.coordinator.Model.ContractorData.FcmUpdate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class D {

@SerializedName("__type")
@Expose
private String type;
@SerializedName("Message")
@Expose
private String message;
@SerializedName("Status")
@Expose
private Integer status;

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

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