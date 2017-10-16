package com.example.administrator.baselibrary.net;


import com.example.administrator.baselibrary.bean.Version;
import com.example.administrator.baselibrary.constant.Constant;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * 网络请求服务接口
 *
 */
public interface APIService {

    /**
     * 获取服务器上应用的版本号以及下载地址等信息
     *
     * @param map
     * @return
     */
    @GET(Constant.WEBURL+"/api/AppService/GetLatestVersionOfAndroid")
    Observable<Version> getVersion(@QueryMap Map<String, String> map);

}
