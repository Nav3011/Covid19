package com.example.covid19;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends Activity {

    public static int SPLASH_TIME=2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                Intent i =new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        },SPLASH_TIME);
    }
}
