package com.example.studyhub.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SubjectDao {
    @Insert
    void insert(Subject item);

    @Update
    void update(Subject item);

    @Delete
    void delete(Subject item);

    @Query("SELECT * FROM subjects ORDER BY id DESC")
    LiveData<List<Subject>> getAll();
}
