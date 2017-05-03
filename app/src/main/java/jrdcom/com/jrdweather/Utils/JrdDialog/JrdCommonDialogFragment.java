package jrdcom.com.jrdweather.Utils.JrdDialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by longcheng on 2017/4/13.
 */

public class JrdCommonDialogFragment extends DialogFragment {

    private boolean mCancelable = true;
    private onCallDialog mOnCallDialog;
    private onCancelListener mOnCancelListener;

    public interface onCancelListener{
        void onCancel();
    }

    public interface onCallDialog{
        Dialog getDialog(Context context);
    }


    /*
    * 构造函数
    * */
    public static JrdCommonDialogFragment getInstance(onCallDialog callDialog, boolean cancelable, onCancelListener onCancelListener){
        JrdCommonDialogFragment instance = new JrdCommonDialogFragment();
        instance.mOnCallDialog = callDialog;
        instance.mOnCancelListener  = onCancelListener;
        instance.mCancelable = cancelable;
        return instance;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if(mOnCallDialog != null){
            return mOnCallDialog.getDialog(getActivity());
        }else{
            return super.onCreateDialog(savedInstanceState);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        //设置背景边框
        if (dialog != null) {

            // 在 5.0 以下的版本会出现白色背景边框，若在 5.0 以上设置则会造成文字部分的背景也变成透明
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                // 目前只有这两个 dialog 会出现边框
                if (dialog instanceof ProgressDialog || dialog instanceof DatePickerDialog) {
                    getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
            }

            Window window = getDialog().getWindow();
            WindowManager.LayoutParams windowParams = window.getAttributes();
            windowParams.dimAmount = 0.0f;
            window.setAttributes(windowParams);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if(mOnCancelListener != null){
            mOnCancelListener.onCancel();
        }
    }
}
