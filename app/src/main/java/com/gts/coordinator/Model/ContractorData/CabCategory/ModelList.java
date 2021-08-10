package com.gts.coordinator.Model.ContractorData.CabCategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelList {

@SerializedName("__type")
@Expose
private String type;
@SerializedName("model")
@Expose
private String model;
@SerializedName("model_id")
@Expose
private Integer modelId;

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public String getModel() {
return model;
}

public void setModel(String model) {
this.model = model;
}

public Integer getModelId() {
return modelId;
}

public void setModelId(Integer modelId) {
this.modelId = modelId;
}

}