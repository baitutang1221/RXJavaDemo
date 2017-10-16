package com.neo.base.net;

import android.util.Log;

import rx.Subscriber;

public abstract class BaseSubscriber<T> extends Subscriber<T> {

    @Override
    public void onCompleted() {
        Log.i("onCompleted", "onCompleted");
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public abstract void onNext(T t);

}
