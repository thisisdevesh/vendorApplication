package com.gts.coordinator.callback;

/**
 * Created by GTS02 on 09-Aug-2017 16:14.
 */

public interface TaskListener<T> {
    void onStart();
    void onSuccess(T message);
    void onFailure(T message);

}
