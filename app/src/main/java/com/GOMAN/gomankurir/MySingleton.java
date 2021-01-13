package com.GOMAN.gomankurir;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

public class MySingleton {

    private static MySingleton minstance;
    private RequestQueue requestQueue;
    private Context contex;

    public MySingleton(Context contex){
        this.contex = contex;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue(){
        if (requestQueue == null){
            Cache cache = new DiskBasedCache(contex.getCacheDir(), 1024*1024);
            Network network = new BasicNetwork(new HurlStack());
            requestQueue = new RequestQueue(cache, network);
            requestQueue = Volley.newRequestQueue(contex.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized MySingleton getInstance(Context context){
        if (minstance == null){
            minstance = new MySingleton(context);
        }
        return minstance;
    }

    public <T> void addToRequestQueue(Request<T> request){
        requestQueue.add(request);
    }


}
