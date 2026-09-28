package com.example.studyhub.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks")
public class Task implements Displayable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String description;
    public String dueDate;

    @Override public int getId() { return id; }
    @Override public String getTitleText() { return title; }
    @Override public String getBodyText() { return description; }
    @Override public String getExtraText() {
        return (dueDate == null || dueDate.isEmpty()) ? null : "Due: " + dueDate;
    }
}
