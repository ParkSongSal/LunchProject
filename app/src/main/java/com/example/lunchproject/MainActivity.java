package com.example.lunchproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lunchproject.Retrofit2.LunchApi;
import com.example.lunchproject.Retrofit2.Menu;
import com.example.lunchproject.Retrofit2.RetrofitClient;
import com.example.lunchproject.util.Common;

import java.util.List;
import java.util.Random;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private LunchApi mLunchApi;
    private AlertDialog dialog;

    TextView mMenuName;


    Common common;
    Menu menu;
    Intent intent;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMenuName = findViewById(R.id.menuNameTxt);

        mLunchApi = new RetrofitClient().getLunchApi();
        common = new Common();

        menu = new Menu();

        builder = new AlertDialog.Builder(MainActivity.this);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menuNameTxt:
                intent = new Intent(this, MenuInfoActivity.class);
                if(menu.getMenu_code() != null ){
                    intent.putExtra("menu", menu);
                    startActivity(intent);
                    finish();
                    Toast.makeText(getApplicationContext(), "메뉴추천! " + menu.getMenu_code(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "메뉴추천을 눌러주세요! ", Toast.LENGTH_SHORT).show();
                }


                break;
            case R.id.menuRecommendBtn:
                // 밥먹으러 Go! -> 메뉴 추천
                menuRecommend();
                break;
            default:
                break;
        }
    }


    public void menuKind(View view) {
        String menuCode = "";
        switch (view.getId()) {

            // 한식
            case R.id.menuKind_I01:
            case R.id.menuKind_T01:
                menuCode = common.getRandomMenuCode(1, 10);
                menuKindRecommend(menuCode);
                break;

            // 일식
            case R.id.menuKind_I02:
            case R.id.menuKind_T02:
                menuCode = common.getRandomMenuCode(11, 20);
                menuKindRecommend(menuCode);
                break;
            // 중식
            case R.id.menuKind_I03:
            case R.id.menuKind_T03:
                menuCode = common.getRandomMenuCode(21, 30);
                menuKindRecommend(menuCode);
                break;

            //양식
            case R.id.menuKind_I04:
            case R.id.menuKind_T04:
                menuCode = common.getRandomMenuCode(31, 40);
                menuKindRecommend(menuCode);
                break;

            //탕.찌개
            case R.id.menuKind_I05:
            case R.id.menuKind_T05:
                menuCode = common.getRandomMenuCode(41, 50);
                menuKindRecommend(menuCode);
                break;

            // 해장
            case R.id.menuKind_I06:
            case R.id.menuKind_T06:
                menuCode = common.getRandomMenuCode(51, 60);
                menuKindRecommend(menuCode);
                break;

            // 간편식
            case R.id.menuKind_I07:
            case R.id.menuKind_T07:
                menuCode = common.getRandomMenuCode(61, 70);
                menuKindRecommend(menuCode);
                break;

            // 기타
            case R.id.menuKind_I08:
            case R.id.menuKind_T08:
                menuCode = common.getRandomMenuCode(71, 80);
                menuKindRecommend(menuCode);
                break;
            default:
                break;
        }
    }

    /*
     메뉴 카테고리별 메뉴 추천
     ex) 한식 -> 한식 메뉴 중 추천
         중식 -> 중식 메뉴 중 추천
    */
    public void menuKindRecommend(String menuCode){
        // 서버에 메뉴 추천 요청
        call_Menu_Recommend(menuCode);
    }

    public void call_Menu_Recommend(String menuCode){
        RequestBody menuCodePart = RequestBody.create(MultipartBody.FORM, menuCode);
        Call<List<Menu>> call = mLunchApi.Menu_Recommend(menuCodePart);
        call.enqueue(new Callback<List<Menu>>() {
            @Override
            public void onResponse(Call<List<Menu>> call, Response<List<Menu>> response) {

                List<Menu> result = response.body();

                if (response.isSuccessful()) {
                    for (int i = 0; i < result.size(); i++) {
                        String seq = result.get(i).getSeq();
                        String menuName = result.get(i).getMenu_name();
                        String menuKind = result.get(i).getMenu_kind();
                        String menuCode = result.get(i).getMenu_code();
                        menu = new Menu(seq, menuKind, menuName, menuCode);
                        mMenuName.setText(menu.getMenu_name());
                    }

                } else {
                    Log.d("TAG", "not successful");
                }
            }
            @Override
            public void onFailure(Call<List<Menu>> call, Throwable t) {
                // 네트워크 문제
                Toast.makeText(MainActivity.this, "데이터 접속 상태를 확인 후 다시 시도해주세요.", Toast.LENGTH_LONG).show();
            }
        });
    }
    // 전체 메뉴중 메뉴 추천
    public void menuRecommend() {

        Random random = new Random();
        String menuCode = ""; // 01~79
        int limit = 80;
        int randomCode = 0;
        randomCode = random.nextInt(limit); // 1~ 79 사이 menu_code 랜덤 뽑기
        menuCode = String.valueOf(randomCode);

        // 서버에 전체 메뉴 추천 요청
        call_Menu_Recommend(menuCode);
    }

    @Override
    public void onBackPressed() {

        CustomDialog dialog = new CustomDialog(this);
        dialog.show();
        // Alert을 이용해 종료시키기


    } //뒤로가기 종료버튼

}