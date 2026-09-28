package com.example.studyhub.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "subjects")
public class Subject implements Displayable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String description;
    public String instructor;

    @Override public int getId() { return id; }
    @Override public String getTitleText() { return name; }
    @Override public String getBodyText() { return description; }
    @Override public String getExtraText() {
        return (instructor == null || instructor.isEmpty()) ? null : "Instructor: " + instructor;
    }
}
