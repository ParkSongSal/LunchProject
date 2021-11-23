package com.example.lunchproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lunchproject.Retrofit2.LunchApi;
import com.example.lunchproject.Retrofit2.Result;
import com.example.lunchproject.Retrofit2.ResultModel;
import com.example.lunchproject.Retrofit2.RetrofitClient;
import com.example.lunchproject.util.Common;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private AlertDialog dialog;

    private EditText mUserId, mPassword, mPassword2;
    private Button mValidateBtn, mRegisterBtn;

    private boolean validate = false;

    private LunchApi mLunchApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mUserId = findViewById(R.id.userIdEdit);
        mPassword = findViewById(R.id.passwordEdit);
        mPassword2 = findViewById(R.id.password2Edit);

        mValidateBtn = findViewById(R.id.validateBtn);
        mRegisterBtn = findViewById(R.id.registerActBtn);

        mLunchApi = new RetrofitClient().getLunchApi();

        // 패스워드 입력 일치 확인
        mPassword2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    //Toast.makeText(getApplicationContext(), "Focus Lose", Toast.LENGTH_SHORT).show();
                    String pass1 = mPassword.getText().toString();
                    String pass2 = mPassword2.getText().toString();
                    if (pass1.equals(pass2)) {
                        Toast.makeText(getApplicationContext(), "패스워드가 일치합니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "패스워드가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });




    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.registerActBtn:
                userRegister();
                break;
            case R.id.validateBtn:
                userIdValidate();
                break;
            default :
                break;
        }

    }

    public void userRegister(){
        final String userId = mUserId.getText().toString();
        final String userPw = mPassword.getText().toString();

        final String rgsDate = Common.nowDate("yyyy-MM-dd HH:mm:ss");

        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);

        if (validate) {
            dialog = builder.setMessage("먼저 중복 체크를 해주세요.")
                    .setNegativeButton("확인", null)
                    .create();
            dialog.show();
            return;
        }

        if (userId.equals("") || userPw.equals("")) {
            dialog = builder.setMessage("빈 칸 없이 입력해주세요.")
                    .setNegativeButton("확인", null)
                    .create();
            dialog.show();
            return;
        }

        Call<ResultModel> call = mLunchApi.User_Register(userId, userPw, rgsDate);
        call.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, retrofit2.Response<ResultModel> response) {
                //정상 결과
                if ("success".equals(response.body().getResult())) {
                    dialog = builder.setMessage("회원등록에 성공했습니다.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Common.intentCommon(RegisterActivity.this, LoginActivity.class);
                                    finish();
                                }
                            }).create();
                } else {
                    dialog = builder.setMessage("회원 등록에 실패했습니다.")
                            .setNegativeButton("확인", null)
                            .create();
                }
                dialog.show();
            }

            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {
                // 네트워크 문제
                Toast.makeText(RegisterActivity.this, "데이터 접속 상태를 확인 후 다시 시도해주세요.", Toast.LENGTH_LONG).show();
                Log.d("TAG", "response : " + t.toString());
                Log.d("TAG", "response : " + t.getMessage());
            }
        });
    }

    // userId 중복 체크
    public void userIdValidate(){
        String userId = mUserId.getText().toString();

        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);

        if (!validate) {
            return;
        }
        if (userId.equals("")) {
            dialog = builder.setMessage("닉네임은 빈 칸일 수 없습니다.")
                    .setPositiveButton("확인", null)
                    .create();
            dialog.show();
            return;
        }
        Call<Result> call = mLunchApi.UserId_Validate(userId);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.d("TAG", "response : " + response.toString());
                Log.d("TAG", "response b: " + response.body().toString());
                Result result = response.body();
                if(result.getResult().equals("success")){
                    dialog = builder.setMessage("사용할 수 있는 닉네임입니다.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    mUserId.setEnabled(false);
                    validate = true;
                    mValidateBtn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }else{
                    //중복인 닉네임 존재
                    dialog = builder.setMessage("사용할 수 없는 닉네임입니다.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                // 네트워크 문제
                Toast.makeText(RegisterActivity.this, "데이터 접속 상태를 확인 후 다시 시도해주세요.", Toast.LENGTH_LONG).show();
                Log.d("TAG", "response : " + t.toString());
                Log.d("TAG", "response : " + t.getMessage());


            }
        });
    }
}