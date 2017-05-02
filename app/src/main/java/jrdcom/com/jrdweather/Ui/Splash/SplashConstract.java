package jrdcom.com.jrdweather.Ui.Splash;

import jrdcom.com.jrdweather.Model.JrdWeatherModel;

/**
 * Created by longcheng on 2017/5/2.
 */

public class SplashConstract {
    public interface SplashView{

    }

    public interface SplashPresentApi{
        void requestWeatherData();
    }

    public interface SplashModelApi{
        void getWeatherData();
        void setGetWeatherListener(JrdWeatherModel.JrdWeatherGetListener jrdWeatherGetListener);
    }
}
