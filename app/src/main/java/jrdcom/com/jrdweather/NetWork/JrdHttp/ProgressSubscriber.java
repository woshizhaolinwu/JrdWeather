package jrdcom.com.jrdweather.NetWork.JrdHttp;

import android.content.Context;


import jrdcom.com.jrdweather.NetWork.JrdHttp.Interface.JrdOnActionListener;
import jrdcom.com.jrdweather.NetWork.JrdHttp.Interface.JrdOnNextListener;
import jrdcom.com.jrdweather.NetWork.JrdHttp.Interface.ProgressCancelListener;
import rx.Subscriber;

/**
 * Created by dhcui on 2017/3/24.
 */

public class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {
    private Context jrdContext;
    private JrdOnNextListener<T> jrdOnNextListener;
    private JrdOnActionListener.JrdOnCompleteListener jrdOnCompleteListener;
    private ProgressDialogHander jrdProgressDialog;
    StringBuilder jrdStringBuiler;
    public ProgressSubscriber(Context context, JrdOnNextListener<T> listener, boolean isShow){
        jrdContext = context;
        jrdOnNextListener = listener;
        if(isShow == true)
        jrdProgressDialog = new ProgressDialogHander(context, this);
        jrdStringBuiler =  new StringBuilder();
    }

    public ProgressSubscriber(Context context, JrdOnNextListener<T> listener, JrdOnActionListener.JrdOnCompleteListener completeListener,  boolean isShow){
        jrdContext = context;
        jrdOnNextListener = listener;
        jrdOnCompleteListener = completeListener;
        if(isShow == true)
        jrdProgressDialog = new ProgressDialogHander(context, this);
        jrdStringBuiler =  new StringBuilder();
    }
    @Override
    public void onStart() {
        super.onStart();
        showDialog();
    }

    @Override
    public void onCompleted() {
        //Toast.makeText(jrdContext,"onCompleted",Toast.LENGTH_LONG).show();
        //TestPrsent testPrsent = (TestPrsent)jrdContext;
        //testPrsent.setText("test");
        dissmissDialog();
        if(jrdOnCompleteListener != null){
            jrdOnCompleteListener.OnComplete();
        }
    }

    @Override
    public void onError(Throwable e) {
        dissmissDialog();
        //Toast.makeText(jrdContext,"onError",Toast.LENGTH_LONG).show();
    }

    //Nest需要自己执行，传入会调借口
    @Override
    public void onNext(T t) {
        if(jrdOnNextListener != null){}
        jrdOnNextListener.OnNext(t);
    }

    @Override
    public void onCancelProgress() {
        if(!this.isUnsubscribed()){
            this.unsubscribe();
        }
    }

    public void showDialog(){
        if(jrdProgressDialog != null)
        jrdProgressDialog.obtainMessage(1).sendToTarget();
    }

    public void dissmissDialog(){
        if(jrdProgressDialog != null){
            jrdProgressDialog.obtainMessage(2).sendToTarget();
            jrdProgressDialog = null;
        }
    }
}
