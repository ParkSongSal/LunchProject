package com.example.lunchproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Rating;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lunchproject.Adapter.MenuRatingAdapter;
import com.example.lunchproject.Retrofit2.LunchApi;
import com.example.lunchproject.Retrofit2.Menu;
import com.example.lunchproject.Retrofit2.MenuRating;
import com.example.lunchproject.Retrofit2.RetrofitClient;
import com.example.lunchproject.util.Common;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuInfoActivity extends AppCompatActivity {
    private LunchApi mLunchApi;

    Intent intent;
    Menu menu;
    MenuRating rating;
    String mMenuKind, mMenuCode, mMenuName;
    TextView mMenuNameTxt;
    RatingBar mRatingBar;
    RecyclerView mRecycle_view;
    MenuRatingAdapter mAdapter;
    List<MenuRating> ratingList;
    private SharedPreferences preferences;

    String loginId = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_info);


        mMenuNameTxt = findViewById(R.id.menuNameTxt);
        mRecycle_view = findViewById(R.id.recyclerView);
        mRatingBar = findViewById(R.id.ratingBar);
        FloatingActionButton fab = findViewById(R.id.fab);

        mLunchApi = new RetrofitClient().getLunchApi();
        preferences = getSharedPreferences("setting", MODE_PRIVATE);
        loginId = preferences.getString("loginId", "");

        menu = new Menu();
        intent = getIntent();

        // dummy data
        rating = new MenuRating();

        ratingList = new ArrayList<MenuRating>();

        //rating = new MenuRating("1","15","4.6","덮밥 맛잇어요!!","2021-11-24","admin","2021-11-24","admin");
        //ratingList.add(rating);
        //mAdapter = new MenuRatingAdapter(getApplicationContext(), ratingList);

        //mRecycle_view.setAdapter(mAdapter);

        if(intent != null){

            menu  = (Menu) intent.getSerializableExtra("menu");
            Log.d("TAG","menu : " + menu.toString());
            mMenuKind = menu.getMenu_kind();
            mMenuCode = menu.getMenu_code();
            mMenuName = menu.getMenu_name();
            mMenuNameTxt.setText(mMenuName);

            getMenuRating(mMenuCode);
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

    public void getMenuRating(String menuCode){
        RequestBody menuCodePart = RequestBody.create(MultipartBody.FORM, menuCode);
        Call<List<MenuRating>> call = mLunchApi.MenuRatingSelect(menuCodePart);
        call.enqueue(new Callback<List<MenuRating>>() {
            @Override
            public void onResponse(Call<List<MenuRating>> call, Response<List<MenuRating>> response) {

                List<MenuRating> result = response.body();
                int resultSize = result.size();
                float fGrade = 0;
                if (response.isSuccessful()) {
                    for (int i = 0; i < result.size(); i++) {

                        String mBId = result.get(i).getB_id();
                        String mMCode = result.get(i).getM_code();
                        String mGrade = result.get(i).getGrade();
                        String mContent = result.get(i).getContent();
                        String mUpdateDate = result.get(i).getUpdate_date();
                        String mUpdateId = result.get(i).getUpdate_id();
                        rating = new MenuRating(mBId, mMCode, mGrade,mContent,null, null, mUpdateDate, mUpdateId);
                        Log.d("TAG", "rating : " + rating.toString());
                        float formatGrade = Float.parseFloat(mGrade);
                        fGrade += formatGrade;
                        ratingList.add(rating);
                        mAdapter = new MenuRatingAdapter(getApplicationContext(), ratingList, loginId);
                        mRecycle_view.setAdapter(mAdapter);
                    }
                    mRatingBar.setRating(fGrade / resultSize);

                } else {
                    Log.d("TAG", "not successful");
                }
            }
            @Override
            public void onFailure(Call<List<MenuRating>> call, Throwable t) {
                // 네트워크 문제
                Toast.makeText(getApplicationContext(), "데이터 접속 상태를 확인 후 다시 시도해주세요.", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    // 보낸이 : MemoRecyclerAdapter
    @SuppressLint("RestrictedApi")
    @Subscribe
    public void onItemClick(MenuRatingAdapter.ItemClickEvent event) {
        //Memo memo2 = newMemoList.get(event.position);
        //Intent intent = new Intent(getApplicationContext(), BoardInsertActivity.class);
        //startActivity(intent);
        Toast.makeText(getApplicationContext(), "수정!!!!", Toast.LENGTH_SHORT).show();

    }

    // 보낸이 : MemoRecyclerAdapter
    @SuppressLint("RestrictedApi")
    @Subscribe
    public void onItemDeleteClick(MenuRatingAdapter.ItemDeleteClickEvent event) {
        //Memo memo2 = newMemoList.get(event.position);
        //Intent intent = new Intent(getApplicationContext(), BoardInsertActivity.class);
        //startActivity(intent);
        Toast.makeText(getApplicationContext(), "삭제!!!!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {

        Common.intentCommon(MenuInfoActivity.this, MainActivity.class);
        finish();

    } //뒤로가기 종료버튼
}