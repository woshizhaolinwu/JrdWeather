package jrdcom.com.jrdweather.NetWork.JrdHttp.Interface;

/**
 * Created by longcheng on 2017/3/27.
 */

public interface JrdOnActionListener {
    public interface JrdOnNextListener<T> {
        void OnNext(T t);
    }

    public interface JrdOnCompleteListener{
        void OnComplete();
    }
}
