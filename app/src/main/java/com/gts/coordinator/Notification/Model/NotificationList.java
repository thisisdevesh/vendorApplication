
package com.gts.coordinator.Notification.Model;
import android.graphics.Movie;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class NotificationList   {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("is_read")
    @Expose
    private Boolean isRead;
    @SerializedName("id")
    @Expose
    private Integer id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static DiffUtil.ItemCallback<NotificationList> DIFF_CALLBACK = new DiffUtil.ItemCallback<NotificationList>() {
        @Override
        public boolean areItemsTheSame(@NonNull NotificationList oldItem, @NonNull NotificationList newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull NotificationList oldItem, @NonNull NotificationList newItem) {
            return oldItem.getId().equals(newItem.getId());
        }


    };


}
