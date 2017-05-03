package jrdcom.com.jrdweather.NetWork.JrdHttp.Services;

import java.util.List;



import jrdcom.com.jrdweather.NetWork.JrdHttp.Beans.JrdWeatherBean;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by longcheng on 2017/3/25.
 */

public interface JrdServiceInterface {
    //使用RXJava获取
    /*@GET("top250")
    Observable<JrdHttpResult<List<JrdSubjectBean>>> getRXMovie(@Query("start") int start, @Query("count") int count);

    @GET("subject/{movieID}")
    Observable<JrdMoveDetailBean> getMovieDetail(@Path("movieID") String movieId);*/


    /*
    * 接口的定义
    * http://v.juhe.cn/weather/index?cityname=上海市&key=61a5541c660e656c809155a80f6c76de&format=2
    * */
    @GET("index")
    Observable<JrdWeatherBean> getWeatherData(@Query("cityname") String cityName, @Query("key") String key, @Query("format") String value);
}
