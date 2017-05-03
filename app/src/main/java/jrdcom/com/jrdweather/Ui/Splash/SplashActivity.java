package jrdcom.com.jrdweather.Ui.Splash;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Map;

import jrdcom.com.jrdweather.Base.BaseActivity;
import jrdcom.com.jrdweather.R;
import jrdcom.com.jrdweather.Ui.Main.MainActivity;
import jrdcom.com.jrdweather.Utils.AppUtils;
import jrdcom.com.jrdweather.Utils.DisplayUtils;
import jrdcom.com.jrdweather.Utils.JrdCommon;
import jrdcom.com.jrdweather.Utils.JrdDialog.IDialogResultListener;
import jrdcom.com.jrdweather.Utils.JrdDialog.JrdCommonDialogFragment;
import jrdcom.com.jrdweather.Utils.JrdDialog.JrdDialogHelper;
import jrdcom.com.jrdweather.Utils.JrdSystemUtil;
import jrdcom.com.jrdweather.Utils.SPUtil;

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
        splashPresent = new SplashPresent(this);
        getLocationPermission();
    }

    @Override
    public void initView() {
        imageViewSun = (ImageView) findViewById(R.id.image_sun);
        imageViewCloud = (ImageView) findViewById(R.id.image_cloud);
        btnGointo = (Button)findViewById(R.id.btn_gointo);

        /*在主界面创建快捷方式*/
        createShortCut();
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
                MainActivity.start(SplashActivity.this);
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
            }else{
                if(splashPresent != null){
                    splashPresent.requestWeatherData();
                }
            }
        }else{
            //获取weather数据
            if(splashPresent != null){
                splashPresent.requestWeatherData();
            }
        }
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

    /*
    * 快捷方式的创建
    * */
    private void createShortCut(){
        String appName ="JrdWeather";
        /*获取sharepreference*/
        Boolean vShortcutExist =  (Boolean)SPUtil.readObjectPreference(JrdCommon.HAS_SHORTCUT, Boolean.class);
        if(vShortcutExist == true ){
            return;
        }

        /*如果已经存在该快捷方式*/
        if(JrdSystemUtil.isHasShortCut(this, appName) == true){
            return;
        }

        /*创建shortcut*/
        JrdSystemUtil.createShortCut(this, appName,R.mipmap.launch_icon2);

        /*写入sharePreference*/
        SPUtil.writeObjectPreference(JrdCommon.HAS_SHORTCUT, true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //获取回来权限
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        /*取回我们的requestCode*/
        switch (requestCode){
            case JrdCommon.LOCATION_REQUEST_CODE:
                Map<String, Integer> map = new HashMap<>();

                for(int i = 0; i < permissions.length; i++){
                    map.put(permissions[i], grantResults[i]);
                }

                /*从map中查找我们请求的*/
                String PermissionString = Manifest.permission.ACCESS_COARSE_LOCATION;
                if(map.get(PermissionString)== PackageManager.PERMISSION_GRANTED){
                    //权限获取
                    splashPresent.requestWeatherData();
                }else{
                    //提示用户
                    AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.Base_AlertDialog);
                    builder.setTitle("Not get permission");
                    builder.setMessage("You don't get location permission, it will finsih");

                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    builder.create().show();
                }
                break;
        }
    }
}
