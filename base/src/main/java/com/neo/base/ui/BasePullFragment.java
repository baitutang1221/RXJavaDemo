package com.neo.base.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.king.widget.SuperSwipeRefreshLayout;
import com.neo.base.R;
import com.neo.base.model.BasePresenter;
import com.neo.base.util.TUtil;

public abstract class BasePullFragment<T extends BasePresenter, E>  extends Fragment {


    RecyclerView mRecyclerView;
    protected SuperSwipeRefreshLayout mSwipeRefreshWidget;
    private View rootView;

    public T mPresenter;
    public E mModel;
    private LinearLayoutManager mLayoutManager;

    protected int page = 1;
    protected boolean loadMore;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_pullrefresh, container, false);
        if (rootView != null) {
            mPresenter = TUtil.getT(this, 0);
            mModel = TUtil.getT(this, 1);
            if (this instanceof BaseView) mPresenter.setVM(this, mModel);
            mRecyclerView = (RecyclerView) rootView.findViewById(android.R.id.list);
            mSwipeRefreshWidget = (SuperSwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mSwipeRefreshWidget.setColorSchemeResources(
                    R.color.blueColor,
                    R.color.material_green_200,
                    R.color.redColor,
                    R.color.material_yellow_900
            );
            initAdapter();
            getData();


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
            return rootView;
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }


    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }



    protected abstract void initData();

    protected abstract void initAdapter();

    protected abstract void getData();


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    protected void onLoadMoreListener() {
        ++page;
        loadMore = true;
    }

    protected  void onRefreshListener(){
        page = 1;
        loadMore = false;
    }
}
