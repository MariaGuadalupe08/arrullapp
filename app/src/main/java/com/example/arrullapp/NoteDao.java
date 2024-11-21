package com.example.arrullapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM notes WHERE date = :date AND userId = :userId LIMIT 1")
    Note getNoteByDateAndUser(String date, String userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdate(Note note);
}

