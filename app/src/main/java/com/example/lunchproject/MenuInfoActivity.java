package com.example.lunchproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
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
import com.example.lunchproject.Retrofit2.Result;
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
    private AlertDialog dialog;
    AlertDialog.Builder builder;
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
        builder = new AlertDialog.Builder(MenuInfoActivity.this);

        if(intent != null){

            menu  = (Menu) intent.getSerializableExtra("menu");
            Log.d("TAG","menu : " + menu.toString());
            mMenuKind = menu.getMenu_kind();
            mMenuCode = menu.getMenu_code();
            mMenuName = menu.getMenu_name();
            mMenuNameTxt.setText(mMenuName);

            getMenuRating(mMenuCode);

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

    // 메뉴 평가 수정 버튼 클릭
    // 보낸이 : MemoRecyclerAdapter
    @SuppressLint("RestrictedApi")
    @Subscribe
    public void onItemClick(MenuRatingAdapter.ItemClickEvent event) {


        intent = new Intent(getApplicationContext(), MenuRatingUpdateActivity.class);

        String bId = ratingList.get(event.position).getB_id();
        intent.putExtra("bId", bId);
        intent.putExtra("content", ratingList.get(event.position).getContent());
        intent.putExtra("grade", ratingList.get(event.position).getGrade());
        startActivity(intent);
        finish();
    }

    // 메뉴 평가 삭제 버튼 클릭
    // 보낸이 : MemoRecyclerAdapter
    @SuppressLint("RestrictedApi")
    @Subscribe
    public void onItemDeleteClick(MenuRatingAdapter.ItemDeleteClickEvent event) {
        String bId = ratingList.get(event.position).getB_id();

        dialog = builder.setTitle("삭제알림")
                .setMessage("해당 평가를 삭제하시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        menuRatingDelete(bId);
                    }
                })
                .setNegativeButton("아니오", null)
                .create();

        dialog.show();
    }

    // 메뉴 평가 삭제 처리
    public void menuRatingDelete(String bId){
        Call<Result> call = mLunchApi.MenuRatingDelete(bId);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                if(response.isSuccessful()) {
                    //정상 결과
                    if ("success".equals(response.body().getResult())) {
                        Toast.makeText(MenuInfoActivity.this, "평가를 삭제했습니다.", Toast.LENGTH_LONG).show();
                        Common.intentCommon(MenuInfoActivity.this, MainActivity.class);
                        finish();
                    } else {
                        Toast.makeText(MenuInfoActivity.this, "평가 삭제를 실패했습니다.", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Log.d("TAG","not successful");
                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                // 네트워크 문제
                Toast.makeText(MenuInfoActivity.this, "데이터 접속 상태를 확인 후 다시 시도해주세요.", Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onBackPressed() {

        Common.intentCommon(MenuInfoActivity.this, MainActivity.class);
        finish();

    } //뒤로가기 종료버튼
}