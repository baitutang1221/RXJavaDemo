package com.neo.base.util;

import java.util.regex.Pattern;


public class UiHelp {
    private static final UiHelp ourInstance = new UiHelp();

    public static UiHelp getInstance() {
        return ourInstance;
    }

    private UiHelp() {
    }

    /**
     * @param time
     * @return
     */
    public String long2Time(long time) {
        int x = (int) time / 1000 / 60 / 60;//小时
        int f = (int) time / 1000 / 60 % 60;//分钟
        int s = (int) time / 1000 % 60;//秒
        String cx = "";
        String cf = "";
        String cs = "";

        if (x != 0) {
            if (x < 10)
                cx += "0";
            cx += x + ":";
        }

        if (f != 0) {
            if (f < 10)
                cf += "0";
            cf += f + ":";
        } else {
            cf = "00:";
        }

        if (s != 0) {
            if (s < 10)
                cs += "0";
            cs += s;
        } else
            cs = "00";

        return cx + cf + cs;
    }

    public  boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
}
