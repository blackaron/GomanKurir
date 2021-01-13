package com.GOMAN.gomankurir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.GOMAN.gomankurir.KoneksiInternet.CekKoneksiInternet;
import com.GOMAN.gomankurir.KoneksiInternet.LoginRequest;
import com.GOMAN.gomankurir.SharedPreferences.UserSession;
import com.GOMAN.gomankurir.model.MainOrderModel;
import com.android.volley.NetworkError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private UserSession session;
    public String status = "aktiv";

    private EditText edtemail,edtpass;
    private String email,pass;
    private TextView forgotpass;
    private RequestQueue requestQueue;
    public static final String TAG = "MyTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtemail = findViewById(R.id.login_email);
        edtpass = findViewById(R.id.login_pass);

        Bundle registerInfo = getIntent().getExtras();

        if (registerInfo != null){
            edtemail.setText(registerInfo.getString("email"));
        }

        session = new UserSession(LoginActivity.this);

//        if (session.isLoggedIn()) {
//            Intent i = new Intent(LoginActivity.this,MainActivity.class);
//            startActivity(i);
//            Log.d("kau","main");
//        }

        requestQueue = Volley.newRequestQueue(LoginActivity.this);

        forgotpass = findViewById(R.id.forgot_pass);
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,
                        ForgotPasswordActivity.class));
                finish();
            }
        });

        Button button = findViewById(R.id.login_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }


    public void login(){
        email = edtemail.getText().toString();
        pass = edtpass.getText().toString();
        Log.d("username", email);
        Log.d("password", pass);
        final KProgressHUD progressDialog=  KProgressHUD.create(LoginActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        LoginRequest loginRequest = new LoginRequest(email, pass, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
                try{
                    JSONObject data = new JSONObject(response);
                    if (data.getBoolean("success")){

                        //getData from json(Database)
                        String sessionId = data.getString("driverId");
                        String sessionName = data.getString("driverName");
                        String sessionPhone = data.getString("driverPhone");
                        String sessionEmail = data.getString("driverEmail");
                        String sessionStatus = data.getString("driverStatus");
                        String sessionFoto = data.getString("driverFoto");

                        //store data to sharedPreferences
                        session.createLoginSession(sessionId,sessionName,sessionEmail,sessionPhone,sessionStatus,sessionFoto);

                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }else{
                        progressDialog.dismiss();
                        if (data.getString("status").equals("INVALID")){
                            Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(LoginActivity.this, "password not match", Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "Bad Response From Server", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if (error instanceof ServerError)
                    Toast.makeText(LoginActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                else if (error instanceof TimeoutError)
                    Toast.makeText(LoginActivity.this, "Connection Timed Out", Toast.LENGTH_SHORT).show();
                else if (error instanceof NetworkError)
                    Toast.makeText(LoginActivity.this, "Bad Network Connection", Toast.LENGTH_SHORT).show();
            }
        });
        loginRequest.setTag(TAG);
        requestQueue.add(loginRequest);
    }

    private boolean validatePassword(String pass) {


        if (pass.length() < 1 || pass.length() > 20) {
            edtpass.setError("Password Must consist of 4 to 20 characters");
            return false;
        }
        return true;
    }

    private boolean validateUsername(String email) {

        if (email.length() < 1 || email.length() > 30) {
            edtemail.setError("Email Must consist of 4 to 30 characters");
            return false;
        }
//        else if (!email.matches("^[A-za-z0-9.@]+")) {
//            edtemail.setError("Only . and @ characters allowed");
//            return false;
//        }
//        else if (!email.contains("@") || !email.contains(".")) {
//            edtemail.setError("Email must contain @ and .");
//            return false;
//        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Login CheckPoint","LoginActivity resumed");
        //check Internet Connection
        new CekKoneksiInternet(this).checkConnection();

    }

    @Override
    protected void onStop () {
        super.onStop();
        Log.e("Login CheckPoint","LoginActivity stopped");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
