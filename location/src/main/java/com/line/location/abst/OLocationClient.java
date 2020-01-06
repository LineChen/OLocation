package com.line.location.abst;

import android.content.Context;
import android.location.Address;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * Created by chenliu on 2020-01-02.
 */
public class OLocationClient {

    private static final String TAG = "OLocationClient";

    private OLocationManagerBase oLocationManagerBase;

    public OLocationClient(Context context) {
        try {
            if (context == null) {
                throw new IllegalArgumentException("Context参数不能为null");
            } else {
                oLocationManagerBase = new OLocationManager(context);
            }
        } catch (Throwable e) {
            Log.e(TAG, "OLocationClient:" + (e == null ? "" : e.getMessage()));
        }
    }

    public void setLocationOption(OLocationOption option) {
        try {
            if (option == null) {
                throw new IllegalArgumentException("OLocationOption参数不能为null");
            } else {
                if (oLocationManagerBase != null) {
                    oLocationManagerBase.setLocationOption(option);
                }
            }
        } catch (Throwable e) {
            Log.e(TAG, "setLocationOption:" + (e == null ? "" : e.getMessage()));
        }
    }

    public void startLocation() {
        try {
            if (oLocationManagerBase != null) {
                oLocationManagerBase.startLocation();
            }
        } catch (Throwable e) {
            Log.e(TAG, "startLocation:" + (e == null ? "" : e.getMessage()));
        }
    }

    public void stopLocation() {
        try {
            if (oLocationManagerBase != null) {
                oLocationManagerBase.stopLocation();
            }
        } catch (Throwable e) {
            Log.e(TAG, "stopLocation:" + (e == null ? "" : e.getMessage()));
        }
    }

    @Nullable
    public Address getLastKnownLocation() {
        try {
            if (oLocationManagerBase != null) {
                return oLocationManagerBase.getLastKnownLocation();
            }
        } catch (Throwable e) {
            Log.e(TAG, "getLastKnownLocation:" + (e == null ? "" : e.getMessage()));
        }
        return null;
    }


    public void setLocationListener(OLocationListener oLocationListener) {
        try {
            if (oLocationListener == null) {
                throw new IllegalArgumentException("OLocationListener参数不能为null");
            } else {
                if (oLocationManagerBase != null) {
                    oLocationManagerBase.setLocationListener(oLocationListener);
                }
            }
        } catch (Throwable e) {
            Log.e(TAG, "setLocationListener:" + (e == null ? "" : e.getMessage()));
        }
    }

    public void unRegisterLocationListener(OLocationListener oLocationListener) {
        try {
            if (oLocationManagerBase != null) {
                oLocationManagerBase.unRegisterLocationListener(oLocationListener);
            }
        } catch (Throwable e) {
            Log.e(TAG, "unRegisterLocationListener:" + (e == null ? "" : e.getMessage()));
        }
    }

    public void onDestroy() {
        try {
            if (oLocationManagerBase != null) {
                oLocationManagerBase.onDestroy();
            }
        } catch (Throwable e) {
            Log.e(TAG, "onDestroy:" + (e == null ? "" : e.getMessage()));
        }
    }

}
