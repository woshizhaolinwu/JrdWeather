package jrdcom.com.jrdweather.NetWork.JrdHttp.Services;

import java.util.List;


import jrdcom.com.jrdweather.NetWork.JrdHttp.Beans.JrdMoveDetailBean;
import jrdcom.com.jrdweather.NetWork.JrdHttp.Beans.JrdSubjectBean;
import jrdcom.com.jrdweather.NetWork.JrdHttp.JrdHttpResult;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by longcheng on 2017/3/25.
 */

public interface JrdServiceInterface {
    //使用RXJava获取
    @GET("top250")
    Observable<JrdHttpResult<List<JrdSubjectBean>>> getRXMovie(@Query("start") int start, @Query("count") int count);

    @GET("subject/{movieID}")
    Observable<JrdMoveDetailBean> getMovieDetail(@Path("movieID") String movieId);
}
