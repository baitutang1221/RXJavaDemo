package com.example.administrator.baselibrary;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.administrator.baselibrary.bean.Version;
import com.example.administrator.baselibrary.constant.Constant;
import com.example.administrator.baselibrary.net.ApiWrapper;
import com.example.administrator.baselibrary.utils.DwonloadUtil;
import com.neo.base.net.BaseSubscriber;
import com.neo.base.util.DialogUtils;
import com.neo.base.util.TelephoneManager;
import com.neo.base.util.YHContext;
import com.neo.base.view.CustomDialog;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 1，以版本更新功能为例
 *
 *,2，base库封装工具类
 *
 * 3，用XJava
 *
 */

public class MainActivity extends AppCompatActivity {

    private String doenloadURL;
    private final int MY_PERMISSIONS_REQUEST_WRITE_SETTINGS = 1;
    private final int SETTINGS_CODE = 3;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        YHContext.getInstance().setContext(this);
        checkUpdate();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkUpdate(){
        Map<String,String> map =new HashMap<>();
        map.put("channel","1");
        ApiWrapper.build().getVersion(map).subscribe(new BaseSubscriber<Version>() {
            @Override
            public void onNext(Version version) {
                Version data = version.getData();
                if(data != null){
                    float ver = Float.valueOf(data.getVersionCode());
                    doenloadURL = Constant.WEBURL + data.getDownLoadUrl();
                    float appVersion = Float.valueOf(TelephoneManager.getVersionName());
                    if(appVersion<ver){
                        DialogUtils.showQuestionDialog("提示", "发现新版本，确认下载吗?", "确定", "取消", new DialogUtils.OnClickYesListener() {
                            public void onClickYes() {
                                CustomDialog.build().setCancle(false).show();
                                checkPrPermission();
                            }
                        }, null);

                    }else{
                        Toast.makeText(MainActivity.this, "当前版本已经是最新版本!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkPrPermission() {
        if (Build.VERSION.SDK_INT < 23) {
            DwonloadUtil.downloadAPP(doenloadURL);
            return;
        }
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        boolean write = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        boolean read = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        boolean settings = Settings.System.canWrite(this);
        if (write && read && settings) {
            DwonloadUtil.downloadAPP(doenloadURL);
        } else if(!settings){
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS, Uri.parse("package:" + this.getPackageName()));
            this.startActivityForResult(intent, SETTINGS_CODE);
        }else{
            ActivityCompat.requestPermissions(this,PERMISSIONS_STORAGE,MY_PERMISSIONS_REQUEST_WRITE_SETTINGS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, final String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_SETTINGS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //授权通过
                    DwonloadUtil.downloadAPP(doenloadURL);
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_WRITE_SETTINGS);
                }
                return;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SETTINGS_CODE) {
            if (Settings.System.canWrite(this)) {
                checkPrPermission();
            }
        }
    }
}
