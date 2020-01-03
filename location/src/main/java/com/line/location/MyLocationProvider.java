package com.line.location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by chenliu on 2020-01-02.
 * 真机调试，使用GPS定位上一次位置信息和回调都没有
 * 使用网络定位时都有
 */
public class MyLocationProvider {
    private static final String TAG = "LocationProvider";

    /**
     * 需要动态申请权限
     *
     * @param context
     */
    @SuppressLint("MissingPermission")
    public void getLocationManager(Context context, LocationListener locationListener) {
        Object systemService = context.getSystemService(Context.LOCATION_SERVICE);
        if ((systemService instanceof LocationManager)) {
            Log.d(TAG, "getLocationManager: init ok");
            LocationManager locationManager = (LocationManager) systemService;
            List<String> providers = locationManager.getProviders(true);
            for (String provider : providers) {
                Log.d(TAG, "getLocationManager: enable provider contains " + provider);
            }

            String bestProvider = locationManager.getBestProvider(getCriteria(), true);
            if (!TextUtils.isEmpty(bestProvider)) {
                Log.d(TAG, "getLocationManager: bestProvider is " + bestProvider);
                LocationProvider bestLocationProvider = locationManager.getProvider(bestProvider);
            } else {
                Log.d(TAG, "getLocationManager: there is no best provider!");
            }

            //获取缓存中的位置信息
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Log.d(TAG, "getLocationManager: last location is " + (lastKnownLocation == null ? "null" : lastKnownLocation.toString()));

            //请求一次位置
            locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, locationListener, null);

            locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Log.d(TAG, "onLocationChanged: inner class:" + location.toString());
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            }, null);

            //定时请求位置更新
//            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5, 10, locationListener);

        } else {
            Log.e(TAG, "getLocationManager: get LocationManager error.");
        }
    }

    private Criteria getCriteria() {
        Criteria criteria = new Criteria();
        // Criteria是一组筛选条件
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        //设置定位精准度
        criteria.setAltitudeRequired(false);
        //是否要求海拔
        criteria.setBearingRequired(true);
        //是否要求方向
        criteria.setCostAllowed(true);
        //是否要求收费
        criteria.setSpeedRequired(true);
        //是否要求速度
        criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
        //设置电池耗电要求
        criteria.setBearingAccuracy(Criteria.ACCURACY_HIGH);
        //设置方向精确度
        criteria.setSpeedAccuracy(Criteria.ACCURACY_HIGH);
        //设置速度精确度
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        //设置水平方向精确度
        criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);
        //设置垂直方向精确度
        return criteria;
    }

    public void geocoder(Context context, Location location) {
        Geocoder geocoder = new Geocoder(context, Locale.CHINA);
        boolean present = Geocoder.isPresent();
        Log.d(TAG, "geocoder: present:" + present);
        if (present) {
            try {
                List<Address> fromLocation = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 3);
                if (fromLocation != null) {
                    for (Address address : fromLocation) {
                        Log.d(TAG, "geocoder: address is " + address.toString());
                    }
                }
            } catch (IOException e) {
                Log.e(TAG, "geocoder error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
