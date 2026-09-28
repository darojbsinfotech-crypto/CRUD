package com.example.studyhub.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Subject.class, Task.class, Note.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    /** Single background thread for all write operations (keeps the UI thread free). */
    public static final ExecutorService IO = Executors.newSingleThreadExecutor();

    private static volatile AppDatabase instance;

    public abstract SubjectDao subjectDao();
    public abstract TaskDao taskDao();
    public abstract NoteDao noteDao();

    public static AppDatabase get(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.getApplicationContext(), AppDatabase.class, "studyhub.db").build();
                }
            }
        }
        return instance;
    }
}
