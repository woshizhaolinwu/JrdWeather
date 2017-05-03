package jrdcom.com.jrdweather.NetWork.JrdHttp;

import java.util.List;
import java.util.concurrent.TimeUnit;


import jrdcom.com.jrdweather.NetWork.JrdHttp.Beans.JrdMoveDetailBean;
import jrdcom.com.jrdweather.NetWork.JrdHttp.Beans.JrdSubjectBean;
import jrdcom.com.jrdweather.NetWork.JrdHttp.Beans.JrdWeatherBean;
import jrdcom.com.jrdweather.NetWork.JrdHttp.Services.JrdServiceInterface;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by longcheng on 2017/3/25.
 */

public class JrdHttpMethod {
    //Base URL
    private static final String BASE_URL = "http://v.juhe.cn/weather/";
    private static final String JUHE_WEATHER_KEY = "61a5541c660e656c809155a80f6c76de";
    private static final int DEFAULT_TIMEOUT = 5;
    private Retrofit jrdRetrofit;
    private JrdServiceInterface jrdApiClient;
    //初始化
    private JrdHttpMethod(){
        //手动添加timeout
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        jrdRetrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        jrdApiClient = jrdRetrofit.create(JrdServiceInterface.class);
    }

    private static class SingletonHolder{
        private static final JrdHttpMethod INSTANCE =new JrdHttpMethod();
    }

    public static JrdHttpMethod getInstance(){
        return JrdHttpMethod.SingletonHolder.INSTANCE;
    }

    /*
    * 网络接口
    * */
    /*获取天气接口*/
    public void requestWeatherData(Subscriber<JrdWeatherBean> subscriber, String cityName){
        jrdApiClient.getWeatherData(cityName,JUHE_WEATHER_KEY)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
