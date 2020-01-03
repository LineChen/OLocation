package com.line.location.abst;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chenliu on 2020-01-02.
 */
public class OLocationOption implements Parcelable {

    /**
     * 优先使用GPS定位
     */
    private boolean gpsFirst;

    /**
     * GPS定位超时
     */
    private long gpsTimeOut;

    /**
     * 网络定位超时
     */
    private long networkTimeOut;

    /**
     * 定时发起定位间隔
     */
    private long interval;

    /**
     * minimum distance between location updates, in meters
     */
    private int minDistance;

    /**
     * 只请求一次
     */
    private boolean onceLocation;

    /**
     * 请求最近一次的位置
     */
    private boolean onceLocationLatest;

    public OLocationOption() {
        gpsFirst = false;
        gpsTimeOut = 30000L;
        networkTimeOut = 30000L;
        interval = 5000L;
        minDistance = 10;
        onceLocation = true;
        onceLocationLatest = false;
    }


    public OLocationOption setGpsFirst(boolean gpsFirst) {
        this.gpsFirst = gpsFirst;
        return this;
    }

    public OLocationOption setGpsTimeOut(long gpsTimeOut) {
        if (gpsTimeOut < 5000L) {
            gpsTimeOut = 5000L;
        }

        if (gpsTimeOut > 30000L) {
            gpsTimeOut = 30000L;
        }

        this.gpsTimeOut = gpsTimeOut;
        return this;
    }

    public OLocationOption setNetworkTimeOut(long networkTimeOut) {
        this.networkTimeOut = networkTimeOut;
        return this;
    }

    public OLocationOption setInterval(long interval) {
        if (interval <= 800L) {
            interval = 800L;
        }
        this.interval = interval;
        return this;
    }

    public OLocationOption setMinDistance(int minDistance) {
        this.minDistance = minDistance;
        return this;
    }

    public OLocationOption setOnceLocation(boolean onceLocation) {
        this.onceLocation = onceLocation;
        return this;
    }

    public OLocationOption setOnceLocationLatest(boolean onceLocationLatest) {
        this.onceLocationLatest = onceLocationLatest;
        return this;
    }

    public boolean isGpsFirst() {
        return gpsFirst;
    }

    public long getGpsTimeOut() {
        return gpsTimeOut;
    }

    public long getNetworkTimeOut() {
        return networkTimeOut;
    }

    public long getInterval() {
        return interval;
    }

    public int getMinDistance() {
        return minDistance;
    }

    public boolean isOnceLocation() {
        return onceLocation;
    }

    public boolean isOnceLocationLatest() {
        return onceLocationLatest;
    }

    protected OLocationOption(Parcel in) {
        gpsFirst = in.readByte() != 0;
        gpsTimeOut = in.readLong();
        networkTimeOut = in.readLong();
        interval = in.readLong();
        minDistance = in.readInt();
        onceLocation = in.readByte() != 0;
        onceLocationLatest = in.readByte() != 0;
    }

    public static final Creator<OLocationOption> CREATOR = new Creator<OLocationOption>() {
        @Override
        public OLocationOption createFromParcel(Parcel in) {
            return new OLocationOption(in);
        }

        @Override
        public OLocationOption[] newArray(int size) {
            return new OLocationOption[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (gpsFirst ? 1 : 0));
        dest.writeLong(gpsTimeOut);
        dest.writeLong(networkTimeOut);
        dest.writeLong(interval);
        dest.writeInt(minDistance);
        dest.writeByte((byte) (onceLocation ? 1 : 0));
        dest.writeByte((byte) (onceLocationLatest ? 1 : 0));
    }
}
