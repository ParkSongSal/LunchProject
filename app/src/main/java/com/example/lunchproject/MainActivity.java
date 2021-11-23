package com.example.lunchproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lunchproject.Retrofit2.LunchApi;
import com.example.lunchproject.Retrofit2.Menu;
import com.example.lunchproject.Retrofit2.Result;
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
    private AlertDialog dialog;

    private LunchApi mLunchApi;

    TextView mMenuName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMenuName = findViewById(R.id.menuNameTxt);

        mLunchApi = new RetrofitClient().getLunchApi();
    }



    public void onClick(View view) {
        switch (view.getId()){
            case R.id.menuNameTxt:
                Toast.makeText(getApplicationContext(), "메뉴추천!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuRecommendBtn:
                // 밥먹으러 Go! -> 메뉴 추천
                menuRecommend();
                //Toast.makeText(getApplicationContext(), "밥먹으러 Go!", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    public void menuRecommend(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        Random random = new Random();
        String menuCode = ""; // 01~79
        int limit = 80;
        int randomCode =0;
        randomCode = random.nextInt(limit);
        Log.d("TAG", "randomCode : " + randomCode);
        menuCode = String.valueOf(randomCode);
        Log.d("TAG", "menuCode : " + menuCode);

        RequestBody menuCodePart = RequestBody.create(MultipartBody.FORM, menuCode);
        Call<List<Menu>> call = mLunchApi.Menu_Recommend(menuCodePart);
        call.enqueue(new Callback<List<Menu>>() {
            @Override
            public void onResponse(Call<List<Menu>> call, Response<List<Menu>> response) {

                List<Menu> result = response.body();

                if(response.isSuccessful()){
                    for(int i=0; i<result.size();i++){
                        String seq = result.get(i).getSeq();
                        String menuName = result.get(i).getMenu_name();
                        String menuKind = result.get(i).getMenu_kind();
                        String menuCode = result.get(i).getMenu_code();
                        Menu menu = new Menu(seq, menuKind, menuName, menuCode);
                        mMenuName.setText(menu.getMenu_name());
                    }

                }else{
                    Log.d("TAG","not successful");
                }
            }

            @Override
            public void onFailure(Call<List<Menu>> call, Throwable t) {
                // 네트워크 문제
                Toast.makeText(MainActivity.this, "데이터 접속 상태를 확인 후 다시 시도해주세요.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void menuKind(View view) {
    }
}