package com.example.cotime_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.example.cotime_app.HelperClasses.ColorPickerRecyclerViewAdapter;
import com.example.cotime_app.HelperClasses.RoutineRecyclerViewAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private ArrayList colors;

    RecyclerView routineRecyclerView;
    ImageView addBtn;

    //add routine bottom layout
    ConstraintLayout addRoutineLayout;
    BottomSheetBehavior bottomSheetBehavior;
    ImageView crossBtn;
    RecyclerView colorPicker;

    //adapter
    private RoutineRecyclerViewAdapter routineRecyclerViewAdapter;

    //color picker adapter
    ColorPickerRecyclerViewAdapter colorPickerRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //display matrix
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //initializing array
        colors = new ArrayList();

        //hooks
        routineRecyclerView = findViewById(R.id.routineRecyclerView);
        addBtn = findViewById(R.id.add_btn);

        //add routine layout hooks
        addRoutineLayout = findViewById(R.id.add_routine_layout);
        bottomSheetBehavior =BottomSheetBehavior.from(addRoutineLayout);
        crossBtn = findViewById(R.id.cross_btn);
        colorPicker = findViewById(R.id.colorPickerRecyclerView);


        bottomSheetBehavior.setPeekHeight(height);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);


        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED || newState == BottomSheetBehavior.STATE_HIDDEN){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
                else{
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        //creating array
        createArray();

        //set routine recycler view
        setRoutineRecyclerView();

        //set color picker Recycler View
        setColorRecyclerView();

        onClick();
    }

    private void createArray() {
        colors.add("#FFE227");
        colors.add("#EB596E");
        colors.add("#845EC2");
        colors.add("#FF884B");
        colors.add("#91091E");
        colors.add("#FFC75F");
        colors.add("#EB5E0B");
        colors.add("#51C2D5");
        colors.add("#1A508B");
        colors.add("#00AF91");
    }

    private void setColorRecyclerView() {

        colorPickerRecyclerViewAdapter = new ColorPickerRecyclerViewAdapter(colors);
        colorPicker.setLayoutManager(new GridLayoutManager(this, 5));
        colorPicker.setAdapter(colorPickerRecyclerViewAdapter);

    }

    private void setRoutineRecyclerView() {
        routineRecyclerViewAdapter = new RoutineRecyclerViewAdapter();
        routineRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        routineRecyclerView.setAdapter(routineRecyclerViewAdapter);
    }

    private void onClick(){
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        crossBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });
    }

}