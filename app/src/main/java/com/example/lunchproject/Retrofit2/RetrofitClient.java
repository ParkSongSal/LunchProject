package com.example.lunchproject.Retrofit2;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private Retrofit mRetrofit;

    private LunchApi mLunchApi;

    public RetrofitClient(){

        mRetrofit = new Retrofit.Builder()
                .baseUrl(LunchApi.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mLunchApi = mRetrofit.create(LunchApi.class);
    }

    public LunchApi getLunchApi(){
        return mLunchApi;

    }

}
