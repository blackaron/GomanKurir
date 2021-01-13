package com.GOMAN.gomankurir;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.GOMAN.gomankurir.SharedPreferences.UserSession;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    //    SharedPreferences.Editor editor;
    private UserSession session;
    private HashMap<String, String> user;


    MaterialEditText Username, Email, Password, Phone;
    Button Enter, selectButton;
    private final int IMG_REQUEST = 1;
    Bitmap bitmap;
    CircleImageView imageView;
    ImageView TakePict;

    //firebase
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    //kode negara
    CountryCodePicker codePicker;

    String UID;
    Boolean uploadStatus = false;

    String stts = "belum aktif";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        session = new UserSession(RegisterActivity.this);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        UID = fAuth.getCurrentUser().getUid();

        Username = findViewById(R.id.name);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        Phone = findViewById(R.id.phone);
        Enter = findViewById(R.id.Register_New);
        imageView = findViewById(R.id.profilepic);
        selectButton = findViewById(R.id.uploadpic);
        codePicker = findViewById(R.id.ccp);

        selectButton.setOnClickListener(this);

        Enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtUsername = Username.getText().toString();
                String txtEmail = Email.getText().toString();
                String txtPassword = Password.getText().toString();
                String txtPhone = "+"+codePicker.getSelectedCountryCode()+Phone.getText().toString();
                //save phone number in sharedpreferences
                String dt = txtPhone;
                //status diabil dari sharedoprefernces
                String txtStatus = stts;
                if (TextUtils.isEmpty(txtUsername) || TextUtils.isEmpty(txtEmail)
                        || TextUtils.isEmpty(txtPassword) || TextUtils.isEmpty(txtPhone) || TextUtils.isEmpty(txtStatus)){
                    Toast.makeText(RegisterActivity.this, "All fields requiered", Toast.LENGTH_LONG).show();
                }else{
                    //call method register to save the data on server
                    register(txtUsername,txtEmail,txtPassword,txtPhone,txtStatus);
//                    session.setPhone(dt);
////                        checkUserProfile();
//                        //uploading to the server
                    if (!uploadStatus){
                        if (fAuth.getCurrentUser() != null){
                        }
                    }else {
                        Toast.makeText(RegisterActivity.this,"Data on server is not uploaded!!",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }

    private void register(final String Username, final String Email, final String Password, final String Phone,final String status){

        //loadingDialog
        final KProgressHUD progressHUD = new KProgressHUD(RegisterActivity.this);
        progressHUD.setCancellable(true)
                .setAutoDismiss(false)
                .setLabel("uploading...")
                .show();

        String url = "https://zainbananaserveronly.000webhostapp.com/secondDB/allnewregister.php";
//        for local
//        String url = "http://192.168.1.4/secondDB/allnewregister.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("Succesfully Registered")){
                    progressHUD.dismiss();
                    Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_LONG).show();
                }else{
                    progressHUD.dismiss();
                    Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_LONG ).show();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressHUD.dismiss();
                        Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("name", Username);
                params.put("email", Email);
                params.put("password", Password);
                params.put("phone", Phone);
                params.put("photo", imageToString(bitmap));
                params.put("status",status);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_MAX_RETRIES));
        MySingleton.getInstance(RegisterActivity.this).addToRequestQueue(request);
    }


    private void selectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==IMG_REQUEST && resultCode==RESULT_OK && data!=null){
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String imageToString(Bitmap bitmap1){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //menconvert image ke jpg format
        bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        //encode byte into String
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.uploadpic:
                selectImage();
                break;
        }
    }
}
