package com.neo.base.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neo.base.model.BasePresenter;
import com.neo.base.util.TUtil;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;


public abstract class BaseFragment<T extends BasePresenter, E> extends Fragment {
    protected View rootView;
    private T mPresenter;
    private E mModel;
    private boolean isEvent;
    private int containerId;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initData();

        rootView = inflater.inflate(getLayoutId(), container, false);
        if(rootView!=null){
            ButterKnife.bind(this,rootView);
            mPresenter = TUtil.getT(this, 0);
            mModel = TUtil.getT(this, 1);
            if (this instanceof BaseView) mPresenter.setVM(this, mModel);
            getData();
            return rootView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected void registerEvenBus(boolean isRegister) {
        setEvent(isRegister);
        initEventBus();
    }

    private void initEventBus() {
        if (isEvent) {
            EventBus.getDefault().register(this);
        }
    }

    private void setEvent(boolean isEvent) {
        this.isEvent = isEvent;
    }

    protected abstract void initData();

    protected abstract int getLayoutId();

    protected abstract void getData();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isEvent) EventBus.getDefault().unregister(this);
        ButterKnife.unbind(this);
    }

    public int getContainerId() {
        return containerId;
    }

    public  void setContainerId(int containerId) {
        this.containerId = containerId;
    }
}
