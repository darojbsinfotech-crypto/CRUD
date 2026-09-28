package com.example.studyhub.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note implements Displayable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String content;

    @Override public int getId() { return id; }
    @Override public String getTitleText() { return title; }
    @Override public String getBodyText() { return content; }
    @Override public String getExtraText() { return null; }
}
