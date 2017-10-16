package com.neo.base.util;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DialogUtils {


    /**
     * 提问框的 Listener
     */
    public interface OnClickYesListener {
        void onClickYes();
    }

    /**
     * 提问框的 Listener
     *
     */
    public interface OnClickNoListener {
        void onClickNo();
    }

    public static void showQuestionDialog( String title,
                                          String text, String confirm, String cancle, final OnClickYesListener listenerYes,
                                          final OnClickNoListener listenerNo) {

        Builder builder = new AlertDialog.Builder(YHContext.getInstance().getContext());

        if (!isBlank(text)) {
            // 此方法为dialog写布局
            final TextView textView = new TextView(YHContext.getInstance().getContext());
            textView.setText(text);
            LinearLayout layout = new LinearLayout(YHContext.getInstance().getContext());
            layout.setPadding(50, 20, 10, 0);
            layout.addView(textView, new LayoutParams(LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
            builder.setView(layout);
        }
        // 设置title
        builder.setTitle(title);
        // 设置确定按钮，固定用法声明一个按钮用这个setPositiveButton
        builder.setPositiveButton(confirm,
                new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // 如果确定被电击
                        if (listenerYes != null) {
                            listenerYes.onClickYes();
                        }
                    }
                });
        // 设置取消按钮，固定用法声明第二个按钮要用setNegativeButton
        builder.setNegativeButton(cancle,
                new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // 如果取消被点击
                        if (listenerNo != null) {
                            listenerNo.onClickNo();
                        }
                    }
                });

        // 控制这个dialog可不可以按返回键，true为可以，false为不可以
        builder.setCancelable(false);
        // 显示dialog
        builder.create().show();

    }

    /**
     * 处理字符的方法
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }
}
