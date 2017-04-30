package jrdcom.com.jrdweather.Ui.Main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import jrdcom.com.jrdweather.Base.BaseActivity;
import jrdcom.com.jrdweather.R;

public class MainActivity extends BaseActivity implements MainConstract.MainActivityView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /*BaseActivity的抽象*/

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void addListener() {

    }

    @Override
    public int getLayoutId() {
        return 0;
    }
}
