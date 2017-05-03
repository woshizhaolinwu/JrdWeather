package jrdcom.com.jrdweather.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import jrdcom.com.jrdweather.JrdWeatherApplication;

/**
 * Created by longcheng on 2017/4/21.
 */

public class SPUtil {

    public static Context mContext;
    private final static String APP_CONFIG = "app_config";
    private static SharedPreferences JrdGetSharePreference(){
        if(mContext == null){
            mContext = JrdWeatherApplication.getAppContext();
        }

        return mContext.getSharedPreferences(APP_CONFIG,Context.MODE_PRIVATE);
    }

    /*
    * 下面根据各种type来进行 preference的封装。
    * 进行优化的话，将这些方法携程一个, 根据传入的value的type来进行处理
    * */
    /*
    * writeString
    * getString
    * */
    public static void writeStringPreference(String key,String value){
        SharedPreferences.Editor editor = JrdGetSharePreference().edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String readStringPreference(String key){
        SharedPreferences preferences = JrdGetSharePreference();
        return preferences.getString(key, null);
    }

    /*
    * writeBoolean
    * getBoolean
    * */
    public static void writeBooleanPreference(String key, Boolean value){
        SharedPreferences.Editor editor = JrdGetSharePreference().edit();
        editor.putBoolean(key,value);
        editor.commit();
    }

    public static Boolean readBooleanPreference(String key){
        SharedPreferences preferences = JrdGetSharePreference();
        return preferences.getBoolean(key, false);
    }

    /**
     * 通过方法反射来进行合并
     */
    public static void writeObjectPreference(String key, Object object){
        /*解析object的类型*/
        Class<?> clazz = object.getClass();
        String objectName = clazz.getSimpleName();
        SharedPreferences.Editor editor = JrdGetSharePreference().edit();
        /*根据返回的类型来进行分别的处理*/
        if(objectName.contains("String"))
        {
            editor.putString(key, (String)object);
        }else if(objectName.contains("Boolean")){
            editor.putBoolean(key, (Boolean)object);
        }
        editor.commit();
    }

    public static Object readObjectPreference(String key, Class<?> clazz){
        String name = clazz.getSimpleName();
        SharedPreferences preferences = JrdGetSharePreference();
        if(name.contains("String")){
            return preferences.getString(key, null);
        }else if(name.contains("Boolean")){
            return preferences.getBoolean(key, false);
        }else{
            return null;
        }
    }

    //


}
