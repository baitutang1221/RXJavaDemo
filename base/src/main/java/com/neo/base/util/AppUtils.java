package com.neo.base.util;

import android.content.Context;
import android.content.Intent;

import java.util.Map;


public class AppUtils {
    /**
     * 带字符串的intent
     * @param cls
     * @param map
     */
    public static void toMapClass( Class<?> cls, Map<String,String> map){
        Intent in = new Intent(YHContext.getInstance().getContext(), cls);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            in.putExtra(entry.getKey(), entry.getValue());
        }
        YHContext.getInstance().getContext().startActivity(in);
    }

    /**
     * 不带字符串的
     * @param cls
     */
    public static void toClass(Class<?> cls){
        Context context = YHContext.getInstance().getContext();
        context.startActivity(new Intent(context, cls));
    }






}
