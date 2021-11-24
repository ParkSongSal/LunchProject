package com.example.lunchproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lunchproject.Retrofit2.LunchApi;
import com.example.lunchproject.Retrofit2.Result;
import com.example.lunchproject.Retrofit2.RetrofitClient;
import com.example.lunchproject.util.Common;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private AlertDialog dialog;

    Intent intent;
    private LunchApi mLunchApi;
    private EditText mUserId, mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLunchApi = new RetrofitClient().getLunchApi();
        mUserId = findViewById(R.id.idEditText);
        mPassword = findViewById(R.id.pwEditText);
    }

    public void onClick(View view) {
        switch (view.getId()){

            // 로그인 버튼
            case R.id.loginBtn:
                loginAct(); // 서버에 로그인 요청
                break;

            // 회원가입 버튼
            case R.id.registerBtn:
                intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
    
    public void loginAct(){
        String userId = mUserId.getText().toString();
        String userPw = mPassword.getText().toString();
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

        // id 빈칸 확인
        if (userId.equals("")) {
            dialog = builder.setMessage("아이디는 빈 칸일 수 없습니다.")
                    .setPositiveButton("확인", null)
                    .create();
            dialog.show();
            return;
        }
        // pw 빈칸 확인
        if (userPw.equals("")) {
            dialog = builder.setMessage("패스워드는 빈 칸일 수 없습니다.")
                    .setPositiveButton("확인", null)
                    .create();
            dialog.show();
            return;
        }
        
        Call<Result> call = mLunchApi.UserLogin(userId, userPw);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                if(response.isSuccessful()){
                    Result result = response.body();
                    if("success".equals(result.getResult())){
                        Common.intentCommon(LoginActivity.this, MainActivity.class);
                        finish();
                        Toast.makeText(getApplicationContext(), "로그인 성공!", Toast.LENGTH_SHORT).show();
                    }else{
                        // ID 또는 패스워드가 틀린 경우
                        dialog = builder.setMessage("계정을 다시 확인바랍니다.")
                                .setNegativeButton("확인", null)
                                .create();
                        dialog.show();
                    }
                }else{
                    Log.d("TAG","not successful");
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                // 네트워크 문제
                Toast.makeText(LoginActivity.this, "데이터 접속 상태를 확인 후 다시 시도해주세요.", Toast.LENGTH_LONG).show();
            }
        });
    }
}