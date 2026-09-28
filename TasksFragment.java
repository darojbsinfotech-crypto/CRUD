package com.example.studyhub.ui;

import androidx.lifecycle.LiveData;

import com.example.studyhub.data.Task;

import java.util.List;

public class TasksFragment extends BaseListFragment<Task> {
    @Override protected String entityName() { return "Task"; }
    @Override protected String titleHint() { return "Task title"; }
    @Override protected String bodyHint() { return "Details"; }
    @Override protected String extraHint() { return "Due date (e.g. 2026-10-05)"; }
    @Override protected LiveData<List<Task>> observeAll() { return db().taskDao().getAll(); }
    @Override protected Task newItem() { return new Task(); }
    @Override protected void fill(Task t, String title, String body, String extra) {
        t.title = title; t.description = body; t.dueDate = extra;
    }
    @Override protected void dbInsert(Task t) { db().taskDao().insert(t); }
    @Override protected void dbUpdate(Task t) { db().taskDao().update(t); }
    @Override protected void dbDelete(Task t) { db().taskDao().delete(t); }
}
