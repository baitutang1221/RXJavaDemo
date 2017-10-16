package com.neo.base.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import com.neo.base.R;
import com.neo.base.util.YHContext;

public class CustomDialog extends ProgressDialog {

    private static CustomDialog build;
    private boolean mCancle;
    private WaterProgress mWaterProgress;

    private CustomDialog()
    {
        super(YHContext.getInstance().getContext(),R.style.CustomDialog);
    }


    public static CustomDialog build() {
        build = new CustomDialog();
        return build;
    }
    public static CustomDialog getInstance(){
        return build;
    }



    public CustomDialog setCancle(boolean cancle){
        this.mCancle = cancle;
        return build;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        init(getContext());
    }

    private void init(Context context)
    {
        //设置不可取消，点击其他区域不能取消，实际中可以抽出去封装供外包设置
        setCancelable(mCancle);
        setCanceledOnTouchOutside(mCancle);

        setContentView(R.layout.load_dialog);
        mWaterProgress = (WaterProgress) findViewById(R.id.pb_load);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }

    @Override
    public void show()
    {
        super.show();
    }



    public void setMax(int max){
        mWaterProgress.setMaxProgress(max);
    }

    public int getMax(int max){
       return mWaterProgress.getMaxProgress();
    }

    public void setProgress(int progress){
        mWaterProgress.setProgress(progress);

    }
}
