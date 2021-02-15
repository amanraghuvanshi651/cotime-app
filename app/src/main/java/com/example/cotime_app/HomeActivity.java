package com.example.cotime_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cotime_app.HelperClasses.ColorPickerRecyclerViewAdapter;
import com.example.cotime_app.HelperClasses.RoutineRecyclerViewAdapter;
import com.example.cotime_app.ModelClasses.Routines;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements ColorPickerRecyclerViewAdapter.onColorItemClickListener {

    private static final String FILE_NAME = "routineData";
    //private color array
    private ArrayList colors;
    private ArrayList<Routines> routinesArrayList;

    //TAG String
    private static final String TAG = "HomeActivity";

    //private selected countdown
    private String selectedCountDown = "3s";


    RecyclerView routineRecyclerView;
    ImageView addBtn;

    //add routine bottom layout
    ConstraintLayout addRoutineLayout;
    BottomSheetBehavior bottomSheetBehavior;
    ImageView crossBtn;
    EditText title;
    RecyclerView colorPicker;
    RelativeLayout createButton;
    //count down
    RelativeLayout threeSec;
    RelativeLayout fiveSec;
    RelativeLayout tenSec;
    TextView threeTxt;
    TextView fiveTxt;
    TextView tenTxt;
    private String selectedColor;

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
        routinesArrayList = new ArrayList<>();


        //hooks
        routineRecyclerView = findViewById(R.id.routineRecyclerView);
        addBtn = findViewById(R.id.add_btn);

        //add routine layout hooks
        addRoutineLayout = findViewById(R.id.add_routine_layout);
        bottomSheetBehavior =BottomSheetBehavior.from(addRoutineLayout);
        crossBtn = findViewById(R.id.cross_btn);
        colorPicker = findViewById(R.id.colorPickerRecyclerView);
        title = findViewById(R.id.title_et);
        createButton = findViewById(R.id.create_btn);
        //countdown
        threeSec = findViewById(R.id.three_sec_time);
        fiveSec = findViewById(R.id.five_sec_time);
        tenSec = findViewById(R.id.ten_sec_time);
        threeTxt = findViewById(R.id.three_sec_time_text);
        fiveTxt = findViewById(R.id.five_sec_time_text);
        tenTxt = findViewById(R.id.ten_sec_time_text);


        //setting bottom sheet behaviour
        bottomSheetBehavior.setPeekHeight(height);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);


        //on state change of the bottom add routine layout
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                if (newState == BottomSheetBehavior.STATE_EXPANDED || newState == BottomSheetBehavior.STATE_HALF_EXPANDED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }else if (newState == BottomSheetBehavior.STATE_COLLAPSED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        //creating array
        createArray();

        //on create
        onClick();

        //setting routine recycler view adapter
        setRoutineRecyclerView();

        //read the file
        File file = new File(getFilesDir(),FILE_NAME);
        if (file.exists()){
            readFile();
        }else {
            Toast.makeText(this,"File do not exist !!", Toast.LENGTH_SHORT).show();
        }
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
        colorPickerRecyclerViewAdapter = new ColorPickerRecyclerViewAdapter(colors, this);
        colorPicker.setLayoutManager(new GridLayoutManager(this, 5));
        colorPicker.setAdapter(colorPickerRecyclerViewAdapter);
    }

    private void setRoutineRecyclerView() {
        routineRecyclerViewAdapter = new RoutineRecyclerViewAdapter(this,routinesArrayList);
        routineRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        routineRecyclerView.setAdapter(routineRecyclerViewAdapter);
    }

    private void onClick(){
        addRoutineLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm.isAcceptingText()) { // verify if the soft keyboard is open
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
            }
        });

        //on click add button
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedColor = colors.get(0).toString();
                Log.i(TAG, "onClick: " + selectedColor);
                Log.i(TAG, "onClick: Add Clicked");
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                //set color picker Recycler View
                setColorRecyclerView();
            }
        });

        //on click cross button
        crossBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedColor = colors.get(0).toString();
                Log.i(TAG, "onClick: Cross Clicked");
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                //close the keyboard
                closeKeyBoard();

            }
        });

        //countdown selector
        //three sec time
        threeSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setting count down
                selectedCountDown = "3s";

                Log.i(TAG, "selected count down: " + selectedCountDown);

                //add background to selected time and change the text color to white
                threeSec.setBackgroundResource(R.drawable.add_routine_select_time_item_background);
                threeTxt.setTextColor(Color.parseColor("#ffffff"));

                //remove background and set text color to black
                fiveSec.setBackgroundResource(0);
                fiveTxt.setTextColor(Color.parseColor("#222222"));
                tenSec.setBackgroundResource(0);
                tenTxt.setTextColor(Color.parseColor("#222222"));

                //close the keyboard
                closeKeyBoard();
            }
        });
        //five sec time
        fiveSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setting count down
                selectedCountDown = "5s";

                Log.i(TAG, "selected count down: " + selectedCountDown);

                //add background to selected time and change the text color to white
                fiveSec.setBackgroundResource(R.drawable.add_routine_select_time_item_background);
                fiveTxt.setTextColor(Color.parseColor("#ffffff"));

                //remove background from not selected time's and set text color to black
                threeSec.setBackgroundResource(0);
                threeTxt.setTextColor(Color.parseColor("#222222"));
                tenSec.setBackgroundResource(0);
                tenTxt.setTextColor(Color.parseColor("#222222"));

                //close the keyboard
                closeKeyBoard();
            }
        });
        //ten sec time
        tenSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setting count down
                selectedCountDown = "10s";

                Log.i(TAG, "selected count down: " + selectedCountDown);

                //add background to selected time and change the text color to white
                tenSec.setBackgroundResource(R.drawable.add_routine_select_time_item_background);
                tenTxt.setTextColor(Color.parseColor("#ffffff"));

                //remove background from not selected time's and set text color to black
                threeSec.setBackgroundResource(0);
                threeTxt.setTextColor(Color.parseColor("#222222"));
                fiveSec.setBackgroundResource(0);
                fiveTxt.setTextColor(Color.parseColor("#222222"));

                //close the keyboard
                closeKeyBoard();
            }
        });

        //onclick on create routine button
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i(TAG, "onClick: working");
                String routineTitle = title.getText().toString();

                if (routineTitle.matches("")){
                    Toast.makeText(HomeActivity.this, "Enter the title",Toast.LENGTH_SHORT).show();
                }else{
                    Routines routine = new Routines(routineTitle,"Desc",selectedCountDown,"23/04/21", selectedColor);
                    routinesArrayList.add(routine);
                    writeFile(routine);

                    //close the keyboard
                    closeKeyBoard();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                        }
                    }, 100);
                }
            }
        });
    }

    private void writeFile(Routines routines){
        try {

            //creating routine json array
            JSONArray routineArray = new JSONArray();

            if (routinesArrayList.isEmpty()){

                //creating routine json object
                JSONObject routine = new JSONObject();

                //putting data into the routine object
                routine.put("title", routines.getTitle());
                routine.put("description", routines.getDescription());
                routine.put("count_down", routines.getCountDown());
                routine.put("created_at", routines.getCreatedAt());
                routine.put("background_color", routines.getColor());

                routineArray.put(routine);
            }else{
                for (int i = 0; i < routinesArrayList.size(); i++){

                    //creating routine json object
                    JSONObject routine = new JSONObject();

                    Log.i(TAG, "writeFile: else working");
                    Log.i(TAG, "writeFile into the android: " + i);
                    //object of routines Class
                    Routines routineData = routinesArrayList.get(i);

                    //putting data into the routine object
                    routine.put("title", routineData.getTitle());
                    routine.put("description", routineData.getDescription());
                    routine.put("count_down", routineData.getCountDown());
                    routine.put("created_at", routineData.getCreatedAt());
                    routine.put("background_color", routineData.getColor());

                    routineArray.put(routine);
                }
            }

            //creating final routine json object
            JSONObject finalRoutineJSON = new JSONObject();
            finalRoutineJSON.put("routineArray", routineArray);


            //converting the final routine json object into string
            String jsonString = finalRoutineJSON.toString();
            Log.i(TAG, "JSON File : " + jsonString);

            // writing the data to file (jsonString)
            File file = new File(getFilesDir(),FILE_NAME);
            FileWriter fileWriter = null;

            fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(jsonString);
            bufferedWriter.close();

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            readFile();
        }
    }

    public void readFile(){
        Log.i(TAG, "readFile: working");
        routinesArrayList.clear();
        File file = new File(getFilesDir(),FILE_NAME);
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();

            while(line != null){
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();

            //storing the string into response its in json string format
            String response = stringBuilder.toString();

            //retrieving the json data from json string (response)
            JSONObject routineData = new JSONObject(response);

            JSONArray routineArray = routineData.getJSONArray("routineArray");

            for (int i = 0; i < routineArray.length(); i++){
                JSONObject routine = routineArray.getJSONObject(i);
                routinesArrayList.add(new Routines(routine.get("title").toString(), routine.getString("description"), routine.getString("count_down"), routine.getString("created_at"),routine.getString("background_color")));
            }

            for (int i = 0; i < routinesArrayList.size(); i++){
                Routines routines = routinesArrayList.get(i);
                Log.i(TAG, "title: " + routines.getTitle());
                Log.i(TAG, "description: " + routines.getDescription());
                Log.i(TAG, "count_down: " + routines.getCountDown());
                Log.i(TAG, "created_at: " + routines.getCreatedAt());
                Log.i(TAG, "background_color: " + routines.getColor());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            Log.i(TAG, "readFile: " + (routinesArrayList.size() - 1));
            //set routine recycler view
            routineRecyclerViewAdapter.notifyItemInserted(routinesArrayList.size() - 1);
        }

    }

    public void closeKeyBoard(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public void onColorItemClicked(int position) {

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        selectedColor = colors.get(position).toString();
        Log.i(TAG, "onColorItemClicked: " + selectedColor);
    }
}