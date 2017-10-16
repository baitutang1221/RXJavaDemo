package com.example.administrator.baselibrary.utils;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.neo.base.util.FileUtil;
import com.neo.base.util.YHContext;
import com.neo.base.view.CustomDialog;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadStatus;


/**
 * 下载工具类
 */

public class DwonloadUtil {


    /**
     * 下载服务器上新版本的apk
     *
     * @param downUrl
     */
    public static void downloadAPP(String downUrl) {
        final Context mContext = YHContext.getInstance().getContext();
        RxDownload.getInstance(mContext)
                .defaultSavePath(FileUtil.getAppPath().getAbsolutePath())
                .download(downUrl, "longyuan.apk")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DownloadStatus>() {
                    @Override
                    public void accept(DownloadStatus status) throws Exception {
                        //DownloadStatus为下载进度
                        CustomDialog.getInstance().setMax((int) status.getTotalSize());
                        CustomDialog.getInstance().setProgress((int) ((int) status.getDownloadSize() - status.getTotalSize() / 10));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //下载失败
                        CustomDialog.getInstance().dismiss();
                        Toast.makeText(mContext, "下载失败！", Toast.LENGTH_SHORT).show();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        //下载成功
                        CustomDialog.getInstance().dismiss();
                        Toast.makeText(mContext, "下载成功！", Toast.LENGTH_SHORT).show();
                        File result = new File(FileUtil.getAppPath().getAbsolutePath() + "/longyuan.apk");

                        if (result == null || !result.exists() || !result.isFile()) {
                            Toast.makeText(mContext, "安装包错误，请到应用市场更新", Toast.LENGTH_SHORT).show();
                        }
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(result), "application/vnd.android.package-archive");
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }
                });
    }


}
