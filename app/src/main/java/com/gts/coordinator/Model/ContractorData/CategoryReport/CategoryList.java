package com.gts.coordinator.Model.ContractorData.CategoryReport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryList {

@SerializedName("__type")
@Expose
private String type;
@SerializedName("actual_cat")
@Expose
private String actualCat;
@SerializedName("attempt")
@Expose
private Integer attempt;
@SerializedName("cat_id")
@Expose
private String catId;
@SerializedName("cat_name")
@Expose
private String catName;
@SerializedName("date")
@Expose
private String date;
@SerializedName("time")
@Expose
private String time;

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public String getActualCat() {
return actualCat;
}

public void setActualCat(String actualCat) {
this.actualCat = actualCat;
}

public Integer getAttempt() {
return attempt;
}

public void setAttempt(Integer attempt) {
this.attempt = attempt;
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

public String getDate() {
return date;
}

public void setDate(String date) {
this.date = date;
}

public String getTime() {
return time;
}

public void setTime(String time) {
this.time = time;
}

}