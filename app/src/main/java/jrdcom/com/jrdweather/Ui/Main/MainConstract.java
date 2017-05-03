package jrdcom.com.jrdweather.Ui.Main;

import java.util.List;

import jrdcom.com.jrdweather.NetWork.JrdHttp.Beans.JrdWeatherBean;

/**
 * Created by dhcui on 2017/4/29.
 */

public class MainConstract {
    /*Main View Presend*/
    public interface MainActivityView{
        //void showDataWithWeatherData(List<JrdWeatherBean.ResultBean.FutureBean> weatherBean);
        void updateData(List<JrdWeatherBean.ResultBean.FutureBean> futureBeen, String cityName);
    }

    public interface MainPresendApi{
        void requestWeatherData();
        String getCityName();
    }

    public interface MainModelApi{

    }


}
