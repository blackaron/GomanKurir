package com.GOMAN.gomankurir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.GOMAN.gomankurir.Adapter.CustomPenghasilanAdapter;
import com.GOMAN.gomankurir.Adapter.PenghasilanAdapter;
import com.GOMAN.gomankurir.Adapter.TransaksiAdapter;
import com.GOMAN.gomankurir.SharedPreferences.UserSession;
import com.GOMAN.gomankurir.model.PenghasilanModel;
import com.GOMAN.gomankurir.model.TransaksiModel;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.savvi.rangedatepicker.CalendarPickerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderReport extends AppCompatActivity implements View.OnClickListener{

    TransaksiAdapter tAdapter;
    PenghasilanAdapter pAdapter;
    CustomPenghasilanAdapter mAdapter;

    RecyclerView tRecycle, pRecycle, mRecycle;
    RecyclerView.LayoutManager mManager,pManager;
    RequestQueue mRequest;
    List<PenghasilanModel> PModel;
    List<TransaksiModel> TModel;

    HashMap<String, String> user;
    private String mNama, mPhone, mFoto, mEmail, mId;

    private String url = "https://gomanpolinema.online/secondDB/driver/Transaksi.php";

    UserSession session;
    Button harian, bulanan, cari;
    TextView dari, sampai;
    LinearLayout lin, lin2;

    int y, m, d;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_report);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Calendar calendar = Calendar.getInstance();
        y = calendar.get(Calendar.YEAR);
        m = calendar.get(Calendar.MONTH);
        d = calendar.get(Calendar.DAY_OF_MONTH);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        session = new UserSession(OrderReport.this);

        harian = (Button) findViewById(R.id.harian);
        bulanan = (Button) findViewById(R.id.bulanan);

        harian.setOnClickListener(this);
        bulanan.setOnClickListener(this);

        tRecycle = findViewById(R.id.recy1);
        pRecycle = findViewById(R.id.recy2);
        mRecycle = findViewById(R.id.recy3);

        dari = findViewById(R.id.dari);
        sampai = findViewById(R.id.sampai);

        lin = findViewById(R.id.editText);
        lin2 = findViewById(R.id.lin2);
        cari = findViewById(R.id.cari);
        cari.setOnClickListener(this);

        dari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTanggal(dari);
//                DatePickerDialog dialog = new DatePickerDialog(
//                        OrderReport.this, R.style.Theme_AppCompat_DayNight_Dialog_MinWidth,
//                        onDateSetListener, y, m, d);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
//                dialog.show();
//
//                onDateSetListener = new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        month = month + 1;
//                        Log.d("Calender",  year+"-"+month+"-"+dayOfMonth);
//                        String date = year +"-"+month+"-"+dayOfMonth;
//                        dari.setText(date);
//                    }
//                };
            }
        });

        sampai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTanggal(sampai);
//                DatePickerDialog datePickerDialog = new DatePickerDialog(
//                        OrderReport.this, R.style.Theme_AppCompat_DayNight_Dialog_MinWidth,
//                        onDateSetListener, y, m, d);
//                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
//                datePickerDialog.show();
//
//                onDateSetListener = new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        month = month + 1;
//                        Log.d("sampai",  year+"-"+month+"-"+dayOfMonth);
//                        String date = year +"-"+month+"-"+dayOfMonth;
//                        sampai.setText(date);
//                    }
//                };
            }
        });

        pRecycle.setVisibility(View.VISIBLE);

        TModel = new ArrayList<>();
        PModel = new ArrayList<>();

        tAdapter = new TransaksiAdapter(OrderReport.this, TModel);
        pAdapter = new PenghasilanAdapter(OrderReport.this, PModel);
        mAdapter = new CustomPenghasilanAdapter(OrderReport.this, PModel);

//        mManager = new LinearLayoutManager(OrderReport.this, LinearLayoutManager.VERTICAL, false);
//        pManager = new LinearLayoutManager(OrderReport.this, LinearLayoutManager.VERTICAL, false);

        tRecycle.setLayoutManager(new LinearLayoutManager(this));
        tRecycle.setHasFixedSize(false);
        tRecycle.setAdapter(tAdapter);

        pRecycle.setLayoutManager(new LinearLayoutManager(this));
        pRecycle.setHasFixedSize(false);
        pRecycle.setAdapter(pAdapter);

        mRecycle.setLayoutManager(new LinearLayoutManager(this));
        mRecycle.setHasFixedSize(false);
        mRecycle.setAdapter(mAdapter);

        getTransaksi();
        getValue();

    }

    private void getTanggal(final TextView v){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                OrderReport.this, R.style.Theme_AppCompat_DayNight_Dialog_MinWidth,
                onDateSetListener, y, m, d);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        datePickerDialog.show();

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;

                Log.d("sampai",  year+"-"+month+"-"+dayOfMonth);
                String date = year +"-"+cekMonth(month)+"-"+dayOfMonth;
                v.setText(date);
            }
        };
    }

    public static String cekMonth(int month) {
            switch (month){
                case 1 :
                    return "01";
                case 2:
                    return "02";
                case 3:
                    return "03";
                case 4:
                    return "04";
                case 5:
                    return "05";
                case 6:
                    return "06";
                case 7:
                    return "07";
                case 8:
                    return "08";
                case 9:
                    return "09";
                default:
                    return "0";
            }
        }

    private void getBulanan() {
        final KProgressHUD progressHUD = new KProgressHUD(OrderReport.this)
                .setCancellable(true)
                .setAutoDismiss(false)
                .setLabel("loading")
                .show();
        Log.d("orderReportTest", "getBulanan: ");
        String u = "https://gomanpolinema.online/secondDB/driver/customPenghasilan.php";
        StringRequest request = new StringRequest(Request.Method.POST, u, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressHUD.dismiss();
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++){
                        try {
                            JSONObject data = array.getJSONObject(i);
                            PenghasilanModel model = new PenghasilanModel();
                            model.setTotal(data.getString("total"));
                            Log.d("orderReportTest", data.getString("total"));

                            PModel.add(model);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressHUD.dismiss();
                Toast.makeText(OrderReport.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("dari", dari.getText().toString());
                params.put("sampai", sampai.getText().toString());
                params.put("idKurir", mId);

                Log.d("orderReportTest", dari.getText().toString());
                Log.d("orderReportTest", sampai.getText().toString());
                Log.d("orderReportTest", mId);

                return params;
            }
        };
        MySingleton.getInstance(OrderReport.this).addToRequestQueue(request);
    }

    private void getPenghasilan() {
        Log.d("orderReportTest", "getPenghasilan: ");
            String link = "https://gomanpolinema.online/secondDB/driver/penghasilan.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i =0; i < array.length(); i++){
                        try {
                            JSONObject data = array.getJSONObject(i);
                            PenghasilanModel model = new PenghasilanModel();
                            model.setTotal(data.getString("total"));

                            PModel.add(model);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        pAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OrderReport.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> item = new HashMap<>();
                item.put("idKurir", mId);
                return item;
            }
        };
        MySingleton.getInstance(OrderReport.this).addToRequestQueue(stringRequest);
    }

    private void getTransaksi() {
        final KProgressHUD progressHUD = new KProgressHUD(OrderReport.this)
                .setCancellable(true)
                .setAutoDismiss(false)
                .setLabel("loading")
                .show();

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressHUD.dismiss();
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i <array.length(); i++){
                        try {
                            JSONObject data = array.getJSONObject(i);
                            TransaksiModel model = new TransaksiModel();
                            model.setIdPesanan(data.getString("orderid"));
                            model.setNamaUser(data.getString("CustomerName"));
                            model.setIdUser(data.getString("userId"));
                            model.setTotalHarga(data.getString("orderPrice"));
                            model.setIdRestoran(data.getString("idRes"));
                            model.setNamaRestoran(data.getString("namarestoran"));

                            TModel.add(model);
                            Collections.reverse(TModel);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                        tAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressHUD.dismiss();
                Toast.makeText(OrderReport.this, error.getMessage(), Toast.LENGTH_LONG ).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("idKurir", mId);
                return params;
            }
        };
        MySingleton.getInstance(OrderReport.this).addToRequestQueue(request);
    }

    private void getValue() {
        session = new UserSession(getApplicationContext());
        //validating session
        session.isLoggedIn();
        //get drivaer detail in login
        user = session.getDriverDetails();

        mNama = user.get(UserSession.KEY_NAME);
        mPhone = user.get(UserSession.KEY_PHONE);
        mEmail = user.get(UserSession.KEY_EMAIL);
        mFoto = user.get(UserSession.KEY_FOTO);
        mId = user.get(UserSession.KEY_ID);

    }

    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(OrderReport.this, MainActivity.class));
        finish();
//        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.harian:
                Log.d("orderReportTest", "this sukses ");
                pRecycle.setVisibility(View.VISIBLE);
                lin.setVisibility(View.INVISIBLE);
                lin2.setVisibility(View.INVISIBLE);
                mRecycle.setVisibility(View.INVISIBLE);
                PModel.clear();
                pAdapter.notifyDataSetChanged();
                mAdapter.notifyDataSetChanged();
                getPenghasilan();
                break;
            case R.id.bulanan:
                PModel.clear();
                pAdapter.notifyDataSetChanged();
                pRecycle.setVisibility(View.INVISIBLE);
                mRecycle.setVisibility(View.VISIBLE);
                lin.setVisibility(View.VISIBLE);
                lin2.setVisibility(View.VISIBLE);
                break;
            case R.id.cari:
                getBulanan();
            default:break;
        }
    }
}