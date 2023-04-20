package com.example.reminderlyfinalproject.model;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class ServiceClient {
    private static ServiceClient serviceClient;
    private static Context context;
    private RequestQueue requestQueue;

    private ServiceClient(Context ctx){
        context = ctx;
        }

        public static synchronized ServiceClient sharedServiceClient(Context ctx) {
        if (serviceClient == null){
            serviceClient = new ServiceClient(ctx);
        }
        return serviceClient;
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }
    public void addRequest(Request request){
        getRequestQueue().add(request);
    }
}
