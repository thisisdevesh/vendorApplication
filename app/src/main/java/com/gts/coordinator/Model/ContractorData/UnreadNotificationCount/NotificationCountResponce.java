package com.gts.coordinator.Model.ContractorData.UnreadNotificationCount;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationCountResponce {

@SerializedName("getresponse")
@Expose
private Getresponse getresponse;
@SerializedName("unreadcount")
@Expose
private String unreadcount;

public Getresponse getGetresponse() {
return getresponse;
}

public void setGetresponse(Getresponse getresponse) {
this.getresponse = getresponse;
}

public String getUnreadcount() {
return unreadcount;
}

public void setUnreadcount(String unreadcount) {
this.unreadcount = unreadcount;
}

}