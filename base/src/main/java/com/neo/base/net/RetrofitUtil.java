package com.neo.base.net;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitUtil<T> {
    /**
     * 服务器地址
     */
    private  static final String API_HOST = "http://eva.nextexplorer.cn/";

    private HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

    private T service;

    public  T getService(Class<?> cls) {
//        if (service == null) {
        service = (T) getRetrofit().create(cls);
//        }
        return service;
    }


    protected   Retrofit getRetrofit() {
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(getCacheInterceptor())
                .addNetworkInterceptor(logging)
                .addInterceptor(new HttpInterceptor())
                .addInterceptor(new CommonParmInterceptor())
                .cache(CacheProvide.build().getCache())
                .retryOnConnectionFailure(true)//出现错误重新连接
                .connectTimeout(50, TimeUnit.SECONDS)//设置超时时间
                .writeTimeout(50, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_HOST )
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) //添加对rxjava回调的支持
                .addConverterFactory(GsonConverterFactory.create()) //
                .build();
        return retrofit;
    }

    /**
     * 普通拦截器
     * @return
     */
    public  class HttpInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();
            Request request = null;
            request = builder.addHeader("Content-type", "application/json")
//                    .addHeader("User-Agent", ReaderApp.nativeAgent)
//                    .addHeader("token", StatisticalUtil.createPostToken())
                    .build();
            return chain.proceed(request);
        }
    }

    /**
     * 公共参数拦截器
     * @return
     */
    public  class CommonParmInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            //get请求后面追加共同的参数
            HttpUrl httpUrl = request.url().newBuilder()
                    .addQueryParameter("service", "f68693e0-9dd2-4c21-97c1-7069258da89e")
//                    .addQueryParameter("ticket", ConstantsAmount.TICKET)
//                    .addQueryParameter("kind",Constant.KIND)
//                    .addQueryParameter("top", "10")
                    .build();
            request = request.newBuilder().url(httpUrl).build();
            return chain.proceed(request);
        }
    }

    /**
     * 缓存拦截器
     * @return
     */
    public  Interceptor getCacheInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if(!NetWorkUtils.isNetworkConnected()){
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response originalResponse = chain.proceed(request);
                if (NetWorkUtils.isNetworkConnected()) {
                    int maxAge = 60; // read from cache
                    return originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public ,max-age=" + maxAge)
                            .build();
                } else {
                    int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                    return originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .build();


                }
            }


        };
    }

    /**
     * 线程转换器
     * @return
     */
    public Observable.Transformer schedulersTransformer() {
        return new Observable.Transformer() {
            @Override
            public Object call(Object observable) {
                return ((Observable)  observable).subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

}