package com.example.arrullapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NotasFragment extends Fragment {
    private CalendarView calendarView;
    private EditText editTextNote;
    private Button buttonSaveNote;
    private String selectedDate;
    private String userId;
    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notas, container, false);

        calendarView = view.findViewById(R.id.calendarView);
        editTextNote = view.findViewById(R.id.editTextNote);
        buttonSaveNote = view.findViewById(R.id.buttonSaveNote);

        // Obtener el userId del usuario logueado desde SharedPreferences
        SharedPreferences preferences = getActivity().getSharedPreferences("myPrefs", getContext().MODE_PRIVATE);
        userId = preferences.getString("user_id", "defaultUserId");  // Reemplaza "defaultUserId" con un valor por defecto adecuado

        // Inicializar Firebase Database Reference con la URL de la base de datos especificada
        databaseReference = FirebaseDatabase.getInstance("https://apinotas-5661a-default-rtdb.firebaseio.com/")
                .getReference("notes")
                .child(userId); // Usar userId para la referencia

        // Establecer la fecha seleccionada por defecto como hoy
        selectedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
            loadNoteForSelectedDate();
        });

        buttonSaveNote.setOnClickListener(v -> saveNoteForSelectedDate());

        // Cargar nota para la fecha seleccionada inicialmente
        loadNoteForSelectedDate();

        return view;
    }

    private void loadNoteForSelectedDate() {
        databaseReference.child(selectedDate).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String noteText = dataSnapshot.getValue(String.class);
                if (noteText != null) {
                    editTextNote.setText(noteText);
                } else {
                    editTextNote.setText("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error al cargar la nota", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveNoteForSelectedDate() {
        String noteText = editTextNote.getText().toString().trim();
        databaseReference.child(selectedDate).setValue(noteText).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getContext(), "Nota guardada", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Error al guardar la nota", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
