package com.example.stickynotes.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.stickynotes.entities.NoteEntities;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM note ORDER BY id DESC")
    List<NoteEntities> getAllNotes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(NoteEntities noteEntities);

    @Delete
    void deleteNote(NoteEntities noteEntities);
}
