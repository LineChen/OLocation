package com.line.locationtest;

import android.Manifest;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.line.location.MyLocationListener;
import com.line.location.MyLocationProvider;
import com.xiaoyu.lib.permission.AbsRequestPermissionCallBack;
import com.xiaoyu.lib.permission.PermissionSection;

import org.jetbrains.annotations.NotNull;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/**
 * 参考博客：https://www.jianshu.com/p/755170c47164
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void initLocationManager(View view) {
        checkPermission(this, new Function0<Unit>() {
            @Override
            public Unit invoke() {
                final MyLocationProvider myLocationProvider = new MyLocationProvider();
                myLocationProvider.getLocationManager(MainActivity.this, new MyLocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        super.onLocationChanged(location);
                        myLocationProvider.geocoder(MainActivity.this, location);
                    }
                });
                return null;
            }
        });
    }

    private void checkPermission(final AppCompatActivity activity, Function0<Unit> callback) {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        PermissionSection.INSTANCE.event(activity, permissions, callback, new AbsRequestPermissionCallBack(activity) {
            @Override
            public void onDenied(@NotNull String s) {
                Toast.makeText(activity, "为保障App使用过程中拍照功能的正常使用。", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNeverAsk(@NotNull String s) {

            }
        }, true);
    }
}
