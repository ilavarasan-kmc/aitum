package com.urufit.aitum.ui;

import com.urufit.aitum.model.UserTokenModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    String BASE_URL = "https://57746554-96ba-4b57-b558-be9952fd1c3d.mock.pstmn.io/";

    @GET("demonew")
    Call<ArrayList<UserTokenModel>> getDash();
}
