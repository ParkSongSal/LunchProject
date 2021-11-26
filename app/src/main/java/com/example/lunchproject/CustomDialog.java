package com.example.lunchproject;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CustomDialog extends Dialog {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_dialog);

        //NativeExpressAdView adView = (NativeExpressAdView) findViewById(R.id.adView);
        TextView quit = (TextView) findViewById(R.id.dialog_btn_quit);
        TextView back = (TextView) findViewById(R.id.dialog_btn_back);



        //AdRequest request = new AdRequest.Builder().build();
        //adView.loadAd(request);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


    public CustomDialog(Context context){
        super(context);
    }
}