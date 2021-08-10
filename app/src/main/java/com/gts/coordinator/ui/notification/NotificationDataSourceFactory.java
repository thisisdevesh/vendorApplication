package com.gts.coordinator.ui.notification;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.gts.coordinator.Notification.Model.NotificationList;
import com.gts.coordinator.ui.notification.NotificationDataSource;

public class NotificationDataSourceFactory extends DataSource.Factory {
    private MutableLiveData<PageKeyedDataSource<Integer, NotificationList>> itemLiveDataSource =new MutableLiveData<>();
    Context context;

    public NotificationDataSourceFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public DataSource create() {
        NotificationDataSource itemDataSource = new NotificationDataSource(context);
        itemLiveDataSource.postValue(itemDataSource);


        return itemDataSource;
    }
    public MutableLiveData<PageKeyedDataSource<Integer, NotificationList>> getItemLiveDataSource() {
        return itemLiveDataSource;
    }
}
