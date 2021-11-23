package com.example.lunchproject.Retrofit2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private Retrofit mRetrofit;

    private LunchApi mLunchApi;

    public RetrofitClient(){

        Gson gson = new GsonBuilder().setLenient().create();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(LunchApi.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        mLunchApi = mRetrofit.create(LunchApi.class);
    }

    public LunchApi getLunchApi(){
        return mLunchApi;

    }

}
