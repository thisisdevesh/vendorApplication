package com.gts.coordinator.dao;

/**
 * Created by GTS Developer on 10-Mar-2017 @ 11:41
 */

public class CabCategory {
  String categoryName, categoryCode;
  long categoryID;
  public  CabCategory()
  {}

  public CabCategory(String categoryCode, long categoryID, String categoryName) {
    this.categoryCode = categoryCode;
    this.categoryID = categoryID;
    this.categoryName = categoryName;
  }

  public String getCategoryCode() {
    return categoryCode;
  }

  public long getCategoryID() {
    return categoryID;
  }

  public String getCategoryName() {
    return categoryName;
  }
}
