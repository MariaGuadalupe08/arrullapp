package com.example.arrullapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TuBebeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TuBebeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Button[] buttons;
    private ImageView imagenBebe;
    private TextView descripcionBebe;
    private ImageView imagenFruta;
    private TextView descripcionFruta;
    private View card;
    private View cardFruta;
    private ImageButton closeButton;

    public TuBebeFragment() {
        // Required empty public constructor
    }

    public static TuBebeFragment newInstance(String param1, String param2) {
        TuBebeFragment fragment = new TuBebeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        View view = inflater.inflate(R.layout.fragment_tu_bebe, container, false);

        imagenBebe = view.findViewById(R.id.imagenBebe);
        descripcionBebe = view.findViewById(R.id.descripcionBebe);
        imagenFruta = view.findViewById(R.id.imagenFruta);
        descripcionFruta = view.findViewById(R.id.descripcionFruta);
        card = view.findViewById(R.id.card);
        cardFruta = view.findViewById(R.id.cardFruta);
        closeButton = view.findViewById(R.id.closeButton);

        buttons = new Button[9];
        buttons[0] = view.findViewById(R.id.button1);
        buttons[1] = view.findViewById(R.id.button2);
        buttons[2] = view.findViewById(R.id.button3);
        buttons[3] = view.findViewById(R.id.button4);
        buttons[4] = view.findViewById(R.id.button5);
        buttons[5] = view.findViewById(R.id.button6);
        buttons[6] = view.findViewById(R.id.button7);
        buttons[7] = view.findViewById(R.id.button8);
        buttons[8] = view.findViewById(R.id.button9);

        for (int i = 0; i < buttons.length; i++) {
            final int month = i + 1;
            buttons[i].setOnClickListener(v -> {
                for (Button btn : buttons) {
                    btn.setSelected(false);
                }
                buttons[month - 1].setSelected(true);
                fetchBebeData(month);
            });
        }

        card.setOnClickListener(v -> showFrutaData());
        closeButton.setOnClickListener(v -> cardFruta.setVisibility(View.GONE));

        return view;
    }

    private void fetchBebeData(int month) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Bebe>> call = apiService.getBebes();
        call.enqueue(new Callback<List<Bebe>>() {
            @Override
            public void onResponse(Call<List<Bebe>> call, Response<List<Bebe>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Bebe bebe = response.body().get(month - 1);
                    Picasso.get().load(bebe.getImagenBebe()).into(imagenBebe);
                    descripcionBebe.setText(bebe.getDescripcionBebe());
                    Picasso.get().load(bebe.getImagenFruta()).into(imagenFruta);
                    descripcionFruta.setText(bebe.getDescripcionFruta());
                } else {
                    Toast.makeText(getContext(), "Error al obtener los datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Bebe>> call, Throwable t) {
                Toast.makeText(getContext(), "Fallo al conectarse a la API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showFrutaData() {
        cardFruta.setVisibility(View.VISIBLE);
    }
}
