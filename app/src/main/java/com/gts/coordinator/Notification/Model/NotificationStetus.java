package com.gts.coordinator.Notification.Model;

import com.google.gson.annotations.SerializedName;

public class NotificationStetus {
    @SerializedName("is_read")
  private int is_read;
    @SerializedName("id")
  private int id;

    public NotificationStetus(int is_read, int id) {
        this.is_read = is_read;
        this.id = id;
    }

}
