package jrdcom.com.jrdweather.Base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

/**
 * Created by dhcui on 2017/4/29.
 */

public abstract class BaseRelativeLayout extends RelativeLayout {
    public BaseRelativeLayout(Context context){
        super(context, null);
    }
    public BaseRelativeLayout(Context context, AttributeSet attributeSet){
        super(context, attributeSet,0 );
    }

    public BaseRelativeLayout(Context context, AttributeSet attributeSet, int defStyleAttr){
        super(context, attributeSet, defStyleAttr);
        bingView();
    }


    private void bingView(){
        LayoutInflater.from(getContext()).inflate(getLayoutId(), this, true);
        findViewId();
    }

    public abstract int getLayoutId();
    public abstract int findViewId();
}
