package com.example.stickynotes.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.stickynotes.entities.NoteEntities;
import com.example.stickynotes.entities.ReminderEntities;

import java.util.List;

@Dao
public interface NoteDAO {

    //NOTE//
    @Query("SELECT * FROM note ORDER BY id DESC")
    List<NoteEntities> getAllNotes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(NoteEntities noteEntities);

    @Delete
    void deleteNote(NoteEntities noteEntities);

    //REMINDER//
    @Query("SELECT * FROM reminder ORDER BY id DESC")
    List<ReminderEntities> getAllReminders();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertReminder(ReminderEntities reminderEntities);

    @Delete
    void deleteReminder(ReminderEntities reminderEntities);
}
