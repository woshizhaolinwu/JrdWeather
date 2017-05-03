package jrdcom.com.jrdweather.Ui.Splash;

import com.google.gson.Gson;

import jrdcom.com.jrdweather.DataCache.JrdDataCache;
import jrdcom.com.jrdweather.Model.JrdWeatherModel;
import jrdcom.com.jrdweather.NetWork.JrdHttp.Beans.JrdWeatherBean;
import jrdcom.com.jrdweather.Utils.GsonUtils;
import jrdcom.com.jrdweather.Utils.JrdCommon;
import jrdcom.com.jrdweather.Utils.LogUtils;
import jrdcom.com.jrdweather.Utils.SPUtil;

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
        LogUtils.d("Present: requestWeatherData");
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
            LogUtils.d("get weather info");
            String weather = GsonUtils.toJson(weatherBean);
            LogUtils.d("Present: weather info is " + weather);
            JrdWeatherBean.ResultBean resultBean = weatherBean.getResult();
            //将取到的天气信息保存起来
            JrdDataCache.getInstance().setHasMapData(JrdDataCache.JRD_WEATHER_CURRENT_DATA, weatherBean);
        }
    };
}
