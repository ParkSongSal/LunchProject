package com.example.lunchproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lunchproject.Retrofit2.LunchApi;
import com.example.lunchproject.Retrofit2.Menu;
import com.example.lunchproject.Retrofit2.Result;
import com.example.lunchproject.Retrofit2.RetrofitClient;
import com.example.lunchproject.util.Common;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuRatingUpdateActivity extends AppCompatActivity {
    private AlertDialog dialog;

    Intent intent;

    String bId ="";
    TextView mWriter;
    RatingBar mRatingBar;
    EditText mContent;

    private LunchApi mLunchApi;
    private SharedPreferences preferences;
    String loginId = "";

    float mGrade = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_rating_update);

        mWriter = findViewById(R.id.writerTxt);
        mRatingBar = findViewById(R.id.ratingBar);
        mContent = findViewById(R.id.contentEdit);

        mLunchApi = new RetrofitClient().getLunchApi();
        preferences = getSharedPreferences("setting", MODE_PRIVATE);
        loginId = preferences.getString("loginId", "");

        intent = getIntent();
        if(intent != null){

            bId = intent.getStringExtra("bId");


            mWriter.setText(loginId);

            mGrade = Float.parseFloat(intent.getStringExtra("grade"));
            mRatingBar.setRating(mGrade);
            mContent.setText(intent.getStringExtra("content"));

        }else{
            Common.intentCommon(MenuRatingUpdateActivity.this, MainActivity.class);
            finish();
            Toast.makeText(getApplicationContext(), "????????? ??????????????????.", Toast.LENGTH_SHORT).show();
        }

        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                //mRateText.setText("Now Rating : " + rating);
                mGrade = v;
                Toast.makeText(getApplicationContext(), "?????? : " + mGrade, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onClick(View view) {
        if (view.getId() == R.id.ratingUpdateBtn) {
            if(!"".equals(bId)){
                menuRatingWrite(bId, mGrade);
            }else{
                Common.intentCommon(this, MainActivity.class);
                finish();
                Toast.makeText(getApplicationContext(), "????????? ???????????????.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void menuRatingWrite(String bId, float grade){

        AlertDialog.Builder builder = new AlertDialog.Builder(MenuRatingUpdateActivity.this);
        final String gradeTxt = String.valueOf(grade);
        final String content = mContent.getText().toString();
        final String rgsDate = Common.nowDate("yyyy-MM-dd HH:mm:ss");

        // ?????? ????????? ?????? ????????????
        if ("".equals(content)) {
            dialog = builder.setMessage("??? ??? ?????? ??????????????????.")
                    .setNegativeButton("??????", null)
                    .create();
            dialog.show();
            return;
        }
        // ????????? ????????? ????????????
        if("".equals(loginId)){
            Common.intentCommon(this, LoginActivity.class);
            finish();
            Toast.makeText(getApplicationContext(), "????????? ????????? ????????????.", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<Result> call = mLunchApi.MenuRatingUpdate(bId, gradeTxt, content, rgsDate, loginId);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                if(response.isSuccessful()) {
                    Result result = response.body();
                    //?????? ??????
                    if ("success".equals(response.body().getResult())) {
                        dialog = builder.setMessage("?????? ????????? ??????????????????.")
                                .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Common.intentCommon(MenuRatingUpdateActivity.this, MainActivity.class);
                                        finish();
                                    }
                                }).create();
                    } else {
                        dialog = builder.setMessage("?????? ????????? ??????????????????.")
                                .setNegativeButton("??????", null)
                                .create();
                    }
                    dialog.show();

                }else{
                    Log.d("TAG","not successful");
                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                // ???????????? ??????
                Toast.makeText(MenuRatingUpdateActivity.this, "????????? ?????? ????????? ?????? ??? ?????? ??????????????????.", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onBackPressed() {

        Common.intentCommon(MenuRatingUpdateActivity.this, MainActivity.class);
        finish();

    } //???????????? ????????????
}