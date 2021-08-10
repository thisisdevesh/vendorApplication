package com.gts.coordinator.ui.notification;

import android.app.Application;
import android.app.Notification;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.gts.coordinator.Notification.Model.NotificationList;

public  class NotificationViewModel extends AndroidViewModel {
       public LiveData<PagedList<NotificationList>> itemPagedList;
       public LiveData<PageKeyedDataSource<Integer, NotificationList>> liveDataSource;
       Notification notification;

        public NotificationViewModel(Application application) {
            super(application);
           // notification.sh
            NotificationDataSourceFactory factory = new NotificationDataSourceFactory(getApplication());
            liveDataSource = factory.getItemLiveDataSource();

            PagedList.Config config =
                    (new PagedList.Config.Builder())
                            .setEnablePlaceholders(true)
                            .setPrefetchDistance(5)
                            .build();

            itemPagedList = (new LivePagedListBuilder(factory, config)).build();

        }
    }
