package com.gts.coordinator.Notification.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationResponce {

@SerializedName("hashmore")
@Expose
private Boolean hashmore;
@SerializedName("getresponse")
@Expose
private Getresponse getresponse;
@SerializedName("noti")
@Expose
private List<NotificationList> noti = null;

public Boolean getHashmore() {
return hashmore;
}

public void setHashmore(Boolean hashmore) {
this.hashmore = hashmore;
}

public Getresponse getGetresponse() {
return getresponse;
}

public void setGetresponse(Getresponse getresponse) {
this.getresponse = getresponse;
}

public List<NotificationList> getNoti() {
return noti;
}

public void setNoti(List<NotificationList> noti) {
this.noti = noti;
}

}