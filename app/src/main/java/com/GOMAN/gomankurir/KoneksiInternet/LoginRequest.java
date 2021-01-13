package com.GOMAN.gomankurir.KoneksiInternet;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    private static final String LOGIN_url = "https://gomanpolinema.online/secondDB/driver/loginDriver.php";
    private Map<String, String> parameters;

    public LoginRequest(String email,String password, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(Method.POST, LOGIN_url, listener, errorListener);
        parameters = new HashMap<>();
        parameters.put("email", email);
        parameters.put("password", password);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
