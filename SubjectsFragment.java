package com.example.studyhub.ui;

import androidx.lifecycle.LiveData;

import com.example.studyhub.data.Subject;

import java.util.List;

public class SubjectsFragment extends BaseListFragment<Subject> {
    @Override protected String entityName() { return "Subject"; }
    @Override protected String titleHint() { return "Subject name"; }
    @Override protected String bodyHint() { return "Description"; }
    @Override protected String extraHint() { return "Instructor"; }
    @Override protected LiveData<List<Subject>> observeAll() { return db().subjectDao().getAll(); }
    @Override protected Subject newItem() { return new Subject(); }
    @Override protected void fill(Subject s, String title, String body, String extra) {
        s.name = title; s.description = body; s.instructor = extra;
    }
    @Override protected void dbInsert(Subject s) { db().subjectDao().insert(s); }
    @Override protected void dbUpdate(Subject s) { db().subjectDao().update(s); }
    @Override protected void dbDelete(Subject s) { db().subjectDao().delete(s); }
}
