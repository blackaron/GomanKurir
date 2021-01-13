package com.GOMAN.gomankurir.SharedPreferences;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.GOMAN.gomankurir.LoginActivity;

import java.util.HashMap;

public class UserSession {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    int private_mode = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "UserSessionPref";

    // First time logic Check
    public static final String FIRST_TIME = "firsttime";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    //driver id
    public static final String KEY_ID = "id";

    //driver name
    public static final String KEY_NAME = "name";

    //driver email
    public static final String KEY_EMAIL = "email";

    //driver phone
    public static final String KEY_PHONE = "phone";

    //driver status
    public static final String KEY_STATUS = "status";

    //driver foto
    public static final String KEY_FOTO = "foto";

    //token
    public static final String Token = "token";

    //setfirst time
    public static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";



    public UserSession(Context context){
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, private_mode);
        editor = pref.edit();
    }

    public void createLoginSession(String id, String name, String email, String phone, String status, String foto){
        //storing login value is true
        editor.putBoolean(IS_LOGIN, true);

        //driver id
        editor.putString(KEY_ID, id);

        //driver name
        editor.putString(KEY_NAME, name);

        //driver email
        editor.putString(KEY_EMAIL, email);

        //driver phone
        editor.putString(KEY_PHONE, phone);

        //driver status
        editor.putString(KEY_STATUS, status);

        //Driver foto
        editor.putString(KEY_FOTO, foto);

        editor.commit();
    }

    public HashMap<String, String> getDriverDetails(){
        HashMap<String, String> driver = new HashMap<>();

        //driver id
        driver.put(KEY_ID, pref.getString(KEY_ID, null));

        //driver name
        driver.put(KEY_NAME, pref.getString(KEY_NAME, null));

        //driver email
        driver.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        //driver phone
        driver.put(KEY_PHONE, pref.getString(KEY_PHONE, null));

        //driver status
        driver.put(KEY_STATUS, pref.getString(KEY_STATUS, null));

        //driver foto
        driver.put(KEY_FOTO, pref.getString(KEY_FOTO, null));

        return driver;

    }

    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(i);
        }
    }

    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.putBoolean(IS_LOGIN,false);
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public String getToken() {
        return pref.getString(Token, null);
    }

    public void setToken(String token){
        editor.putString(Token, token);
        editor.commit();
    }

    public String getPhone(){
        return pref.getString(Token, null);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public Boolean getFirstTime() {
        return pref.getBoolean(FIRST_TIME, true);
    }

    public void setFirstTime(Boolean b){
        editor.putBoolean(FIRST_TIME,b);
        editor.commit();
    }
}
