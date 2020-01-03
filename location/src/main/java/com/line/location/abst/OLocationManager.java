package com.line.location.abst;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Locale;

/**
 * Created by chenliu on 2020-01-02.
 */
public class OLocationManager implements OLocationManagerBase {

    private static final String TAG = "OLocationManager";

    private LocationManager sdkLocationManager;

    private Geocoder sdkGeoCoder;

    private OLocationOption oLocationOption;

    private OLocationListener oLocationListener;

    private Context context;

    private OHandler oHandler;

    public OLocationManager(Context context) {
        this.context = context;
        Object systemService = context.getSystemService(Context.LOCATION_SERVICE);
        if ((systemService instanceof LocationManager)) {
            this.sdkLocationManager = (LocationManager) systemService;
        } else {
            Log.e(TAG, "sdkLocationManager init error.");
        }
        this.oHandler = new OHandler(this);
    }

    /*******************open api***********************/

    @Override
    public void setLocationOption(OLocationOption option) {
        this.oLocationOption = option;
    }

    @Override
    public void startLocation() {
        try {
            oHandler.sendEmptyMessage(OHandler.START_LOCATION);
        } catch (Throwable e) {
            Log.e(TAG, (e == null ? "" : e.getMessage()));
        }
    }

    @Override
    public void stopLocation() {
        try {
            oHandler.sendEmptyMessage(OHandler.STOP_LOCATION);
        } catch (Throwable e) {
            Log.e(TAG, "stopLocation" + (e == null ? "" : e.getMessage()));
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public @Nullable
    Address getLastKnownLocation() {
        try {
            Location lastGPSLocation = sdkLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastGPSLocation == null) {
                Location lastNetWorkLocation = sdkLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                return decodeLocation(lastNetWorkLocation);
            } else {
                return decodeLocation(lastGPSLocation);
            }
        } catch (Throwable e) {
            Log.e(TAG, "getLastKnownLocation");
        }
        return null;
    }

    @Override
    public void setLocationListener(OLocationListener oLocationListener) {
        try {
            this.oLocationListener = oLocationListener;
        } catch (Throwable e) {
            Log.e(TAG, "setLocationListener");
        }
    }

    @Override
    public void unRegisterLocationListener(OLocationListener oLocationListener) {
        try {
            this.oLocationListener = null;
            if (sdkLocationManager != null) {
                sdkLocationManager.removeUpdates(sdkLocationListener);
            }
        } catch (Throwable e) {
            Log.e(TAG, "unRegisterLocationListener");
        }
    }

    @Override
    public void onDestroy() {
        stopLocation();
        oHandler.removeMessages(OHandler.LOCATION_TIMEOUT);
        if (oLocationOption != null) {
            oLocationOption = null;
        }
        if (sdkGeoCoder != null) {
            sdkGeoCoder = null;
        }
    }


    /***************inner api**********************/
    private Address decodeLocation(Location location) {
        if (location == null) {
            return null;
        }
        try {
            if (Geocoder.isPresent()) {
                if (sdkGeoCoder == null) {
                    sdkGeoCoder = new Geocoder(context, Locale.CHINA);
                }
                List<Address> fromLocation = sdkGeoCoder.getFromLocation(location.getLatitude(), location.getLongitude(), 3);
                if (fromLocation != null && fromLocation.size() > 0) {
                    return fromLocation.get(0);
                }
            } else {
                oLocationListener.onError(-1, "Geocoder is not present");
            }
        } catch (Throwable e) {
            Log.e(TAG, "");
        }
        return null;
    }


    private LocationListener sdkLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            oHandler.removeMessages(OHandler.LOCATION_TIMEOUT);
            Address address = decodeLocation(location);
            if (oLocationListener != null) {
                oLocationListener.onLocationChanged(address);
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {
            if (oLocationListener != null) {
                oLocationListener.onError(-1, "provider is disabled");
            }
        }
    };


    private static class OHandler extends android.os.Handler {

        public OHandler() {
        }

        public OHandler(@NonNull Looper looper) {
            super(looper);
        }

        final static int START_LOCATION = 1000;
        final static int STOP_LOCATION = 1001;
        final static int LOCATION_TIMEOUT = 1009;
        private WeakReference<OLocationManager> locationManagerWeakReference;

        public OHandler(OLocationManager locationManager) {
            locationManagerWeakReference = new WeakReference<>(locationManager);
        }

        @SuppressLint("MissingPermission")
        @Override
        public void handleMessage(@NonNull Message msg) {
            try {
                OLocationManager oLocationManager = locationManagerWeakReference.get();
                if (oLocationManager == null) {
                    return;
                }
                switch (msg.what) {
                    case START_LOCATION:
                        OLocationOption oLocationOption = oLocationManager.oLocationOption;
                        if (oLocationOption == null) {
                            OLocationOption defaultOptions = new OLocationOption();
                            oLocationManager.setLocationOption(defaultOptions);
                            oLocationOption = oLocationManager.oLocationOption;
                        }

                        if (oLocationOption.isOnceLocationLatest()) {
                            getOnceLocationLatest(oLocationManager);
                        } else {
                            if (oLocationOption.isOnceLocation()) {
                                if (oLocationOption.isGpsFirst()) {
                                    oLocationManager.sdkLocationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, oLocationManager.sdkLocationListener, getLooper());
                                    sendEmptyMessageDelayed(LOCATION_TIMEOUT, oLocationOption.getGpsTimeOut());
                                } else {
                                    oLocationManager.sdkLocationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, oLocationManager.sdkLocationListener, getLooper());
                                    sendEmptyMessageDelayed(LOCATION_TIMEOUT, oLocationOption.getNetworkTimeOut());
                                }
                            } else {
                                if (oLocationOption.isGpsFirst()) {
                                    oLocationManager.sdkLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, oLocationOption.getInterval(), oLocationOption.getMinDistance(), oLocationManager.sdkLocationListener);
                                    sendEmptyMessageDelayed(LOCATION_TIMEOUT, oLocationOption.getGpsTimeOut());
                                } else {
                                    oLocationManager.sdkLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, oLocationOption.getInterval(), oLocationOption.getMinDistance(), oLocationManager.sdkLocationListener);
                                    sendEmptyMessageDelayed(LOCATION_TIMEOUT, oLocationOption.getNetworkTimeOut());
                                }
                                sendEmptyMessageDelayed(START_LOCATION, oLocationOption.getInterval());
                            }
                        }
                        break;

                    case STOP_LOCATION:
                        oLocationManager.sdkLocationManager.removeUpdates(oLocationManager.sdkLocationListener);
                        removeMessages(START_LOCATION);
                        break;
                    case LOCATION_TIMEOUT:
                        oLocationManager.oLocationListener.onError(-1, "location timeout");
                        break;
                }
            } catch (Throwable e) {
                Log.e(TAG, "handleMessage" + e.getMessage());
            }
        }

        @SuppressLint("MissingPermission")
        private void getOnceLocationLatest(OLocationManager oLocationManager) {
            Location lastGPSLocation = oLocationManager.sdkLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastGPSLocation == null) {
                Location lastNetWorkLocation = oLocationManager.sdkLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                Address address = oLocationManager.decodeLocation(lastNetWorkLocation);
                oLocationManager.oLocationListener.onLocationChanged(address);
            } else {
                Address address = oLocationManager.decodeLocation(lastGPSLocation);
                oLocationManager.oLocationListener.onLocationChanged(address);
            }
        }


    }
}
