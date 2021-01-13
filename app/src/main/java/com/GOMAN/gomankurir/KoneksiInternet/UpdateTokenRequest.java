package com.GOMAN.gomankurir.KoneksiInternet;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UpdateTokenRequest extends StringRequest {
    private static final String url = "https://gomanpolinema.online/secondDB/driver/updateToken.php";
    private HashMap<String, String> parameters;

    public UpdateTokenRequest(String phone, String token,String IsServerToken, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(Method.POST, url, listener, errorListener);
        parameters = new HashMap<>();
        parameters.put("phone", phone);
        parameters.put("token", token);
        parameters.put("IsServerToken", IsServerToken);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
