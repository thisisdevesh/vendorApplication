
package com.gts.coordinator.Model.ContractorData.CabCategory;

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
@SerializedName("categories")
@Expose
private List<Category> categories = null;

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

public List<Category> getCategories() {
return categories;
}

public void setCategories(List<Category> categories) {
this.categories = categories;
}

}