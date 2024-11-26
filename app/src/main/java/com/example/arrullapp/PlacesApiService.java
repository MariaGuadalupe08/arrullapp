package com.example.arrullapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlacesApiService {
    @GET("nearbysearch/json")
    Call<PlacesResponse> getNearbyPlaces(
            @Query("location") String location,
            @Query("radius") int radius,
            @Query("type") String type,
            @Query("key") String apiKey
    );
}
