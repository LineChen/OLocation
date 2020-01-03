package com.line.amap;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * Created by chenliu on 2020-01-02.
 */
public class AMapProvider {
    private static final String TAG = "AMapProvider";
    //异步获取定位结果
    AMapLocationListener mAMapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                Log.d(TAG, "onLocationChanged: " + amapLocation.toStr());
                if (amapLocation.getErrorCode() == 0) {
                    //解析定位结果

                }
            }
        }
    };

    public void start(Context context) {
        //声明AMapLocationClient类对象
        AMapLocationClient mLocationClient = null;
        //初始化定位
        mLocationClient = new AMapLocationClient(context);
        //设置定位回调监听
        mLocationClient.setLocationListener(mAMapLocationListener);
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);
//        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        option.setGpsFirst(true);
        option.setGpsFirstTimeout(30000);
        option.setHttpTimeOut(10000);
//        option.setInterval(10000);
        option.setOnceLocation(true);
        option.setOnceLocationLatest(false);
        mLocationClient.setLocationOption(option);
        //启动定位
        mLocationClient.startLocation();
    }

}
