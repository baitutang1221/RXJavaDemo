package com.neo.base.util;


public class Utils {
    public static String getHtmlDataSmallNew(String... datas) {
        return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><link rel='stylesheet' href='file:///android_asset/newstyles_2.css' type='text/css'/></head>" + "<body>" + datas[0] + "</body></html>"; //+ summary 去掉导言
    }
}
