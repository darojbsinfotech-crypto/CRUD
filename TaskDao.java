package com.example.studyhub.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void insert(Task item);

    @Update
    void update(Task item);

    @Delete
    void delete(Task item);

    @Query("SELECT * FROM tasks ORDER BY id DESC")
    LiveData<List<Task>> getAll();
}
