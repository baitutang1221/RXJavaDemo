package com.neo.base.model;

import android.content.Context;


public class BasePresenter<M, T> {
    public Context context;
    public M mModel;
    public T mView;
    public void setVM(T v, M m) {
        this.mView = v;
        this.mModel = m;
    }
}
