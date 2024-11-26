package com.example.arrullapp;

import com.example.arrullapp.models.UserLoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @POST("register/")
    Call<User> registerUser(@Body User user);

    @POST("login/")
    Call<UserLoginResponse> loginUser(@Body User user);

    @GET("user-id/{username}/")
    Call<User> getUserId(@Path("username") String username);

    @GET("exercises/")
    Call<List<Exercise>> getExercises();


    @GET("bebes/")
    Call<List<Bebe>> getBebes();

    @GET("mami/")
    Call<List<Mama>> getMamaInfo();

    @GET("devs/")
    Call<List<Dev>> getDevs();

}


