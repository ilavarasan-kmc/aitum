package com.urufit.aitum.ui;

import com.urufit.aitum.model.UserDetailsModel;
import com.urufit.aitum.model.UserTokenModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    String BASE_URL = "https://y16dosf4mh.execute-api.ap-south-1.amazonaws.com/main/v1/";

    @GET("/demonew")
    Call<UserTokenModel> getDash();

    @GET("users/{email}")
    Call<UserDetailsModel> getUserDetails(@Path("email") String email,@Header("Authorization:") String token);
}
