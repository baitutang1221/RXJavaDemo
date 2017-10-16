package com.neo.base.ui;

import android.content.Context;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.neo.base.R;
import com.neo.base.util.YHContext;


public abstract class BaseWebViewActivity extends BaseActiviy {
    protected WebView mWebView;







    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void getData() {
        mWebView = (WebView) this.findViewById(R.id.web_view);
        webViewSetting();


    }

    protected  void loadUrl(){}


    private void webViewSetting() {
        WebSettings  wb = mWebView.getSettings();
        wb.setJavaScriptEnabled(true);
        // 设置数据缓存
        wb.setDomStorageEnabled(true);
        String appcathDir = YHContext.getInstance().getContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        wb.setAppCachePath(appcathDir);
        wb.setAllowFileAccess(true);
        wb.setAppCacheEnabled(true);
        wb.setCacheMode(WebSettings.LOAD_DEFAULT);
        wb.setLoadsImagesAutomatically(true);  //支持自动加载图片
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(mWebViewClient);
    }


    public WebViewClient mWebViewClient = new WebViewClient() {


        public void onPageFinished(final WebView view, final String url) {
            super.onPageFinished(view, url);
            if (view != null) {
                if (!view.getSettings().getLoadsImagesAutomatically()) {
                    view.getSettings().setLoadsImagesAutomatically(true);
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
