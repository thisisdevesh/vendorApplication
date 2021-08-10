package com.gts.coordinator.Model.ContractorData.CategoryReport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class D {

@SerializedName("__type")
@Expose
private String type;
@SerializedName("Response")
@Expose
private Response response;
@SerializedName("category_list")
@Expose
private List<CategoryList> categoryList = null;

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public Response getResponse() {
return response;
}

public void setResponse(Response response) {
this.response = response;
}

public List<CategoryList> getCategoryList() {
return categoryList;
}

public void setCategoryList(List<CategoryList> categoryList) {
this.categoryList = categoryList;
}

}