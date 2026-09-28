package com.example.studyhub.ui;

import androidx.lifecycle.LiveData;

import com.example.studyhub.data.Note;

import java.util.List;

public class NotesFragment extends BaseListFragment<Note> {
    @Override protected String entityName() { return "Note"; }
    @Override protected String titleHint() { return "Note title"; }
    @Override protected String bodyHint() { return "Content"; }
    @Override protected String extraHint() { return null; }
    @Override protected LiveData<List<Note>> observeAll() { return db().noteDao().getAll(); }
    @Override protected Note newItem() { return new Note(); }
    @Override protected void fill(Note n, String title, String body, String extra) {
        n.title = title; n.content = body;
    }
    @Override protected void dbInsert(Note n) { db().noteDao().insert(n); }
    @Override protected void dbUpdate(Note n) { db().noteDao().update(n); }
    @Override protected void dbDelete(Note n) { db().noteDao().delete(n); }
}
