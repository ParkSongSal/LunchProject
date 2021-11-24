package com.example.lunchproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lunchproject.R;
import com.example.lunchproject.Retrofit2.MenuRating;

import java.util.List;

public class MenuRatingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<MenuRating> mData;
    private Context context;
    public MenuRatingAdapter(Context context, List<MenuRating> menuRatingList) {
        this.context = context;
        this.mData = menuRatingList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_rating,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MenuRating rating = mData.get(position);

        MyViewHolder myViewHolder = (MyViewHolder) holder;

        myViewHolder.mWriter.setText(rating.getWriter());

        myViewHolder.mGrade.setText(rating.getGrade());

        myViewHolder.mDate.setText(rating.getDate());

        myViewHolder.mReply.setText(rating.getReply());


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mWriter, mGrade, mDate, mReply;

        public MyViewHolder(View itemView) {
            super(itemView);
            mWriter = itemView.findViewById(R.id.writerTxt);
            mGrade = itemView.findViewById(R.id.gradeTxt);
            mDate = itemView.findViewById(R.id.dateTxt);
            mReply = itemView.findViewById(R.id.replyTxt);

        }
    }
}