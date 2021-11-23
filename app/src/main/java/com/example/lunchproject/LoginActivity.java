package com.example.lunchproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginBtn:
                Toast.makeText(getApplicationContext(), "로그인", Toast.LENGTH_SHORT).show();
                break;
            case R.id.registerBtn:
                Toast.makeText(getApplicationContext(), "회원가입", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}