package jrdcom.com.jrdweather.Utils;

import com.google.gson.Gson;

/**
 * Project Name:AnimDveDemo
 * Author:IceChen
 * Date:16/8/24
 * Notes:
 */
public class GsonUtils {
  private static Gson gson;

  private GsonUtils() {

  }

  public static Gson getSingleInstance() {
    if (gson == null) {
      synchronized (GsonUtils.class) {
        if (gson == null) {
          gson = new Gson();
        }
      }
    }
    return gson;
  }

  public static Gson newInstance(){
    return new Gson();
  }

  public static String toJson(Object obj){
    return getSingleInstance().toJson(obj);
  }

  public static <T>T fromJson(String gsonString, Class<T> tClass){
      return getSingleInstance().fromJson(gsonString, tClass);
  }
}
