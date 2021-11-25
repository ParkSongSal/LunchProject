package com.example.lunchproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lunchproject.Adapter.MenuRatingAdapter;
import com.example.lunchproject.Retrofit2.Menu;
import com.example.lunchproject.Retrofit2.MenuRating;
import com.example.lunchproject.util.Common;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MenuInfoActivity extends AppCompatActivity {

    Intent intent;
    Menu menu;
    MenuRating rating;
    String mMenuKind, mMenuCode, mMenuName;
    TextView mMenuNameTxt;
    RatingBar mRatingBar;
    RecyclerView mRecycle_view;
    MenuRatingAdapter mAdapter;
    List<MenuRating> ratingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_info);


        mMenuNameTxt = findViewById(R.id.menuNameTxt);
        mRecycle_view = findViewById(R.id.recyclerView);
        mRatingBar = findViewById(R.id.ratingBar);
        FloatingActionButton fab = findViewById(R.id.fab);

        menu = new Menu();
        intent = getIntent();

        // dummy data
        rating = new MenuRating();

        ratingList = new ArrayList<MenuRating>();

        rating = new MenuRating("1","15","4.6","덮밥 맛잇어요!!","2021-11-24","admin","2021-11-24","admin");
        ratingList.add(rating);
        mAdapter = new MenuRatingAdapter(getApplicationContext(), ratingList);

        mRecycle_view.setAdapter(mAdapter);

        if(intent != null){

            menu  = (Menu) intent.getSerializableExtra("menu");
            Log.d("TAG","menu : " + menu.toString());
            mMenuKind = menu.getMenu_kind();
            mMenuCode = menu.getMenu_code();
            mMenuName = menu.getMenu_name();
            mMenuNameTxt.setText(mMenuName);

            // 평균 평점
            float rating = 3.5F;
            mRatingBar.setRating(rating);

            /*mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    //mRateText.setText("Now Rating : " + rating);

                }
            });*/
        }else{
            Common.intentCommon(MenuInfoActivity.this, MainActivity.class);
            finish();
            Toast.makeText(getApplicationContext(), "잘못된 데이터입니다.", Toast.LENGTH_SHORT).show();
        }

        // 리뷰 등록 버튼 클릭
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), MenuRatingWriteActivity.class);
                intent.putExtra("menuCode", menu.getMenu_code());
                startActivity(intent);
                finish();
            }
        });
    }
}