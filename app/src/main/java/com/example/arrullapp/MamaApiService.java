package com.example.arrullapp;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MamaApiService {
    @GET("api/mami")
    Call<MamaResponse> getMamaInfo();
}
