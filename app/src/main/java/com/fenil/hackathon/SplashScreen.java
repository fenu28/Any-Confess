package com.fenil.hackathon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_SCREEN_TIME = 2000; //Splash Screen Time = 2 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        },SPLASH_SCREEN_TIME);
    }

    public void saveAndroidID() {
        SharedPreferences sharedPreferences = getSharedPreferences("androidID",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("androidID", Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID));
        editor.commit();
    }
    public String getAndroidID()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("androidID",MODE_PRIVATE);
        return sharedPreferences.getString("androidID","null");
    }
}