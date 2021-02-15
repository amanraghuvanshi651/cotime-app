package com.example.cotime_app.HelperClasses;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cotime_app.ModelClasses.Routines;
import com.example.cotime_app.R;

import java.util.ArrayList;

public class RoutineRecyclerViewAdapter extends RecyclerView.Adapter<RoutineRecyclerViewAdapter.routineViewHolder> {

    private ArrayList<Routines> routinesArrayList;
    private Context context;
    private static final String TAG = "RoutineRecyclerViewAdapter";
    private MediaPlayer mp ;

    public RoutineRecyclerViewAdapter(Context context,ArrayList<Routines> routinesArrayList) {
        this.routinesArrayList = routinesArrayList;
    }

    @NonNull
    @Override
    public routineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.routine_recyclerview_layout,parent,false);

        return new routineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull routineViewHolder holder, int position) {
        Routines routines = routinesArrayList.get(position);
        holder.title.setText(routines.getTitle());
        holder.background.setBackgroundColor(Color.parseColor(routines.getColor()));
    }

    @Override
    public int getItemCount() {
        return routinesArrayList.size();
    }

    public class routineViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout background;
        TextView title;
        CardView playPauseButton;
        ImageView play;
        ImageView pause;
        public routineViewHolder(@NonNull View itemView) {
            super(itemView);


            background = itemView.findViewById(R.id.background);
            title = itemView.findViewById(R.id.routine_title);
            playPauseButton = itemView.findViewById(R.id.playPauseButton);
            play = itemView.findViewById(R.id.playSound);
            pause = itemView.findViewById(R.id.pauseSound);



            playPauseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mp != null && mp.isPlaying()) {
                        mp.stop();
                        mp.reset();
                        mp.release();
                        mp = null;
                        pause.setVisibility(View.GONE);
                        play.setVisibility(View.VISIBLE);
                    }else{
                        mp = MediaPlayer.create(v.getContext(), R.raw.bell_sound);
                        mp.start();
                        play.setVisibility(View.GONE);
                        pause.setVisibility(View.VISIBLE);
                    }
                }
            });

        }
    }
}
