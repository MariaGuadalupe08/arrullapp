package com.example.arrullapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.MapView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationClient;
    private PlacesApiService placesApiService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        // Inicializar Retrofit para la API de Places
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/place/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        placesApiService = retrofit.create(PlacesApiService.class);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        // Obtener la ubicación del usuario
        getUserLocation();
    }

    private void getUserLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Pedir permisos si no están concedidos
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                googleMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));
                showNearbyHospitals(userLocation);
            }
        }).addOnFailureListener(e -> {
            if (e instanceof SecurityException) {
                // Manejar la excepción de seguridad
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido, volver a intentar obtener la ubicación del usuario
                getUserLocation();
            } else {
                // Permiso rechazado, manejar la situación
                // Mostrar un mensaje al usuario indicando que los permisos son necesarios
            }
        }
    }

    private void showNearbyHospitals(LatLng userLocation) {
        String location = userLocation.latitude + "," + userLocation.longitude;
        int radius = 5000; // Radio de búsqueda en metros
        String types = "hospital|doctor|health|pharmacy";
        String apiKey = "AIzaSyADC_X0v9b9sOPH5utEsYvqFdBZ6MownDA"; // Reemplaza con tu API Key

        Call<PlacesResponse> call = placesApiService.getNearbyPlaces(location, radius, types, apiKey);
        call.enqueue(new Callback<PlacesResponse>() {
            @Override
            public void onResponse(Call<PlacesResponse> call, Response<PlacesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (PlacesResponse.PlaceResult result : response.body().getResults()) {
                        LatLng placeLocation = new LatLng(result.getGeometry().getLocation().getLat(), result.getGeometry().getLocation().getLng());
                        String placeName = result.getName();

                        // Personalizar el color del marcador según el tipo de lugar
                        float markerColor = BitmapDescriptorFactory.HUE_RED;
                        if (result.getName().toLowerCase().contains("hospital")) {
                            markerColor = BitmapDescriptorFactory.HUE_RED; // Hospital: Rojo
                        } else if (result.getName().toLowerCase().contains("doctor")) {
                            markerColor = BitmapDescriptorFactory.HUE_BLUE; // Doctor: Azul
                        } else if (result.getName().toLowerCase().contains("health")) {
                            markerColor = BitmapDescriptorFactory.HUE_GREEN; // Salud: Verde
                        } else if (result.getName().toLowerCase().contains("pharmacy")) {
                            markerColor = BitmapDescriptorFactory.HUE_ORANGE; // Farmacia: Naranja
                        }

                        googleMap.addMarker(new MarkerOptions()
                                .position(placeLocation)
                                .title(placeName)
                                .icon(BitmapDescriptorFactory.defaultMarker(markerColor))); // Color personalizado para el marcador
                    }
                }
            }

            @Override
            public void onFailure(Call<PlacesResponse> call, Throwable t) {
                t.printStackTrace();
                // Manejar el error
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
