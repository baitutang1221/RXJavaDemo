package com.neo.base.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.king.widget.SuperSwipeRefreshLayout;
import com.neo.base.R;
import com.neo.base.model.BasePresenter;
import com.neo.base.util.TUtil;

public abstract class BasePullActiviy<T extends BasePresenter, E> extends FragmentActivity {
    RecyclerView mRecyclerView;
    protected SuperSwipeRefreshLayout mSwipeRefreshWidget;




    private T mPresenter;
    private E mModel;
    private LinearLayoutManager mLayoutManager;

    protected int page = 1;
    protected boolean loadMore;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pullrefresh);
        initData();
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (this instanceof BaseView) mPresenter.setVM(this, mModel);
        mRecyclerView = (RecyclerView) this.findViewById(android.R.id.list);
        mSwipeRefreshWidget = (SuperSwipeRefreshLayout) this.findViewById(R.id.swipe_refresh);
        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mSwipeRefreshWidget.setColorSchemeResources(
                R.color.blueColor,
                R.color.material_green_200,
                R.color.redColor,
                R.color.material_yellow_900
        );
        mSwipeRefreshWidget.setOnRefreshListener(new SuperSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SuperSwipeRefreshLayout.Direction direction) {
                if (direction == SuperSwipeRefreshLayout.Direction.TOP) {
                    onRefreshListener();
                } else if (direction == SuperSwipeRefreshLayout.Direction.BOTTOM) {
                    onLoadMoreListener();
                }
                mSwipeRefreshWidget.setRefreshing(false);
            }


        });
        initAdapter();
        getData();

    }


    protected void onLoadMoreListener() {
        ++page;
        loadMore = true;
    }

    protected void onRefreshListener() {
        page = 1;
        loadMore = false;
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }


    protected abstract void initData();

    protected abstract void initAdapter();

    protected abstract void getData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
