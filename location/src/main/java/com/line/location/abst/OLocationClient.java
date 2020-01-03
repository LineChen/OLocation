package com.line.location.abst;

import android.content.Context;
import android.location.LocationManager;
import android.util.Log;

/**
 * Created by chenliu on 2020-01-02.
 */
public class OLocationClient {
    private static final String TAG = "OLocationClient";

    private Context context;
    private LocationManager locationManager;
    private OLocationListener oLocationListener;

    public OLocationClient(Context context) {
        this.context = context;
        Object systemService = context.getSystemService(Context.LOCATION_SERVICE);
        if ((systemService instanceof LocationManager)) {
            Log.d(TAG, "getLocationManager: init ok");
            locationManager = (LocationManager) systemService;
        } else {

        }
    }

//    @Override
//    public void setLocationListener(OLocationListener oLocationListener) {
//        this.oLocationListener = oLocationListener;
//    }
//
//    @Override
//    public void unRegisterLocationListener(OLocationListener oLocationListener) {
//
//    }
//
//    @Override
//    public void startLocation() {
//
//    }
//
//    @Override
//    public void stopLocation() {
//
//    }
}
