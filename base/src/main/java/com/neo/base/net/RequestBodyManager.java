package com.neo.base.net;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;


public class RequestBodyManager {
    /**
     * 生成对应的RequestBody
     *
     * @param param
     * @return
     */
    public static RequestBody createRequestBody(int param) {
        return RequestBody.create(MediaType.parse("text/plain"), String.valueOf(param));
    }

    public static RequestBody createRequestBody(long param) {
        return RequestBody.create(MediaType.parse("text/plain"), String.valueOf(param));
    }

    public static RequestBody createRequestBody(String param) {
        return RequestBody.create(MediaType.parse("text/plain"), param);
    }

    public static RequestBody createRequestBody(File param) {
        return RequestBody.create(MediaType.parse("image/*"), param);
    }

    public static RequestBody createJsonRequestBody(String param) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), param);
    }
}
