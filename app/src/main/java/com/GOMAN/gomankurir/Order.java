package com.GOMAN.gomankurir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.GOMAN.gomankurir.model.OrderModel;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Order extends AppCompatActivity {

    TextView namaOrder,harga,alamat,tipePembayaran,coment,orderid, namaRetoran, alamatRestoran;
    Button batal, ambil, diterima;
    RequestQueue mrequest;

    String mnamaOrder,mharga,malamat,mtipePembayaran,mcoment, morderid, mNamaRestoran, mAlamatRestoran;
    private String url = "https://gomanpolinema.online/secondDB/driver/batalOrder.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        namaOrder = findViewById(R.id.namaCustomer);
        orderid = findViewById(R.id.orderID);
        harga = findViewById(R.id.harga);
        alamat = findViewById(R.id.alamat);
        tipePembayaran = findViewById(R.id.tipePembayaran);
        coment = findViewById(R.id.coment);
        namaRetoran = findViewById(R.id.tvNamaRestotan);
        alamatRestoran = findViewById(R.id.tvAlamat);

        batal = findViewById(R.id.batal);
        ambil = findViewById(R.id.ambil);
        diterima = findViewById(R.id.sampai);

        //getdatafromintent
        mnamaOrder = getIntent().getStringExtra(OrderModel.mNamaPenerima);
        morderid = getIntent().getStringExtra(OrderModel.mOrderId);
        mharga = getIntent().getStringExtra(OrderModel.mHarga);
        malamat = getIntent().getStringExtra(OrderModel.mAlamat);
        mtipePembayaran = getIntent().getStringExtra(OrderModel.mTipePembayaran);
        mcoment = getIntent().getStringExtra(OrderModel.mComent);
        mNamaRestoran = getIntent().getStringExtra(OrderModel.mNamaRestoran);
        mAlamatRestoran = getIntent().getStringExtra(OrderModel.mAlamatRestoran);

        mrequest = Volley.newRequestQueue(Order.this);

        inisiasi();

        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                batalOrder();
                onBackPressed();
            }
        });
    }

    private void batalOrder() {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Order.this, response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Order.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> item = new HashMap<>();
                item.put("orderid", morderid);
                return item;
            }
        };
        mrequest.add(request);
    }

    private void inisiasi() {
        namaOrder.setText(mnamaOrder);
        orderid.setText(morderid);
        harga.setText(mharga);
        alamat.setText(malamat);
        tipePembayaran.setText(mtipePembayaran);
        coment.setText(mcoment);
        namaRetoran.setText(mNamaRestoran);
        alamatRestoran.setText(mAlamatRestoran);
    }

    public void updateOrder(View view){
        ambil.setVisibility(View.INVISIBLE);
        diterima.setVisibility(View.VISIBLE);
        String url1 = "https://gomanpolinema.online/secondDB/driver/ambilOrder.php";
        StringRequest request = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Order.this, response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Order.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> item = new HashMap<>();
                item.put("orderid", morderid);
                Log.d("data", morderid);
                item.put("orderStatus", "2");
                return item;
            }
        };
        MySingleton.getInstance(Order.this).addToRequestQueue(request);
    }
    public void diterima(View view){
        diterima.setVisibility(View.INVISIBLE);
        batal.setVisibility(View.INVISIBLE);
        String url1 = "https://gomanpolinema.online/secondDB/driver/ambilOrder.php";
        StringRequest request = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Order.this, response, Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Order.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> item = new HashMap<>();
                item.put("orderid", morderid);
                Log.d("data", morderid);
                item.put("orderStatus", "3");
                return item;
            }
        };
        MySingleton.getInstance(Order.this).addToRequestQueue(request);
    }


}
