package com.example.lunchproject.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lunchproject.R;
import com.example.lunchproject.Retrofit2.MenuRating;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class MenuRatingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<MenuRating> mData;
    private Context context;
    private String mLoginId;
    public MenuRatingAdapter(Context context, List<MenuRating> menuRatingList, String loginId) {
        this.context = context;
        this.mData = menuRatingList;
        this.mLoginId = loginId;
    }

    //Event Bus 클래스
    public static class ItemClickEvent {
        public ItemClickEvent(int position) {
            this.position = position;
            //this.id = id;
        }

        public int position;
        //public long id;

    }
    //Event Bus 클래스
    public static class ItemDeleteClickEvent {
        public ItemDeleteClickEvent(int position) {
            this.position = position;
            //this.id = id;
        }

        public int position;
        //public long id;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_rating,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,final int position) {

        MenuRating rating = mData.get(position);

        MyViewHolder myViewHolder = (MyViewHolder) holder;

        myViewHolder.mWriter.setText(rating.getUpdate_id());


        //myViewHolder.mGrade.setText("평점 : " + rating.getGrade());

        float grade = Float.parseFloat(rating.getGrade());

        myViewHolder.mRatingBar.setRating(grade);

        myViewHolder.mDate.setText(rating.getUpdate_date());

        myViewHolder.mReply.setText(rating.getContent());

        myViewHolder.mReply.setSelected(true);

        if(mLoginId.equals(rating.getUpdate_id())){
            myViewHolder.mUpdate.setVisibility(View.VISIBLE);
            myViewHolder.mDelete.setVisibility(View.VISIBLE);
        }else{
            myViewHolder.mUpdate.setVisibility(View.GONE);
            myViewHolder.mDelete.setVisibility(View.GONE);
        }

        // 수정버튼 클릭
        myViewHolder.mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // MainActivity에 onItemClick이 받음
                EventBus.getDefault().post(new ItemClickEvent(position));
            }
        });

        // 수정버튼 클릭
        myViewHolder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // MainActivity에 onItemClick이 받음
                EventBus.getDefault().post(new ItemDeleteClickEvent(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mWriter, mGrade, mDate, mReply, mUpdate, mDelete;
        RatingBar mRatingBar;
        public MyViewHolder(View itemView) {
            super(itemView);
            mWriter = itemView.findViewById(R.id.writerTxt);
            //mGrade = itemView.findViewById(R.id.gradeTxt);
            mRatingBar = itemView.findViewById(R.id.ratingBar);
            mDate = itemView.findViewById(R.id.dateTxt);
            mReply = itemView.findViewById(R.id.replyTxt);

            mUpdate = itemView.findViewById(R.id.updateTxt);
            mDelete = itemView.findViewById(R.id.deleteTxt);
        }
    }
}
