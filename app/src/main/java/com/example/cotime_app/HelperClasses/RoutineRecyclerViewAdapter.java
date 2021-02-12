package com.example.cotime_app.HelperClasses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cotime_app.R;

public class RoutineRecyclerViewAdapter extends RecyclerView.Adapter<RoutineRecyclerViewAdapter.routineViewHolder> {
    @NonNull
    @Override
    public routineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.routine_recyclerview_layout,parent,false);
        return new routineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull routineViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class routineViewHolder extends RecyclerView.ViewHolder{
        public routineViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
