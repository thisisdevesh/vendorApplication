package com.gts.coordinator.dao;

/**
 * Created by GTS Developer on 10-Mar-2017 @ 12:16
 */

public class CabModel {
  String modelName;
  long modelID;

  public CabModel(long modelID, String modelName) {
    this.modelID = modelID;
    this.modelName = modelName;
  }

  public long getModelID() {
    return modelID;
  }

  public String getModelName() {
    return modelName;
  }
}
