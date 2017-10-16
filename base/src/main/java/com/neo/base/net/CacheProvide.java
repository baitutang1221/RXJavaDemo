package com.neo.base.net;

import com.neo.base.util.YHContext;

import java.io.File;

import okhttp3.Cache;

/**
 *
 * 缓存提供器 主要配置修改缓存的文件目录
 */

public class CacheProvide {
//    File cache=new File(Environment.getExternalStorageDirectory()+"/Neo") ;
    File cache = new File(YHContext.getInstance().getContext().getCacheDir(), "LongYuan");


    private static final CacheProvide ourInstance = new CacheProvide();

    public static CacheProvide build() {
        return ourInstance;
    }


    private CacheProvide() {
    }

    public Cache getCache() {//使用应用缓存文件路径，缓存大小为10MB
        return new Cache(cache, 1024* 1024*10);
    }

}
