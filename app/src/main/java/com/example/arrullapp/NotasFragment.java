package com.example.arrullapp;

import android.os.Bundle;
import android.util.Log;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NotasFragment extends Fragment {
    private NoteManager noteManager;
    private List<Note> noteList = new ArrayList<>();
    private NoteAdapter noteAdapter;
    private EditText editTextNote;
    private CalendarView calendarView;
    private String selectedDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notas, container, false);
        noteManager = new NoteManager(getContext());

        // Configurar RecyclerView y Adapter
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        noteAdapter = new NoteAdapter(noteList);
        recyclerView.setAdapter(noteAdapter);

        // Configurar EditText y CalendarView
        editTextNote = view.findViewById(R.id.editTextNote);
        calendarView = view.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth);

        // Configurar el botón de guardar
        Button buttonSaveNote = view.findViewById(R.id.buttonSaveNote);
        buttonSaveNote.setOnClickListener(v -> saveNote());

        // Cargar las notas del usuario
        loadUserNotes();

        return view;
    }

    private void saveNote() {
        String noteText = editTextNote.getText().toString().trim();
        if (noteText.isEmpty() || selectedDate == null) {
            Toast.makeText(getContext(), "Please enter a note and select a date", Toast.LENGTH_SHORT).show();
            return;
        }

        Note note = new Note();
        note.setDate(selectedDate);
        note.setText(noteText);

        noteManager.saveNote(note);
        Toast.makeText(getContext(), "Note saved", Toast.LENGTH_SHORT).show();
    }

    private void loadUserNotes() {
        noteManager.getNotes(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                noteList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Note note = snapshot.getValue(Note.class);
                    noteList.add(note);
                }
                noteAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("NotasFragment", "Error al cargar las notas", databaseError.toException());
            }
        });
    }
}
