package jrdcom.com.jrdweather.Model;

import jrdcom.com.jrdweather.BaiDuLocation.JrdBaiDuLocationManager;
import jrdcom.com.jrdweather.JrdWeatherApplication;
import jrdcom.com.jrdweather.NetWork.JrdHttp.Beans.JrdWeatherBean;
import jrdcom.com.jrdweather.NetWork.JrdHttp.Interface.JrdOnNextListener;
import jrdcom.com.jrdweather.NetWork.JrdHttp.JrdHttpMethod;
import jrdcom.com.jrdweather.NetWork.JrdHttp.ProgressSubscriber;
import jrdcom.com.jrdweather.Ui.Splash.SplashConstract;

/**
 * Created by longcheng on 2017/5/2.
 */

public class JrdWeatherModel implements SplashConstract.SplashModelApi {
    private JrdWeatherGetListener mJrdWeatherGetListener;
    @Override
    public void getWeatherData() {
        /*定位获取地址， 并且根据新的地址查询天气*/
        JrdBaiDuLocationManager jrdBaiDuLocationManager = JrdBaiDuLocationManager.getInstance(JrdWeatherApplication.getAppContext());
        jrdBaiDuLocationManager.getLocation();
        jrdBaiDuLocationManager.setManagerListener(jrdLocationManagerListener);

    }

    private JrdBaiDuLocationManager.JrdLocationManagerListener jrdLocationManagerListener = new JrdBaiDuLocationManager.JrdLocationManagerListener() {
        @Override
        public void getCoordinate(double la, double longti, float radius) {
            //donithing
        }

        @Override
        public void getCityName(String cityName) {
            //根据City来获取weather信息
            getWeatherInfo(cityName);
        }
    };

    private void getWeatherInfo(String cityName){
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
        });
        JrdHttpMethod.getInstance().requestWeatherData(progressSubscriber, cityName);
    }

    public void setGetWeatherListener(JrdWeatherGetListener jrdWeatherGetListener){
        mJrdWeatherGetListener = jrdWeatherGetListener;
    }

    public interface JrdWeatherGetListener{
        void getWeatherInfo(JrdWeatherBean weatherBean);
    }
}
