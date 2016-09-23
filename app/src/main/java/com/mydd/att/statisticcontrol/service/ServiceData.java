package com.mydd.att.statisticcontrol.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mydd.att.statisticcontrol.volley.VolleyErrorHelper;
import com.mydd.att.statisticcontrol.volley.VolleyRequestQueue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by MuhammadNaqiuddin on 23-Sep-16.
 */

public class ServiceData extends IntentService {

    private final static String TAG = "SERVICE";

    private final static String API_URL = "http://api-m2x.att.com/v2/devices/b83aa74556918c978be7803de33133bb/streams/acidic_gas/value?value=";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public ServiceData() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        runParkingData();
    }

    /***
     * Run parking data
     */
    private void runParkingData() {

        final Map<String, String> headers = new HashMap<>();
        headers.put("content", "application/json");
        headers.put("X-M2X-KEY", "c8742a3c2a34b3fb44ff0ce2d788deba");

        Random r = new Random();
        int i = r.nextInt(80 - 65) + 65;

        String pUrl = API_URL + i;

        // VERIFY USER
        StringRequest stringRequest = new StringRequest(
                Request.Method.PUT,
                pUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!response.isEmpty()) {
                            Log.d(TAG, response.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyErrorHelper.displayError(error, getApplicationContext());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers;
            }
        };

        VolleyRequestQueue.getInstance(this).addToRequestQueue(stringRequest, TAG);
    }
}
