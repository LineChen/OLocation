package com.line.locationtest;

import android.location.Address;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.line.location.abst.OLocationClient;
import com.line.location.abst.OLocationListener;
import com.line.location.abst.OLocationOption;

public class OLocationActivity extends AppCompatActivity {

    TextView resultTextView;
    OLocationClient oLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olocation);
        resultTextView = findViewById(R.id.tvResult);
        oLocationClient = new OLocationClient(this);
    }

    public void getLastKnownLocation(View view) {
        resultTextView.setText("getLastKnownLocation:\n");
        OLocationClient oLocationClient = new OLocationClient(this);
        Address lastKnownLocation = oLocationClient.getLastKnownLocation();
        if (lastKnownLocation != null) {
            resultTextView.append(lastKnownLocation.toString());
        }
    }

    public void startOnceGPSLocation(View view) {
        resultTextView.setText("startOnceGPSLocation:\n");
        OLocationOption option = new OLocationOption();
        option.setGpsFirst(true);
        option.setOnceLocation(true);
        oLocationClient.setLocationOption(option);
        oLocationClient.setLocationListener(new OLocationListener() {
            @Override
            public void onLocationChanged(@Nullable Address address) {
                if (address != null) {
                    resultTextView.append(address.toString());
                }
            }

            @Override
            public void onError(int code, String msg) {
                resultTextView.append("error:" + msg);
            }
        });
        oLocationClient.startLocation();
    }

    public void startOnceNetLocation(View view) {
        resultTextView.setText("startOnceNetLocation:\n");
        OLocationOption option = new OLocationOption();
        option.setGpsFirst(false);
        option.setOnceLocation(true);
        oLocationClient.setLocationOption(option);
        oLocationClient.setLocationListener(new OLocationListener() {
            @Override
            public void onLocationChanged(@Nullable Address address) {
                if (address != null) {
                    resultTextView.append(address.toString());
                }
            }

            @Override
            public void onError(int code, String msg) {
                resultTextView.append("error:" + msg);
            }
        });
        oLocationClient.startLocation();
    }

    public void startGPSLocation(View view) {
        resultTextView.setText("startGPSLocation:\n");
        OLocationOption option = new OLocationOption();
        option.setGpsFirst(true);
        option.setOnceLocation(false);
        option.setInterval(10000L);
        oLocationClient.setLocationOption(option);
        oLocationClient.setLocationListener(new OLocationListener() {
            @Override
            public void onLocationChanged(@Nullable Address address) {
                if (address != null) {
                    resultTextView.append(address.toString());
                }
            }

            @Override
            public void onError(int code, String msg) {
                resultTextView.append("error:" + msg);
            }
        });
        oLocationClient.startLocation();
    }

    public void startNetLocation(View view) {
        resultTextView.setText("startNetLocation:\n");
        OLocationOption option = new OLocationOption();
        option.setGpsFirst(false);
        option.setOnceLocation(false);
        option.setInterval(5000L);
        oLocationClient.setLocationOption(option);
        oLocationClient.setLocationListener(new OLocationListener() {
            @Override
            public void onLocationChanged(@Nullable Address address) {
                if (address != null) {
                    resultTextView.append(address.toString());
                }
            }

            @Override
            public void onError(int code, String msg) {
                resultTextView.append("error:" + msg);
            }
        });
        oLocationClient.startLocation();
    }

    public void stopLocation(View view) {
        oLocationClient.stopLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        oLocationClient.onDestroy();
    }

}
