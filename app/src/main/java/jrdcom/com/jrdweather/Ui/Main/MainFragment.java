package jrdcom.com.jrdweather.Ui.Main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import jrdcom.com.jrdweather.Base.BaseFragment;
import jrdcom.com.jrdweather.NetWork.JrdHttp.Beans.JrdWeatherBean;
import jrdcom.com.jrdweather.R;

/**
 * Created by longcheng on 2017/5/3.
 */

public class MainFragment extends BaseFragment {
    private int index= 0;
    private TextView mCityText;
    private TextView mTempText;
    private TextView mWindText;
    private TextView mDressTExt;
    private JrdWeatherBean.ResultBean.FutureBean mFutureBean;
    private String mCityName;
    @Override
    public void initView(View view) {
        mCityText = (TextView)view.findViewById(R.id.city_name);
        mTempText = (TextView)view.findViewById(R.id.temp);
        mWindText = (TextView)view.findViewById(R.id.wind);
        mDressTExt = (TextView)view.findViewById(R.id.dress);
    }

    @Override
    public void initData() {
        //

    }

    @Override
    public void addListener() {

    }

    @Override
    public int getLayoutId() {
        //获取layout//return 0;
        return R.layout.layout_main_fragment;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle=getArguments();
        index = bundle.getInt("index");
        mFutureBean = (JrdWeatherBean.ResultBean.FutureBean) bundle.getSerializable("weatherData");
        updateScreen();
    }


    public void updateInfo(JrdWeatherBean.ResultBean.FutureBean futureBean, String cityName){
        mFutureBean = futureBean;
        mCityName = cityName;
        updateScreen();
    }

    private void updateScreen(){
        mCityText.setText(mCityName);
        if(mFutureBean!= null){
            mTempText.setText(mFutureBean.getTemperature());
            mWindText.setText(mFutureBean.getWind());
            mDressTExt.setText(mFutureBean.getDate());
        }
    }
}
