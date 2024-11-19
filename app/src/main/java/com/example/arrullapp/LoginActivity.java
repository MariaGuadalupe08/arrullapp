package com.example.arrullapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.arrullapp.models.UserLoginResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private Button btnLogin;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        apiService = ApiClient.getClient().create(ApiService.class);

        btnLogin.setOnClickListener(v -> loginUser());
    }

    private void loginUser() {
        User user = new User();
        user.setUsername(etUsername.getText().toString());
        user.setPassword(etPassword.getText().toString());

        Call<UserLoginResponse> call = apiService.loginUser(user);
        call.enqueue(new Callback<UserLoginResponse>() {
            @Override
            public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserLoginResponse loginResponse = response.body();
                    String accessToken = loginResponse.getAccess();
                    String refreshToken = loginResponse.getRefresh();

                    if (accessToken != null && !accessToken.isEmpty()) {
                        // Guardar el token para usarlo en futuras peticiones
                        SharedPreferences preferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("access_token", accessToken);
                        editor.putString("refresh_token", refreshToken);
                        editor.apply();

                        // Ir a la actividad de bienvenida
                        Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Login failed: No access token received", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("LoginError", "Error Body: " + errorBody);
                        Toast.makeText(LoginActivity.this, "Login failed: " + errorBody, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserLoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Login failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("LoginError", "Throwable: " + t.getMessage());
            }
        });
    }
}

