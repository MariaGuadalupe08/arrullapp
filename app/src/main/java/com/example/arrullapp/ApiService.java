package com.example.arrullapp;

import com.example.arrullapp.models.UserLoginResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("register/")
    Call<User> registerUser(@Body User user);

    @POST("login/")
    Call<UserLoginResponse> loginUser(@Body User user);
}


