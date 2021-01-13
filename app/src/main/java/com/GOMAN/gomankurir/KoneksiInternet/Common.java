package com.GOMAN.gomankurir.KoneksiInternet;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.GOMAN.gomankurir.SharedPreferences.UserSession;
import com.GOMAN.gomankurir.model.TokenModel;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;

public class Common {


    public static void updateToken(final Context context, final String newToken, final boolean isServer, final boolean isDriver){
        final UserSession session = new UserSession(context);
        HashMap<String, String> user;
        user = session.getDriverDetails();
        final String phone = user.get(UserSession.KEY_PHONE);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        UpdateTokenRequest request = new UpdateTokenRequest(phone, newToken,"1", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                new TokenModel(phone,newToken, isServer, isDriver);

                Log.d(phone, "nomerhape");
                Log.d(newToken, "tokenFCM");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(request);
    }
}
