package com.line.location.abst;

import android.location.Address;

import androidx.annotation.Nullable;

/**
 * Created by chenliu on 2020-01-02.
 */
public interface OLocationManagerBase {

    void setLocationOption(OLocationOption option);

    void startLocation();

    void stopLocation();

    @Nullable
    Address getLastKnownLocation();

    void setLocationListener(OLocationListener oLocationListener);

    void unRegisterLocationListener(OLocationListener oLocationListener);

    void onDestroy();

}
