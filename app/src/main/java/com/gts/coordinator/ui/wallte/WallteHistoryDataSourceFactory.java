package com.gts.coordinator.ui.wallte;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;
import com.gts.coordinator.Model.ContractorData.WallteHistory.History;
public class WallteHistoryDataSourceFactory extends DataSource.Factory {
    private MutableLiveData<PageKeyedDataSource<Integer,History>> itemLiveDataSource =new MutableLiveData<>();
    Context context;

    public WallteHistoryDataSourceFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public DataSource create() {
        WalletHistoryDataSource itemDataSource = new WalletHistoryDataSource(context);
        itemLiveDataSource.postValue(itemDataSource);

      ///  itemDataSource.showPrograsBarData();


        return itemDataSource;
    }
    public MutableLiveData<PageKeyedDataSource<Integer, History>> getItemLiveDataSource() {
        return itemLiveDataSource;
    }
}
