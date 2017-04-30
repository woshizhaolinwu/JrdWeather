package jrdcom.com.jrdweather.NetWork.JrdHttp.Dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import jrdcom.com.jrdweather.R;


public class MyProgressDialog extends ProgressDialog {

    private ProgressWheel loadingPW;

    public MyProgressDialog(Context context) {
        this(context, R.style.MyProgressDialogDialog);
    }

    public MyProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_progress_dialog);
        loadingPW = (ProgressWheel) findViewById(R.id.interpolated);
        loadingPW.setVisibility(View.VISIBLE);
        setCanceledOnTouchOutside(false);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
    }

    @Override
    public void dismiss() {
        try {
            loadingPW.setVisibility(View.GONE);
            if (this.isShowing()) {      //退出报错：View not attached to window manager
                super.dismiss();
            }
        } catch (Exception e) {
        }
    }

}
