package jrdcom.com.jrdweather.Model;

import jrdcom.com.jrdweather.BaiDuLocation.JrdBaiDuLocationManager;
import jrdcom.com.jrdweather.DataCache.JrdDataCache;
import jrdcom.com.jrdweather.DataCache.JrdWeatherDataCache;
import jrdcom.com.jrdweather.JrdWeatherApplication;
import jrdcom.com.jrdweather.NetWork.JrdHttp.Beans.JrdWeatherBean;
import jrdcom.com.jrdweather.NetWork.JrdHttp.Interface.JrdOnNextListener;
import jrdcom.com.jrdweather.NetWork.JrdHttp.JrdHttpMethod;
import jrdcom.com.jrdweather.NetWork.JrdHttp.ProgressSubscriber;
import jrdcom.com.jrdweather.Ui.Splash.SplashConstract;
import jrdcom.com.jrdweather.Utils.LogUtils;
import jrdcom.com.jrdweather.Utils.SPUtil;

/**
 * Created by longcheng on 2017/5/2.
 */

public class JrdWeatherModel implements SplashConstract.SplashModelApi {
    private JrdWeatherGetListener mJrdWeatherGetListener;
    private JrdBaiDuLocationManager jrdBaiDuLocationManager;
    @Override
    public void getWeatherData() {
        /*如果有历史cityname数据，则不需要重新获取*/
        //判断历史信息中是否有CityName,如果有， 则直接调用查询weather的函数
        String cityName = JrdWeatherDataCache.getInstance().getWeatherName();
        if(cityName != null && cityName.length()!=0){
            getWeatherInfo(cityName);
        }else{
            /*定位获取地址， 并且根据新的地址查询天气*/
            jrdBaiDuLocationManager = JrdBaiDuLocationManager.getInstance(JrdWeatherApplication.getAppContext());
            jrdBaiDuLocationManager.setManagerListener(jrdLocationManagerListener);
            jrdBaiDuLocationManager.getLocation();
        }
    }

    private JrdBaiDuLocationManager.JrdLocationManagerListener jrdLocationManagerListener = new JrdBaiDuLocationManager.JrdLocationManagerListener() {
        @Override
        public void getCoordinate(double la, double longti, float radius) {
            //donithing
        }

        @Override
        public void getCityName(String cityName) {
            //保存city name
            //JrdDataCache.getInstance().setHasMapData(JrdDataCache.JRD_WEATHER_CITY_NAME, cityName);
            //根据City来获取weather信息
            getWeatherInfo(cityName);
            //成功获取到城市名称，停止定位
            jrdBaiDuLocationManager.stopLocation();
        }
    };

    private void getWeatherInfo(String cityName){

        LogUtils.d("JrdWeatherModel: getWeatherInfo");
        //Check是否有weather data
        JrdWeatherBean weatherBean = JrdWeatherDataCache.getInstance().getWeatherData(cityName);
        if(weatherBean != null){
            if(mJrdWeatherGetListener != null){
                mJrdWeatherGetListener.getWeatherInfo(weatherBean);
            }
            return; //如果weather已经存在数据不需要在获取
        }

        ProgressSubscriber<JrdWeatherBean> progressSubscriber = new ProgressSubscriber<JrdWeatherBean>(JrdWeatherApplication.getAppContext(), new JrdOnNextListener<JrdWeatherBean>() {
                @Override
            public void OnNext(JrdWeatherBean weatherBean) {
                /*
                * 获取到了weather info,回调给present
                * */
                if(mJrdWeatherGetListener != null){
                    mJrdWeatherGetListener.getWeatherInfo(weatherBean);
                }
            }
        }, false);
        JrdHttpMethod.getInstance().requestWeatherData(progressSubscriber, cityName);
    }

    public void setGetWeatherListener(JrdWeatherGetListener jrdWeatherGetListener){
        mJrdWeatherGetListener = jrdWeatherGetListener;
    }

    public interface JrdWeatherGetListener{
        void getWeatherInfo(JrdWeatherBean weatherBean);
    }
}
