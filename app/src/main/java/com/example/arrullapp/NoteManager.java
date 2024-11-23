package com.example.arrullapp;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.database.ValueEventListener;

public class NoteManager {
    private NoteDatabase noteDatabase;
    private SharedPreferences preferences;

    public NoteManager(Context context) {
        noteDatabase = new NoteDatabase();
        preferences = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
    }

    public void saveNote(Note note) {
        String userId = preferences.getString("user_id", null);
        if (userId != null) {
            noteDatabase.saveNoteForUser(userId, note);
        }
    }

    public void getNotes(ValueEventListener listener) {
        String userId = preferences.getString("user_id", null);
        if (userId != null) {
            noteDatabase.getNotesForUser(userId, listener);
        }
    }
}
