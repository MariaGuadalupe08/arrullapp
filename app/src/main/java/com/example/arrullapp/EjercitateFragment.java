/* package com.example.arrullapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EjercitateFragment extends Fragment {
    private RecyclerView recyclerView;
    private ExerciseAdapter exerciseAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ejercitate, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewExercises);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadExercises();

        return view;
    }

    private void loadExercises() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Exercise>> call = apiService.getExercises();
        call.enqueue(new Callback<List<Exercise>>() {
            @Override
            public void onResponse(Call<List<Exercise>> call, Response<List<Exercise>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Exercise> exerciseList = response.body();
                    exerciseAdapter = new ExerciseAdapter(exerciseList);
                    recyclerView.setAdapter(exerciseAdapter);
                } else {
                    Log.e("EjercitateFragment", "Error in response: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Exercise>> call, Throwable t) {
                Log.e("EjercitateFragment", "Failure: " + t.getMessage());
            }
        });
    }
}
*/

package com.example.arrullapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EjercitateFragment extends Fragment {
    private RecyclerView recyclerView;
    private ExerciseAdapter exerciseAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ejercitate, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewExercises);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadExercises();

        return view;
    }

    private void loadExercises() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Exercise>> call = apiService.getExercises();
        call.enqueue(new Callback<List<Exercise>>() {
            @Override
            public void onResponse(Call<List<Exercise>> call, Response<List<Exercise>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Exercise> exerciseList = response.body();
                    exerciseAdapter = new ExerciseAdapter(getContext(), exerciseList);
                    recyclerView.setAdapter(exerciseAdapter);
                } else {
                    Log.e("EjercitateFragment", "Error in response: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Exercise>> call, Throwable t) {
                Log.e("EjercitateFragment", "Failure: " + t.getMessage());
            }
        });
    }
}

