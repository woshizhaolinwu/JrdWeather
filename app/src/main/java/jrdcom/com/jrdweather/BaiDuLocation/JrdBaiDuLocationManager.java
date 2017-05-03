package jrdcom.com.jrdweather.BaiDuLocation;

import android.content.Context;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * Created by longcheng on 2017/4/7.
 */

public class JrdBaiDuLocationManager {
    private LocationClient mLocationClient = null;//new LocationClient();
    private static Context mContext;
    private double distance = 0.002;
    private double mTestLa = 0;

    private static JrdBaiDuLocationManager manager;
    private JrdLocationManagerListener managerListener;
    private JrdBaiDuLocationManager(Context context){
        mContext = context;
        mLocationClient = new LocationClient(context);
    }

    public static JrdBaiDuLocationManager getInstance(Context context){
        if(manager == null){
            manager = new JrdBaiDuLocationManager(context);
        }
        mContext = context;
        return manager;
    }
    /*
    *  获取Location
    * */
    public void getLocation(){
        /*初始化option*/
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        option.setNeedDeviceDirect(true);
        option.setAddrType("all");
        mLocationClient.setLocOption(option);

        mLocationClient.registerLocationListener(mBDLocationListener);
        mLocationClient.start();
    }

    public void stopLocation(){
        mLocationClient.stop();
    }

    private BDLocationListener mBDLocationListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if(bdLocation==null){
                return;
            }
            /*返回数据， 把返回的数据以回调的方式给到调用方*/
            double lat = bdLocation.getLatitude();
            double longti = bdLocation.getLongitude();
            float radius = bdLocation.getRadius();

            if(true){
                if(managerListener != null){

                    managerListener.getCoordinate(lat, longti, radius);
                    managerListener.getCityName(bdLocation.getCity());
                }
            }


            //显示location信息：
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("location descripation:"+bdLocation.getLocTypeDescription()+"/n");
            stringBuilder.append("la:"+lat+"  longti = "+longti);
            Toast.makeText(mContext, stringBuilder.toString(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    };

    /*
    *  设置LocationManagerListener
    * */
    public void setManagerListener(JrdLocationManagerListener listener){
        managerListener = listener;
    }

    public interface JrdLocationManagerListener{
        void getCoordinate(double la, double longti, float radius);
        void getCityName(String cityName);
    }
}
