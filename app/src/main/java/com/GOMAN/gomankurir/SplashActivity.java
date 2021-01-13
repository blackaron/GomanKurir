package com.GOMAN.gomankurir;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.GOMAN.gomankurir.SharedPreferences.UserSession;

public class SplashActivity extends Activity {

    private static int SPLASH_TIME_OUT = 3000;
    private UserSession session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        session = new UserSession(SplashActivity.this);

        new Handler().postDelayed(new Runnable() {
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
//                startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                if (session.isFirstTimeLaunch()) {
                    session.setFirstTimeLaunch(false);
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
//                    launchHomeScreen();
//                    finish();
                }else{
                    launchHomeScreen();
                }

            }
        }, SPLASH_TIME_OUT);

    }
    private void launchHomeScreen() {
        if(session.isLoggedIn()) {
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(i);
        }else{
            Intent i = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(i);
        }
        finish();
    }
}
