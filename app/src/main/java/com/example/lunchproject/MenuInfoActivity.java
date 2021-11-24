package com.example.lunchproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lunchproject.Adapter.MenuRatingAdapter;
import com.example.lunchproject.Retrofit2.Menu;
import com.example.lunchproject.Retrofit2.MenuRating;
import com.example.lunchproject.util.Common;

import java.util.ArrayList;
import java.util.List;

public class MenuInfoActivity extends AppCompatActivity {

    Intent intent;
    Menu menu;
    MenuRating rating;
    String mMenuKind, mMenuCode, mMenuName;
    TextView mMenuNameTxt;
    RecyclerView mRecycle_view;
    MenuRatingAdapter mAdapter;
    List<MenuRating> ratingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_info);


        mMenuNameTxt = findViewById(R.id.menuNameTxt);
        mRecycle_view = findViewById(R.id.recyclerView);

        menu = new Menu();
        intent = getIntent();

        // dummy data
        rating = new MenuRating();

        ratingList = new ArrayList<MenuRating>();

        rating = new MenuRating("1","관리자","평점 : 4.1","2021-11-22","아주 맛있네요!!");
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



        }else{
            Common.intentCommon(MenuInfoActivity.this, MainActivity.class);
            finish();
            Toast.makeText(getApplicationContext(), "잘못된 데이터입니다.", Toast.LENGTH_SHORT).show();
        }

    }
}