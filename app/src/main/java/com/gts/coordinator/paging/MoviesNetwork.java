package com.gts.coordinator.paging;


/**
 * Created by Elad on 6/25/2018.
 */

public class MoviesNetwork {

    final private static String TAG = MoviesNetwork.class.getSimpleName();
 //   final private LiveData<PagedList<NotificationList>> moviesPaged;
 //   final private LiveData<NetworkState> networkState;

/*
    public MoviesNetwork(NotificationDataSourceFactory dataSourceFactory, PagedList.BoundaryCallback<NotificationList> boundaryCallback){
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                .setInitialLoadSizeHint(10).setPageSize(10).build();
        networkState = Transformations.switchMap(dataSourceFactory.getNetworkStatus(),
                (Function<NotificationDataSource, LiveData<NetworkState>>)
                        NotificationDataSource::getNetworkState);
        Executor executor = Executors.newFixedThreadPool(3);
        LivePagedListBuilder livePagedListBuilder = new LivePagedListBuilder(dataSourceFactory, pagedListConfig);
        moviesPaged = livePagedListBuilder.
                setFetchExecutor(executor).
                setBoundaryCallback(boundaryCallback).
                build();

    }
*/


/*
    public LiveData<PagedList<NotificationList>> getPagedMovies(){
        return moviesPaged;
    }
*/



/*
    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }
*/

}
