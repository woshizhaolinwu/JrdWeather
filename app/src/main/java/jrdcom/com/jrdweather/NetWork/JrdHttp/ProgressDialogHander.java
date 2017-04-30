package jrdcom.com.jrdweather.NetWork.JrdHttp;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Message;

import jrdcom.com.jrdweather.NetWork.JrdHttp.Dialog.MyProgressDialog;
import jrdcom.com.jrdweather.NetWork.JrdHttp.Interface.ProgressCancelListener;


/**
 * Created by longcheng on 2017/3/25.
 */

public class ProgressDialogHander extends android.os.Handler {
    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISSMISS_PROGRESS_DIALOG = 2;
    private ProgressCancelListener progressCancelListener;
    private MyProgressDialog progressDialog;
    private Context mContext;
    public ProgressDialogHander(Context context, ProgressCancelListener jrdProgressCancel){
        mContext = context;
        progressCancelListener = jrdProgressCancel;
    }

    private void initProgressDialog(){
        if(progressDialog  == null){
            progressDialog = new MyProgressDialog(mContext);
            if(!progressDialog.isShowing()){
                progressDialog.show();
            }

            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    progressCancelListener.onCancelProgress();
                }
            });
        }
    }

    private void dismissProgressDialog(){
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }

    @Override
    public void handleMessage(Message msg) {
        //super.handleMessage(msg);
        switch (msg.what){
            case SHOW_PROGRESS_DIALOG:
                initProgressDialog();
                break;
            case  DISSMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
        }
    }
}
