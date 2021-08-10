package com.gts.coordinator.ui.wallte;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.gts.coordinator.Model.ContractorData.WallteHistory.History;
import com.gts.coordinator.ui.wallte.WallteHistoryDataSourceFactory;

public class WalletHistoryViewModel extends AndroidViewModel {
   public LiveData<PagedList<History>> itemPagedList;
   public LiveData<PageKeyedDataSource<Integer, History>> liveDataSource;
    public WalletHistoryViewModel(Application application) {


        super(application);

        WallteHistoryDataSourceFactory factory = new WallteHistoryDataSourceFactory(getApplication());
        liveDataSource = factory.getItemLiveDataSource();

        PagedList.Config config =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(true)
                        .setPrefetchDistance(5)
                        .build();

        itemPagedList = (new LivePagedListBuilder(factory, config)).build();



    }
    public int showData(int a){
        return a ;
    }
    public int getNetworkState(int a) {

        return a;
    }


}
