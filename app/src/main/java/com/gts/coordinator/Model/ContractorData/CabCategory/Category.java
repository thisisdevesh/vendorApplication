package com.gts.coordinator.Model.ContractorData.CabCategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Category {

@SerializedName("__type")
@Expose
private String type;
@SerializedName("cat_id")
@Expose
private String catId;
@SerializedName("cat_name")
@Expose
private String catName;
@SerializedName("id")
@Expose
private double id;
@SerializedName("model_list")
@Expose
private List<ModelList> modelList = null;

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public String getCatId() {
return catId;
}

public void setCatId(String catId) {
this.catId = catId;
}

public String getCatName() {
return catName;
}

public void setCatName(String catName) {
this.catName = catName;
}

public double getId() {
return id;
}

public void setId(double id) {
this.id = id;
}

public List<ModelList> getModelList() {
return modelList;
}

public void setModelList(List<ModelList> modelList) {
this.modelList = modelList;
}

}