package jrdcom.com.jrdweather.DataCache;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by longcheng on 2017/5/2.
 */

public class JrdDataCache
{
    private ConcurrentHashMap<String, Object> mHashMap;
    private static JrdDataCache mInstance = null;
    public JrdDataCache(){
        mHashMap = new ConcurrentHashMap<>();
    }

    public static JrdDataCache getInstance(){
        if(mInstance == null){
            mInstance = new JrdDataCache();
        }
        return mInstance;
    }

    public <T> void setHasMapData(String key, T t){
        if(t != null){
            mHashMap.put(key, t);
        }
    }

    public <T> T getHasMapData(String key, Class<T> clazz){
        T t = null;
        Object o = mHashMap.get(key);
        if(o == null){
            return t;
        }
        if(clazz.isInstance(o)){
            t = (T)o;
        }
        return t;

    }

    /**
     * 定义Common的key
     */

    public static final String JRD_WEATHER_CITY_NAME = "city_name";
    public static final String JRD_WEATHER_CURRENT_DATA="weather_data";
}
