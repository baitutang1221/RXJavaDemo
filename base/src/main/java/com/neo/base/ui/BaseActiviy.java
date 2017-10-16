package com.neo.base.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.neo.base.model.BasePresenter;
import com.neo.base.util.TUtil;
import com.neo.base.util.YHContext;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;


public abstract class BaseActiviy<T extends BasePresenter, E> extends AppCompatActivity {
    private boolean isEvent;
    private T mPresenter;
    private E mModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
//        ImmersionBar.with(this).init();
        setContentView(getLayoutId());
         ButterKnife.bind(this);
        YHContext.getInstance().setContext(this);
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (this instanceof BaseView) mPresenter.setVM(this, mModel);
        getData();
    }


    protected abstract void initData();

    protected abstract int  getLayoutId();

    protected abstract void getData();

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isEvent) EventBus.getDefault().unregister(this);
//        ImmersionBar.with(this).destroy();
        ButterKnife.unbind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        YHContext.getInstance().setContext(this);
    }
}
