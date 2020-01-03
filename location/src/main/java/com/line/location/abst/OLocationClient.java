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
            Log.e(TAG, "");
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
            Log.e(TAG, "");
        }
    }

    public void startLocation() {
        try {
            if (oLocationManagerBase != null) {
                oLocationManagerBase.startLocation();
            }
        } catch (Throwable e) {
            Log.e(TAG, "");
        }
    }

    public void stopLocation() {
        try {
            if (oLocationManagerBase != null) {
                oLocationManagerBase.stopLocation();
            }
        } catch (Throwable e) {
            Log.e(TAG, "");
        }
    }

    @Nullable
    public Address getLastKnownLocation() {
        try {
            if (oLocationManagerBase != null) {
                return oLocationManagerBase.getLastKnownLocation();
            }
        } catch (Throwable e) {
            Log.e(TAG, "");
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
            Log.e(TAG, "");
        }
    }

    public void unRegisterLocationListener(OLocationListener oLocationListener) {
        try {
            if (oLocationManagerBase != null) {
                oLocationManagerBase.unRegisterLocationListener(oLocationListener);
            }
        } catch (Throwable e) {
            Log.e(TAG, "");
        }
    }

    public void onDestroy() {
        try {
            if (oLocationManagerBase != null) {
                oLocationManagerBase.onDestroy();
            }
        } catch (Throwable e) {
            Log.e(TAG, "");
        }
    }

}
