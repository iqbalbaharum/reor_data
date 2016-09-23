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

public class ServiceTraffic extends IntentService {

    public final static String TAG = "PARKING";

    private final static HashMap<String, String> DEVICES;

    static{
        DEVICES = new HashMap<>();
        DEVICES.put("8da6dde9b8b528ccee0e155e49db94ea", "39eb351a36d31faf6591fe76d78318a6");
        DEVICES.put("368056d38766cd091acf44b63b091409", "39eb351a36d31faf6591fe76d78318a6");
    }

    public final static String API_URL_ENDPOINT = "http://api-m2x.att.com/v2/devices/";
    public final static String API_URL_STREAM = "/streams/car_count/value";
    public final static String API_URL_VALUE = "?value=";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor
     */
    public ServiceTraffic() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        runTrafficData();
    }

    /***
     * Run parking data
     */
    private void runTrafficData() {

        for (Object o : DEVICES.entrySet()) {

            final Map.Entry pair = (Map.Entry) o;

            final Map<String, String> headers = new HashMap<>();
            headers.put("content", "application/json");
            headers.put("X-M2X-KEY", pair.getValue().toString());

            Random r = new Random();
            int i = r.nextInt(120 - 25) + 25;

            String pUrl = API_URL_ENDPOINT + pair.getKey() + API_URL_STREAM + API_URL_VALUE + i;

            // VERIFY USER
            StringRequest stringRequest = new StringRequest(
                    Request.Method.PUT,
                    pUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (!response.isEmpty()) {
                                Log.d(TAG, "traffic (" + pair.getKey() + ")- " + response.toString());
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
}
