package jrdcom.com.jrdweather.Ui.Splash;

import jrdcom.com.jrdweather.DataCache.JrdDataCache;
import jrdcom.com.jrdweather.Model.JrdWeatherModel;
import jrdcom.com.jrdweather.NetWork.JrdHttp.Beans.JrdWeatherBean;

/**
 * Created by longcheng on 2017/5/2.
 */

public class SplashPresent implements SplashConstract.SplashPresentApi {
    private SplashConstract.SplashModelApi splashModelApi;
    private SplashConstract.SplashView splashView;

    public SplashPresent(SplashConstract.SplashView view){
        splashView = view;
        splashModelApi = new JrdWeatherModel();
    }

    /*请求weather data*/
    public void requestWeatherData(){
        //注册回调
        splashModelApi.setGetWeatherListener(jrdWeatherGetListener);
        //获取定位信息
        splashModelApi.getWeatherData();
    }

    private JrdWeatherModel.JrdWeatherGetListener jrdWeatherGetListener = new JrdWeatherModel.JrdWeatherGetListener() {
        @Override
        public void getWeatherInfo(JrdWeatherBean weatherBean) {
            /*饶了一圈，终于把weather信息获取回来了，很不容易啊*/
            /*保存起来，留到主屏的时候用*/
            JrdDataCache.getInstance().setHasMapData(JrdDataCache.JRD_WEATHER_CURRENT_DATA, weatherBean);
        }
    };
}
