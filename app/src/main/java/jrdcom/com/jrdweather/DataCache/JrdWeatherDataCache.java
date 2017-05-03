package jrdcom.com.jrdweather.DataCache;

import jrdcom.com.jrdweather.NetWork.JrdHttp.Beans.JrdWeatherBean;
import jrdcom.com.jrdweather.Utils.GsonUtils;
import jrdcom.com.jrdweather.Utils.JrdCommon;
import jrdcom.com.jrdweather.Utils.SPUtil;

/**
 * Created by longcheng on 2017/5/3.
 */

public class JrdWeatherDataCache {
    private static JrdWeatherDataCache jrdWeatherDataCache = null;
    public JrdWeatherDataCache(){

    }

    public static JrdWeatherDataCache getInstance(){
        if(jrdWeatherDataCache == null){
            jrdWeatherDataCache = new JrdWeatherDataCache();
        }
        return jrdWeatherDataCache;
    }

    /*
    * 存取CityName
    * */
    public void saveWeatherName(String CityName){
        SPUtil.writeObjectPreference(JrdCommon.WEATHER_CITY, CityName);
    }

    public String getWeatherName(){
        String weatherName = (String) SPUtil.readObjectPreference(JrdCommon.WEATHER_CITY, String.class);
        return weatherName;
    }

    /*
    * 存取WeatherData
    * */
    public void saveWeatherData(JrdWeatherBean weatherBean){
        /*将Data转化为String*/
        String weather = GsonUtils.toJson(weatherBean);
        SPUtil.writeObjectPreference(JrdCommon.WEATHER_DATA, weather);
    }

    public JrdWeatherBean getWeatherData(String cityName){
        String weather = (String) SPUtil.readObjectPreference(JrdCommon.WEATHER_DATA, String.class);
        JrdWeatherBean weatherBean = GsonUtils.fromJson(weather, JrdWeatherBean.class);
        if(weatherBean == null){
            return weatherBean;
        }
        String weatherCity = weatherBean.getResult().getToday().getCity();
        if(cityName.contains(weatherCity)||cityName.equalsIgnoreCase(weatherCity)){
            return weatherBean;
        }else {
            return null;
        }
    }
}
