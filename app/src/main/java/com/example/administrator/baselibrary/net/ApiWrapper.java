package com.example.administrator.baselibrary.net;

import com.example.administrator.baselibrary.bean.Version;
import com.neo.base.net.RetrofitUtil;

import java.util.Map;

import rx.Observable;

/**
 * 网络请求实现类
 *
 */
public class ApiWrapper extends RetrofitUtil<APIService> {

    private static ApiWrapper apiWrapper = null;

    public static ApiWrapper build() {
        if (apiWrapper == null)
            apiWrapper = new ApiWrapper();
        return apiWrapper;
    }

    public APIService getService() {
        return getService(APIService.class);
    }


    /**
     * 获取服务器上应用的版本号以及下载地址等信息 （在此实现方法）
     *
     * @param map
     * @return
     */
    public Observable<Version> getVersion(Map<String, String> map) {
        return getService().getVersion(map).compose(schedulersTransformer());
    }

}