package com.example.cotime_app.onboarding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.cotime_app.R;
import com.example.cotime_app.onboarding.helperClass.OnBoardingList;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {

    ArrayList<OnBoardingList> onBoardingLists;
    Context context;

    public ViewPagerAdapter(ArrayList<OnBoardingList> onBoardingLists, Context context) {
        this.onBoardingLists = onBoardingLists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.onboardingscreen, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OnBoardingList list = onBoardingLists.get(position);

        //setting data
        holder.topImage.setImageResource(list.getImg());
        holder.Title.setText(list.getTitle());
        holder.Decs.setText(list.getDecs());

    }

    @Override
    public int getItemCount() {
        return onBoardingLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView topImage;
        TextView Title;
        TextView Decs;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            topImage = itemView.findViewById(R.id.onBoarding_image);
            Title = itemView.findViewById(R.id.onBoarding_title);
            Decs = itemView.findViewById(R.id.onBoarding_decs);
        }
    }
}
