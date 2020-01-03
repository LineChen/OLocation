package com.line.location.abst;

import android.location.Address;

import androidx.annotation.Nullable;

/**
 * Created by chenliu on 2020-01-02.
 */
public interface OLocationListener {
    void onLocationChanged(@Nullable Address address);

    void onError(int code, String msg);
}
