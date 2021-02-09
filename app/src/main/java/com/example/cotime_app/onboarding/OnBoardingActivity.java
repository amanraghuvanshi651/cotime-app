package com.example.cotime_app.onboarding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cotime_app.HomeActivity;
import com.example.cotime_app.R;
import com.example.cotime_app.onboarding.ViewPagerAdapter;
import com.example.cotime_app.onboarding.helperClass.OnBoardingList;

import java.util.ArrayList;

public class OnBoardingActivity extends AppCompatActivity {

    private ArrayList<OnBoardingList> onBoardingList;

    //Views
    RelativeLayout button;
    ViewPager2 viewPager2;
    ViewPagerAdapter adapter;
    TextView buttonTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        View decorView =getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        );

        onBoardingList = new ArrayList<>();

        //hooks
        button = findViewById(R.id.onBoarding_Btn);
        viewPager2 = findViewById(R.id.onBoardingViewPager);
        buttonTxt = findViewById(R.id.button_txt);

        //creating on boarding list
        createOnBoardingList();

        //set adapter
        setAdapter();

        //on click Handler
        onClick();

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (position == 2){
                    buttonTxt.setText("Start");
                }else{
                    buttonTxt.setText("Next");
                }
            }
        });
    }

    private void onClick() {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager2.getCurrentItem()+1<adapter.getItemCount()){
                    viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
                }else {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                }
            }
        });

    }

    private void setAdapter() {
        //initializing adapter
        adapter = new ViewPagerAdapter(onBoardingList, this);

        //setting adapter
        viewPager2.setAdapter(adapter);
    }

    private void createOnBoardingList() {
        onBoardingList.add(new OnBoardingList("Custom breakpoints", "Set your own custom breakpoints.\n" +
                "with custom time interval.", R.drawable.custom_breakpoints_ai));
        onBoardingList.add(new OnBoardingList("Voice Alert", "Set custom voice alert as per your need.", R.drawable.voice_alert_ai));
        onBoardingList.add(new OnBoardingList("Free Forever", "This app is free to use. But if you like \n" +
                "the and want to donate you can help us\n" +
                "here.", R.drawable.free_forever_ai));
    }
}