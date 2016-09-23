package com.mydd.att.statisticcontrol;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mydd.att.statisticcontrol.service.ServiceData;

public class MainActivity extends AppCompatActivity {

    private final static int DATA_UPDATE_INTERVAL = 20000;

    final Handler handler = new Handler();
    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            // Run service
            startRunService();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /***
     *
     * @param view
     */
    public void runService(View view) {
        startRunService();
        handler.postDelayed(runnable, DATA_UPDATE_INTERVAL);
    }

    /***
     * Start service
     */
    private void startRunService() {
        Intent serviceIntent = new Intent(getApplicationContext(), ServiceData.class);
        startService(serviceIntent);
    }
}
