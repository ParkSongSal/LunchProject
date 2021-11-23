package com.example.lunchproject.Retrofit2;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface LunchApi {

      String BaseUrl = "https://lunch1116.cafe24.com/lunch/";

    // Login
    //@FormUrlEncoded
    @GET("user_login.php")
    Call<Result> UserLogin(@Query("userId") String userId,
                                @Query("userPw") String userPw);


    // userId 중복검사
    @GET("user_id_validate.php")
    Call<Result> UserId_Validate(@Query("userId") String userId);

    //닉네임 회원가입
    @GET("user_register.php")
    Call<Result> User_Register(@Query("userId") String user_name,
                                    @Query("userPw") String user_phone,
                                    @Query("rgs_date") String rgs_date);

    //@FormUrlEncoded
    // 게시판 리스트 조회
    @POST("getBoardList.php")
    Call<List<ResultModel>> getBoardList();

    // 게시판 Insert (이미지 o)
    @Multipart
    @POST("Gwangju_Board_Insert.php")
    Call<ResponseBody>InsertBoard(@Part("USER") RequestBody user,
                                  @Part("TITLE") RequestBody title,
                                  @Part("CONTENT") RequestBody content,
                                  @Part("DATE") RequestBody date,
                                  @Part MultipartBody.Part image);



    // 게시판 Insert (이미지 x)
    @Multipart
    @POST("Gwangju_Board_Insert_NoImage.php")
    Call<ResponseBody>InsertBoard_NoImage(@Part("USER") RequestBody user,
                                          @Part("TITLE") RequestBody title,
                                          @Part("CONTENT") RequestBody content,
                                          @Part("DATE") RequestBody date);

    // 게시판 Update (이미지 o)
    @Multipart
    @POST("Gwangju_Board_Update.php")
    Call<ResponseBody>UpdateBoard(@Part("Seq") RequestBody seq,
                                  @Part("USER") RequestBody user,
                                  @Part("TITLE") RequestBody title,
                                  @Part("CONTENT") RequestBody content,
                                  @Part("DATE") RequestBody date,
                                  @Part MultipartBody.Part image);

    // 게시판 Update (이미지 x)
    @Multipart
    @POST("Gwangju_Board_No_Image_Update.php")
    Call<ResponseBody>UpdateBoard_NoImage(@Part("Seq") RequestBody seq,
                                          @Part("USER") RequestBody user,
                                          @Part("TITLE") RequestBody title,
                                          @Part("CONTENT") RequestBody content,
                                          @Part("DATE") RequestBody date);


}
