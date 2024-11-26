package com.example.arrullapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private TextView nombreTextView;
    private TextView apellidoTextView;
    private TextView numeroTextView;
    private TextView noControlTextView;
    private TextView correoTextView;
    private ImageView fotoImageView;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        nombreTextView = view.findViewById(R.id.nombreTextView);
        apellidoTextView = view.findViewById(R.id.apellidoTextView);
        numeroTextView = view.findViewById(R.id.numeroTextView);
        noControlTextView = view.findViewById(R.id.noControlTextView);
        correoTextView = view.findViewById(R.id.correoTextView);
        fotoImageView = view.findViewById(R.id.fotoImageView);

        fetchProfileData();

        return view;
    }

    private void fetchProfileData() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Dev>> call = apiService.getDevs();

        call.enqueue(new Callback<List<Dev>>() {
            @Override
            public void onResponse(Call<List<Dev>> call, Response<List<Dev>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Dev dev = response.body().get(0); // Asumiendo que solo hay un desarrollador
                    updateUI(dev);
                }
            }

            @Override
            public void onFailure(Call<List<Dev>> call, Throwable t) {
                // Maneja el error
            }
        });
    }

    private void updateUI(Dev dev) {
        nombreTextView.setText(dev.getNombredev());
        apellidoTextView.setText(dev.getApellidodev());
        numeroTextView.setText(dev.getNumerodev());
        noControlTextView.setText(dev.getNocontroldev());
        correoTextView.setText(dev.getCorreoddev());

        // Cargar imagen desde la URL
        new LoadImageTask(fotoImageView).execute(dev.getFotodev());
    }

    private static class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public LoadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
