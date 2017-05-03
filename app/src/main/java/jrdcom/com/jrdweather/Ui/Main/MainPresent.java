package jrdcom.com.jrdweather.Ui.Main;

import jrdcom.com.jrdweather.DataCache.JrdWeatherDataCache;
import jrdcom.com.jrdweather.Model.JrdWeatherModel;
import jrdcom.com.jrdweather.NetWork.JrdHttp.Beans.JrdWeatherBean;
import jrdcom.com.jrdweather.Ui.Splash.SplashConstract;

/**
 * Created by longcheng on 2017/5/3.
 */

public class MainPresent implements MainConstract.MainPresendApi {
    private MainConstract.MainActivityView mainView;
    private SplashConstract.SplashModelApi weatherAPi;
    public MainPresent(MainConstract.MainActivityView mainActivityView){
        mainView = mainActivityView;
        weatherAPi = new JrdWeatherModel();
    }
    @Override
    public void requestWeatherData() {
        weatherAPi.setGetWeatherListener(jrdWeatherGetListener);
        //获取定位信息
        weatherAPi.getWeatherData();
    }

    private JrdWeatherModel.JrdWeatherGetListener jrdWeatherGetListener = new JrdWeatherModel.JrdWeatherGetListener() {
        @Override
        public void getWeatherInfo(JrdWeatherBean weatherBean) {
            //获得信息，回调给View显示
            mainView.updateData(weatherBean.getResult().getFuture(),weatherBean.getResult().getToday().getCity());
        }
    };

    @Override
    public String getCityName() {
       String cityName =  JrdWeatherDataCache.getInstance().getWeatherName();
        return cityName;
    }
}
