package com.example.lunchproject.Retrofit2;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface LunchApi {

    // 서버 URL
    String BaseUrl = "https://lunch1116.cafe24.com/lunch/";

    // 로그인
    @GET("user_login.php")
    Call<Result> UserLogin(@Query("userId") String userId,
                           @Query("userPw") String userPw);

    // userId 중복검사
    @GET("user_id_validate.php")
    Call<Result> UserId_Validate(@Query("userId") String userId);

    // 회원가입
    @GET("user_register.php")
    Call<Result> User_Register(@Query("userId") String user_name,
                               @Query("userPw") String user_phone,
                               @Query("rgs_date") String rgs_date);

    // 메뉴 추천
    @Multipart
    @POST("menu_recommend.php")
    Call<List<Menu>> Menu_Recommend(@Part("menuCode") RequestBody menuCode);

    @Multipart
    @POST("menuKind_recommend.php")
    Call<List<Menu>> MenuKind_Recommend(@Part("menuKind") RequestBody menuKind,
                                        @Part("menuCode") RequestBody menuCode);


    
    // 메뉴 평가 등록
    @GET("menuRating_write.php")
    Call<Result> MenuRatingWrite(@Query("menuCode") String menuCode,
                                 @Query("grade") String grade,
                                 @Query("content") String content,
                                 @Query("registDate") String rgsDate,
                                 @Query("registId") String registId,
                                 @Query("updateDate") String updateDate,
                                 @Query("updateId") String updateId);


    // 메뉴 평가 수정
    @GET("menu_rating_update.php")
    Call<Result> MenuRatingUpdate(@Query("b_id") String b_id,
                                 @Query("grade") String grade,
                                 @Query("content") String content,
                                 @Query("updateDate") String updateDate,
                                 @Query("updateId") String updateId);

    // 메뉴 평가 조회
    @Multipart
    @POST("menu_rating_select.php")
    Call<List<MenuRating>> MenuRatingSelect(@Part("menuCode") RequestBody menuCode);

    // 메뉴 평가 삭제
    @GET("menu_rating_delete.php")
    Call<Result> MenuRatingDelete(@Query("b_id") String b_id);




}
