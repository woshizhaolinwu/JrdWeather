package jrdcom.com.jrdweather.Ui.Main;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import jrdcom.com.jrdweather.Base.BaseActivity;
import jrdcom.com.jrdweather.NetWork.JrdHttp.Beans.JrdWeatherBean;
import jrdcom.com.jrdweather.R;
import jrdcom.com.jrdweather.Ui.Main.Adapter.JrdWeatherPageAdapter;

public class MainActivity extends BaseActivity implements MainConstract.MainActivityView{
    private ViewPager mViewPager;
    private RecyclerView mRecyclerView;
    List<MainFragment> fragments = new ArrayList<>();
    List<JrdWeatherBean.ResultBean.FutureBean> futureBeanList;
    //定义Controller的控件
    /*BaseActivity的抽象*/
    private MainConstract.MainPresendApi presendApi;

    @Override
    public void initData() {
        presendApi = new MainPresent(this);
        presendApi.requestWeatherData();
    }

    @Override
    public void initView() {
        mViewPager= (ViewPager)findViewById(R.id.main_view_pager);
        mRecyclerView = (RecyclerView)findViewById(R.id.main_recycler_view);

        //初始化ViewPager
        for (int i = 0; i < 5; i++){
            MainFragment mainFragment = new MainFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("index", i);
            if(futureBeanList != null){
                bundle.putSerializable("futureData", futureBeanList.get(i));
            }else{
                bundle.putSerializable("futureData", null);
            }
            mainFragment.setArguments(bundle);
            fragments.add(mainFragment);
        }
        JrdWeatherPageAdapter jrdWeatherPageAdapter = new JrdWeatherPageAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(jrdWeatherPageAdapter);

        //初始化list
    }

    @Override
    public void addListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                MainFragment fragment = fragments.get(position);
                fragment.updateInfo(futureBeanList.get(position),getCityName());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_main;
    }

    @Override
    public void updateData(List<JrdWeatherBean.ResultBean.FutureBean> futureBeen) {
        futureBeanList = futureBeen;
        showDataWithWeatherData(futureBeanList.get(mViewPager.getCurrentItem()));
    }

    public void showDataWithWeatherData(JrdWeatherBean.ResultBean.FutureBean futureBean) {
        int index = mViewPager.getCurrentItem();
        MainFragment mainFragment = fragments.get(index);
        mainFragment.updateInfo(futureBean, getCityName());
    }

    private String getCityName(){
       return presendApi.getCityName();
    }
}
