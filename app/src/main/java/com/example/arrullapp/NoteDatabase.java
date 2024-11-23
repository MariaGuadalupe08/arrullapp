package com.example.arrullapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/*@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()  // Permitir consultas en el hilo principal (no recomendado para producción)
                    .build();
        }
        return instance;
    }
}*/


public class NoteDatabase {
    private DatabaseReference databaseReference;

    public NoteDatabase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("notes");
    }

    public void saveNoteForUser(String userId, Note note) {
        databaseReference.child(userId).child(note.getDate()).setValue(note);
    }

    public void getNotesForUser(String userId, ValueEventListener listener) {
        databaseReference.child(userId).addListenerForSingleValueEvent(listener);
    }
}


