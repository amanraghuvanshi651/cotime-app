package com.example.cotime_app.HelperClasses;

import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cotime_app.R;

import java.util.ArrayList;

public class ColorPickerRecyclerViewAdapter extends RecyclerView.Adapter<ColorPickerRecyclerViewAdapter.colorViewHolder> {

    ArrayList colors;

    public ColorPickerRecyclerViewAdapter(ArrayList colors) {
        this.colors = colors;
    }

    @NonNull
    @Override
    public colorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.color_picker_item_layout,parent,false);
        return new colorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull colorViewHolder holder, int position) {
        holder.colorContainer.setBackgroundColor(Color.parseColor(colors.get(position).toString()));
    }

    @Override
    public int getItemCount() {
        return colors.size();
    }

    public class colorViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout colorContainer;

        public colorViewHolder(@NonNull View itemView) {
            super(itemView);
            colorContainer = itemView.findViewById(R.id.color_container);
        }
    }
}
