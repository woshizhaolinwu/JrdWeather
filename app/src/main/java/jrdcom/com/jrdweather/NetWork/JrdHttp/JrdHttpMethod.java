package jrdcom.com.jrdweather.NetWork.JrdHttp;

import java.util.List;
import java.util.concurrent.TimeUnit;


import jrdcom.com.jrdweather.NetWork.JrdHttp.Beans.JrdMoveDetailBean;
import jrdcom.com.jrdweather.NetWork.JrdHttp.Beans.JrdSubjectBean;
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
    private static final String BASE_URL = "https://api.douban.com/v2/movie/";
    private static final int DEFAULT_TIMEOUT = 5;
    private Retrofit jrdRetrofit;
    private JrdServiceInterface jrdMovieServer;
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
        jrdMovieServer = jrdRetrofit.create(JrdServiceInterface.class);
    }

    private static class SingletonHolder{
        private static final JrdHttpMethod INSTANCE =new JrdHttpMethod();
    }

    public static JrdHttpMethod getInstance(){
        return JrdHttpMethod.SingletonHolder.INSTANCE;
    }

    public void getMovie(Subscriber<JrdSubjectBean> subscriber, int start, int count){
        jrdMovieServer.getRXMovie(start, count)
                .flatMap(new Func1<JrdHttpResult<List<JrdSubjectBean>>, Observable<JrdSubjectBean>>() {
                    @Override
                    public Observable<JrdSubjectBean> call(JrdHttpResult<List<JrdSubjectBean>> listHttpResult) {
                        return Observable.from(listHttpResult.getSubjects());
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getMovieList(Subscriber<List<JrdSubjectBean>> subscriber, int start, int count){
        jrdMovieServer.getRXMovie(start, count)
                .map(new Func1<JrdHttpResult<List<JrdSubjectBean>>, List<JrdSubjectBean>>() {
                    @Override
                    public List<JrdSubjectBean> call(JrdHttpResult<List<JrdSubjectBean>> listJrdHttpResult) {
                        return listJrdHttpResult.getSubjects();
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    public void getHttpMovieList(Subscriber<JrdHttpResult<List<JrdSubjectBean>>> subscriber, int start, int count){
        jrdMovieServer.getRXMovie(start, count)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getHttpMovieDetail(Subscriber<JrdMoveDetailBean> subscriber, String MovieId){
        jrdMovieServer.getMovieDetail(MovieId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
