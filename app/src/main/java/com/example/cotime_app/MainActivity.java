package com.example.cotime_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.example.cotime_app.onboarding.OnBoardingActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "myPref";
    private static final String KEY = "open_OnBoarding_screen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View decorView =getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        |View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        );

        sharedPreferences = getSharedPreferences("",MODE_PRIVATE);

        //activity checking shared preferences data available or not
        String check = sharedPreferences.getString(KEY,null);

        Log.i(TAG, "onCreate: " + check);

        if(check != null){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    finish();
                }
            }, 3000);
        }else{
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY,"no");
            editor.apply();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this, OnBoardingActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 3000);
        }
    }
}