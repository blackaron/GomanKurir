package com.GOMAN.gomankurir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.TestLooperManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.GOMAN.gomankurir.Adapter.MainOrderAdapter;
import com.GOMAN.gomankurir.KoneksiInternet.CekKoneksiInternet;
import com.GOMAN.gomankurir.KoneksiInternet.Common;
import com.GOMAN.gomankurir.KoneksiInternet.UpdateTokenRequest;
import com.GOMAN.gomankurir.SharedPreferences.UserSession;
import com.GOMAN.gomankurir.model.MainOrderModel;
import com.GOMAN.gomankurir.model.OrderModel;
import com.GOMAN.gomankurir.model.TokenModel;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.mikepenz.crossfadedrawerlayout.view.CrossfadeDrawerLayout;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.interfaces.ICrossfader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;
import com.mikepenz.materialize.util.UIUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    MainOrderAdapter mAdapter;
    private Drawer result;
    private static long backPressed;
    private CrossfadeDrawerLayout crossfadeDrawerLayout = null;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager mManager;
    RequestQueue mRequest;

    List<MainOrderModel> mModel;
    HashMap<String, String> user;

    Button startButton, offButton;
    TextView namaDriver, phone;

    private String mNama, mPhone, mFoto, mEmail, mId;
    private UserSession session;
    HashMap<String, String> list;
    private String url = "https://gomanpolinema.online/secondDB/driver/loadOrder.php" ;
    int count = 0;
    boolean isActive= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new UserSession(MainActivity.this);

        startButton = findViewById(R.id.startButton);
        offButton = findViewById(R.id.offButton);

        namaDriver = findViewById(R.id.TVnamaDriver);
        phone = findViewById(R.id.TVPhoneDriver);

        startButton.setOnClickListener(this);
        offButton.setOnClickListener(this);
        offButton.setVisibility(View.INVISIBLE);
        startButton.setVisibility(View.VISIBLE);
        mModel = new ArrayList<>();
        //cek koneksi
        new CekKoneksiInternet(MainActivity.this).checkConnection();

        mAdapter = new MainOrderAdapter(MainActivity.this, mModel);
        recyclerView = findViewById(R.id.orderRecycler);
        mRequest = Volley.newRequestQueue(MainActivity.this);

        mManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mManager);
        recyclerView.setAdapter(mAdapter);


        if (session.getFirstTime()) {
            //tap target view
            session.setFirstTime(false);
        }
        getValue();
        inflateNavDrawer();
    }

    public void content(){
        count++;
        getOrder();
        mModel.clear();
        mAdapter.notifyDataSetChanged();
        if (isActive){
//            getOrder();
            Refresh(5000);
        }
    }

    public void Refresh(int miliseconds){
        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                content();
                mModel.clear();
                mAdapter.notifyDataSetChanged();
            }
        };
        handler.postDelayed(runnable, miliseconds);
    }

    private void getOrder() {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++){
                        try {
                            JSONObject data = array.getJSONObject(i);
                            MainOrderModel model = new MainOrderModel();
                            model.setOrderId(data.getString("orderid"));
                            model.setNamaOrder(data.getString("CustomerName"));
                            model.setOrderAddress(data.getString("orderAddress"));
                            model.setOrderPrice(data.getString("orderPrice"));
                            model.setOrderComent(data.getString("orderComment"));
                            model.setTipePembayaran(data.getString("tipePembayaran"));
                            model.setNamaRestoran(data.getString("namaRestoran"));
                            model.setAlamat(data.getString("alamat"));


                            mModel.add(model);
                            Collections.reverse(mModel);
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

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> item = new HashMap<>();
                item.put("ID_KURIR", mId);
                return item;
            }
        };
        mRequest.add(request);
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

        namaDriver.setText(mNama);
        phone.setText(mPhone);

    }

    public void UpdateOrder(View view){
        if (!isActive){
            Toast.makeText(MainActivity.this, "tidak bisa ambil orderan", Toast.LENGTH_LONG).show();
        }else {
            TextView orderid = (TextView) view.findViewById(R.id.TVorderid);
            TextView price = (TextView) view.findViewById(R.id.TVorderPrice);
            TextView orderName = (TextView) view.findViewById(R.id.TVnamaPenerima);
            TextView address = (TextView) view.findViewById(R.id.TVorderAddress);
            TextView coment = (TextView) view.findViewById(R.id.TVComment);
            TextView tipePembayaran = (TextView) view.findViewById(R.id.TVtipePembayaran);
            TextView namaRestoran = (TextView) view.findViewById(R.id.TVNamaRestoran);
            TextView alamat = (TextView) view.findViewById(R.id.TVAlamat);

            Intent intent = new Intent(MainActivity.this, Order.class);
            intent.putExtra(OrderModel.mOrderId, orderid.getText().toString());
            intent.putExtra(OrderModel.mHarga, price.getText().toString());
            intent.putExtra(OrderModel.mNamaPenerima, orderName.getText().toString());
            intent.putExtra(OrderModel.mAlamat, address.getText().toString());
            intent.putExtra(OrderModel.mComent, coment.getText().toString());
            intent.putExtra(OrderModel.mTipePembayaran, tipePembayaran.getText().toString());
            intent.putExtra(OrderModel.mNamaRestoran, namaRestoran.getText().toString());
            intent.putExtra(OrderModel.mAlamatRestoran, alamat.getText().toString());
            startActivityForResult(intent, 1);
            offToken();
        }

    }

    private void inflateNavDrawer() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        IProfile iProfile = new ProfileDrawerItem()
                .withName(mNama)
                .withName(mPhone)
                .withEmail(mEmail);

        AccountHeader accountHeader =new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.color.md_cyan_500)
                .addProfiles(iProfile)
                .withCompactStyle(true)
                .build();

        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("home").withIcon(R.drawable.home);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName("logout").withIcon(R.drawable.logout);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(2).withName("Report Order").withIcon(R.drawable.report_order);

        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHasStableIds(true)
                .withDrawerLayout(R.layout.crossfade_drawer)
                .withAccountHeader(accountHeader)
                .withDrawerWidthDp(72)
                .withGenerateMiniDrawer(true)
                .withTranslucentStatusBar(true)
                .addDrawerItems(item1, item2,item3)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch (position){
                            case 1:
                                if (result != null && result.isDrawerOpen()){
                                    result.closeDrawer();
                                }
                                break;
                            case 2:
                                session.logoutUser();
                                finish();
                                break;
                            case 3:
                                startActivity(new Intent(MainActivity.this, OrderReport.class));
                                finish();
                                default:
                                    Toast.makeText(MainActivity.this, "DEFAULT", Toast.LENGTH_LONG).show();
                        }
                        return true;
                    }
                }).build();

        crossfadeDrawerLayout = (CrossfadeDrawerLayout) result.getDrawerLayout();
        //define max drawer
        crossfadeDrawerLayout.setMaxWidthPx(DrawerUIUtils.getOptimalDrawerWidth(this));
        final MiniDrawer miniDrawer = result.getMiniDrawer();
        View view = miniDrawer.build(this);
        view.setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(this, com.mikepenz.materialdrawer.R.attr.material_drawer_background, com.mikepenz.materialdrawer.R.color.material_drawer_background));
        crossfadeDrawerLayout.getSmallView().addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        miniDrawer.withCrossFader(new ICrossfader() {
            @Override
            public void crossfade() {
                boolean isFaded = isCrossfaded();
                crossfadeDrawerLayout.crossfade();

                //only close the drawer if we were already faded and want to close it now
                if (isFaded){
                    result.getDrawerLayout().closeDrawer(GravityCompat.START);
                }
            }

            @Override
            public boolean isCrossfaded() {
                return crossfadeDrawerLayout.isCrossfaded();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
//            super.onBackPressed();
            if (backPressed + 2000 > System.currentTimeMillis()) super.onBackPressed();
            else Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
            backPressed = System.currentTimeMillis();
        }
    }
    @Override
    protected void onResume() {

        //check Internet Connection
        new CekKoneksiInternet(this).checkConnection();
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void updateToken() {
        isActive = true;
        updateStatus("1");
//        get all order(not used)
       content();

    }

    private void offToken() {
        isActive = false;
        updateStatus("2");
        //clear data in recycler view
        mModel.clear();
        mAdapter.notifyDataSetChanged();
    }

    public void updateStatus(final String status){
        String updateStatusUrl = "https://gomanpolinema.online/secondDB/updateStatusDriver.php";
        StringRequest request = new StringRequest(Request.Method.POST, updateStatusUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> item = new HashMap<>();
                item.put("statusKurir", status);
                item.put("idKurir", mId);
                Log.d("update", status);
                return item;
            }
        };
        mRequest.add(request);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.startButton:
                startButton.setVisibility(View.INVISIBLE);
                offButton.setVisibility(View.VISIBLE);
                updateToken();
                break;

            case R.id.offButton:
                startButton.setVisibility(View.VISIBLE);
                offButton.setVisibility(View.INVISIBLE);
                offToken();
                break;
        }
    }
}
