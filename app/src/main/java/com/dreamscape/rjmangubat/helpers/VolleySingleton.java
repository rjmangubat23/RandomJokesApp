package com.dreamscape.rjmangubat.helpers;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Web service helper using the Volley API
 * Created by rj mangubat on 4/26/2015.
 */
public class VolleySingleton {

    private static VolleySingleton instance;
    private RequestQueue requestQueue;
    private Context context;

    private VolleySingleton(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public static VolleySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new VolleySingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

}
