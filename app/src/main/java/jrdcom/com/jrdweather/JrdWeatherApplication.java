package jrdcom.com.jrdweather;

import android.app.Application;
import android.content.Context;

/**
 * Created by wuzhaolin on 2017/4/29.
 * 创建Application, 并获取Context
 */

public class JrdWeatherApplication extends Application{
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getAppContext(){
        return mContext;
    }
}
