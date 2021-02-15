package com.example.cotime_app.HelperClasses;

import android.graphics.Color;
import android.os.Handler;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cotime_app.R;

import java.util.ArrayList;

public class ColorPickerRecyclerViewAdapter extends RecyclerView.Adapter<ColorPickerRecyclerViewAdapter.colorViewHolder> {

    //color array list
    ArrayList colors;

    private int selectedItemPos = 0;
    private int lastItemSelectedPos = 0;

    private static final String TAG = "ColorPickerRecyclerViewAdapter";

    onColorItemClickListener onColorItemClickListener;

    public ColorPickerRecyclerViewAdapter(ArrayList colors, onColorItemClickListener onColorItemClickListener) {
        this.colors = colors;
        this.onColorItemClickListener = onColorItemClickListener;
    }

    @NonNull
    @Override
    public colorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.color_picker_item_layout,parent,false);
        return new colorViewHolder(view, onColorItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull colorViewHolder holder, int position) {
        holder.colorContainer.setBackgroundColor(Color.parseColor(colors.get(position).toString()));

        if(position == selectedItemPos){
            holder.check.setVisibility(View.VISIBLE);
        }else{
            holder.check.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return colors.size();
    }

    public class colorViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout colorContainer;
        onColorItemClickListener onColorItemClickListener;
        ImageView check;

        public colorViewHolder(@NonNull View itemView, onColorItemClickListener onColorItemClickListener) {
            super(itemView);
            this.onColorItemClickListener = onColorItemClickListener;
            colorContainer = itemView.findViewById(R.id.color_container);
            check = itemView.findViewById(R.id.check);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    selectedItemPos =getAdapterPosition();

                    if (lastItemSelectedPos == -1){
                        lastItemSelectedPos = selectedItemPos;
                    }else {
                        Log.i(TAG, "lastItemSelectedPos before: " + lastItemSelectedPos);
                        //remove the check icon
                        notifyItemChanged(lastItemSelectedPos);
                        lastItemSelectedPos = selectedItemPos;
                        Log.i(TAG, "lastItemSelectedPos after: " + lastItemSelectedPos);
                    }
                    //add check icon
                    notifyItemChanged(selectedItemPos);

                    Log.i(TAG, "onClick: " + getAdapterPosition());
                    Log.i(TAG, "onClick: " + colors.get(getAdapterPosition()));

                    onColorItemClickListener.onColorItemClicked(getAdapterPosition());
                }
            });
        }
    }

    public interface onColorItemClickListener{
        void onColorItemClicked(int position);
    }
}
