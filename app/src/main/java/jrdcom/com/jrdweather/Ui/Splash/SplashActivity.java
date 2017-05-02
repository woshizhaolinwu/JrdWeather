package jrdcom.com.jrdweather.Ui.Splash;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.pm.PackageManager;
import android.media.Image;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import jrdcom.com.jrdweather.Base.BaseActivity;
import jrdcom.com.jrdweather.R;
import jrdcom.com.jrdweather.Utils.AppUtils;
import jrdcom.com.jrdweather.Utils.DisplayUtils;
import jrdcom.com.jrdweather.Utils.JrdCommon;

/**
 * Created by dhcui on 2017/4/29.
 */

public class SplashActivity extends BaseActivity implements SplashConstract.SplashView{
    private ImageView imageViewSun;
    private ImageView imageViewCloud;
    private Button btnGointo;
    private SplashConstract.SplashPresentApi splashPresent;

    /*实现BaseActivity的抽象方法*/
    @Override
    public int getLayoutId() {
        return R.layout.layout_splash;
    }

    @Override
    public void initData() {
        //申请权限
        getLocationPermission();
        splashPresent = new SplashPresent(this);
    }

    @Override
    public void initView() {
        imageViewSun = (ImageView) findViewById(R.id.image_sun);
        imageViewCloud = (ImageView) findViewById(R.id.image_cloud);
        btnGointo = (Button)findViewById(R.id.btn_gointo);
        /*开始动画*/

    }

    @Override
    public void addListener() {
        /*监听*/
        playAnimationOnLayout();
        /*定义listener*/
        btnGointo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*进入MainActivity*/

            }
        });
    }

    private void playAnimationOnLayout(){
        //监听GlobalLayout
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                playAnimation();
                getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    /*
    * 申请Location权限
    * */
    private void getLocationPermission(){
        String PermissionString = Manifest.permission.ACCESS_COARSE_LOCATION;
        /*Android N后才需要主动获取权限*/
        if(AppUtils.getSdkVersion() >= 23){
            if(checkSelfPermission(PermissionString) != PackageManager.PERMISSION_GRANTED){
                //requestPermissions(PermissionString, JrdCommon.LOCATION_REQUEST_CODE)
                String requestPermis[] = {PermissionString};
                requestPermissions(requestPermis, JrdCommon.LOCATION_REQUEST_CODE);
            }
        }else{
            //获取weather数据
            if(splashPresent != null){
                splashPresent.requestWeatherData();
            }
        }

        /*if(checkSelfPermission(Android.))*/
    }

    /*
    * 播放动画
    * */
    private void playAnimation(){
        playButtonAnimation();
        playClodeAnimation();
        playSunAnimation();
    }

    private void playSunAnimation(){
        ObjectAnimator anim = ObjectAnimator.ofFloat(imageViewSun, "rotation", 0f, 360f);
        anim.setRepeatMode(ObjectAnimator.RESTART);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.setInterpolator(new LinearInterpolator());
        anim.setDuration(30 * 1000);
        anim.start();
    }
    private void playClodeAnimation()
    {
        int left = imageViewCloud.getLeft();
        int right = DisplayUtils.getScreenWidth() - imageViewCloud.getRight();
        int width = imageViewCloud.getMeasuredWidth();
        ObjectAnimator anim =
                ObjectAnimator.ofFloat(imageViewCloud, "translationX", right + width, -(left + width));
        anim.setRepeatMode(ObjectAnimator.RESTART);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.setInterpolator(new LinearInterpolator());
        anim.setDuration(31 * 1000);
        anim.start();
    }
    private void playButtonAnimation(){
        ObjectAnimator anim = ObjectAnimator.ofFloat(btnGointo, "alpha", 0.1f, 1f);
        anim.setDuration(2000);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.setRepeatMode(ObjectAnimator.REVERSE);
        anim.start();
    }

}
