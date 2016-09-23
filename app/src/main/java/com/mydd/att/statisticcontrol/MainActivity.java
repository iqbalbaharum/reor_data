package com.mydd.att.statisticcontrol;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mydd.att.statisticcontrol.service.ServiceParking;
import com.mydd.att.statisticcontrol.service.ServiceTraffic;

public class MainActivity extends AppCompatActivity {

    private final static int DATA_UPDATE_INTERVAL = 20000;

    final Handler handler = new Handler();
    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            // Run service
            startRunService();
            startTrafficData();
            // delay for
            handler.postDelayed(this, DATA_UPDATE_INTERVAL);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /***
     *
     * @param view
     */
    public void runService(View view) {
        startRunService();
        startTrafficData();
        handler.postDelayed(runnable, DATA_UPDATE_INTERVAL);
    }

    /***
     * Start service
     */
    private void startRunService() {
        Intent serviceIntent = new Intent(getApplicationContext(), ServiceParking.class);
        startService(serviceIntent);
    }

    private void startTrafficData() {
        Intent serviceIntent = new Intent(getApplicationContext(), ServiceTraffic.class);
        startService(serviceIntent);
    }
}
