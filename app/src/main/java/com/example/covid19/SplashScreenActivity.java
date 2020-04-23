package com.example.covid19;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends Activity {

    public static int SPLASH_TIME=2000;
    public static int CONNECTED=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if(ConnectionCheck.isConnected(this))
            CONNECTED=1;
        else
            CONNECTED=0;

        new Handler().postDelayed(new Runnable(){
            Intent i;
            @Override
            public void run() {
                if (CONNECTED==1)
                    i =new Intent(SplashScreenActivity.this, MainActivity.class);
                else
                    i =new Intent(SplashScreenActivity.this, NoConnectionActivity.class);
                startActivity(i);
            }
        },SPLASH_TIME);
    }
}
