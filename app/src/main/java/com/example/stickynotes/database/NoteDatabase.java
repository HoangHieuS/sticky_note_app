package com.example.stickynotes.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.stickynotes.dao.NoteDAO;
import com.example.stickynotes.entities.NoteEntities;
import com.example.stickynotes.entities.ReminderEntities;

@Database(entities = {NoteEntities.class, ReminderEntities.class}, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase noteDatabase;

    public static synchronized NoteDatabase getNoteDatabase(Context context) {

        if (noteDatabase == null) {
            noteDatabase = Room.databaseBuilder(
                    context,
                    NoteDatabase.class,
                    "note_db"
            ).build();
        }
        return noteDatabase;
    }

    public abstract NoteDAO noteDao();
}
