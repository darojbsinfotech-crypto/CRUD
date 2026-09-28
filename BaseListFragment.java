package com.example.studyhub.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studyhub.R;
import com.example.studyhub.data.AppDatabase;
import com.example.studyhub.data.Displayable;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

/**
 * Generic CRUD screen. Subclasses only supply the entity-specific pieces
 * (DAO calls, field labels), so Create/Read/Update/Delete logic lives in one place.
 */
public abstract class BaseListFragment<T extends Displayable> extends Fragment
        implements ItemAdapter.Listener {

    // ----- supplied by subclasses -----
    protected abstract String entityName();
    protected abstract String titleHint();
    protected abstract String bodyHint();
    /** Return null when the model has no third field. */
    protected abstract String extraHint();
    protected abstract LiveData<List<T>> observeAll();
    protected abstract T newItem();
    protected abstract void fill(T item, String title, String body, String extra);
    protected abstract void dbInsert(T item);
    protected abstract void dbUpdate(T item);
    protected abstract void dbDelete(T item);

    protected AppDatabase db() { return AppDatabase.get(requireContext()); }

    private ItemAdapter adapter;
    private ProgressBar progress;
    private TextView empty;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recycler = view.findViewById(R.id.recycler);
        progress = view.findViewById(R.id.progress);
        empty = view.findViewById(R.id.empty);
        ExtendedFloatingActionButton fab = view.findViewById(R.id.fab_add);

        adapter = new ItemAdapter(this);
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        recycler.setAdapter(adapter);

        // READ: LiveData re-emits after every insert/update/delete, so the list stays fresh.
        progress.setVisibility(View.VISIBLE);
        observeAll().observe(getViewLifecycleOwner(), list -> {
            progress.setVisibility(View.GONE);
            adapter.submit(list);
            empty.setVisibility(list.isEmpty() ? View.VISIBLE : View.GONE);
        });

        fab.setOnClickListener(v -> showForm(null));
    }

    // ----- CREATE / UPDATE form -----
    @SuppressWarnings("unchecked")
    @Override public void onEdit(Displayable item) { showForm((T) item); }

    private void showForm(@Nullable T existing) {
        View form = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_form, null);
        TextInputLayout tilTitle = form.findViewById(R.id.til_title);
        TextInputLayout tilBody = form.findViewById(R.id.til_body);
        TextInputLayout tilExtra = form.findViewById(R.id.til_extra);
        TextInputEditText etTitle = form.findViewById(R.id.et_title);
        TextInputEditText etBody = form.findViewById(R.id.et_body);
        TextInputEditText etExtra = form.findViewById(R.id.et_extra);

        tilTitle.setHint(titleHint());
        tilBody.setHint(bodyHint());
        if (extraHint() == null) tilExtra.setVisibility(View.GONE);
        else tilExtra.setHint(extraHint());

        if (existing != null) {
            etTitle.setText(existing.getTitleText());
            etBody.setText(existing.getBodyText());
            String extra = existing.getExtraText();
            // strip the display prefix ("Due: ", "Instructor: ") when pre-filling
            if (extra != null && extra.contains(": ")) extra = extra.substring(extra.indexOf(": ") + 2);
            etExtra.setText(extra);
        }

        AlertDialog dialog = new MaterialAlertDialogBuilder(requireContext())
                .setTitle((existing == null ? "Add " : "Edit ") + entityName())
                .setView(form)
                .setPositiveButton("Save", null)   // listener set below so validation can keep dialog open
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String title = text(etTitle);
            String body = text(etBody);
            String extra = text(etExtra);
            if (title.isEmpty()) {
                tilTitle.setError("Required");
                return;
            }
            tilTitle.setError(null);
            if (existing == null) {
                T item = newItem();
                fill(item, title, body, extra);
                runDb(() -> dbInsert(item), entityName() + " added");
                dialog.dismiss();
            } else {
                // UPDATE requires confirmation
                new MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Confirm update")
                        .setMessage("Save changes to \"" + existing.getTitleText() + "\"?")
                        .setPositiveButton("Update", (d, w) -> {
                            fill(existing, title, body, extra);
                            runDb(() -> dbUpdate(existing), entityName() + " updated");
                            dialog.dismiss();
                        })
                        .setNegativeButton("Back", null)
                        .show();
            }
        });
    }

    // ----- DELETE with confirmation -----
    @SuppressWarnings("unchecked")
    @Override
    public void onDelete(Displayable item) {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Delete " + entityName())
                .setMessage("Delete \"" + item.getTitleText() + "\"? This cannot be undone.")
                .setPositiveButton("Delete", (d, w) ->
                        runDb(() -> dbDelete((T) item), entityName() + " deleted"))
                .setNegativeButton("Cancel", null)
                .show();
    }

    // ----- helpers -----
    private static String text(TextInputEditText et) {
        return et.getText() == null ? "" : et.getText().toString().trim();
    }

    /** Runs a DB write off the UI thread, with error handling and user feedback. */
    private void runDb(Runnable op, String successMessage) {
        AppDatabase.IO.execute(() -> {
            try {
                op.run();
                toast(successMessage);
            } catch (Exception e) {
                toast("Something went wrong: " + e.getMessage());
            }
        });
    }

    private void toast(String message) {
        Activity a = getActivity();
        if (a != null) a.runOnUiThread(() -> Toast.makeText(a, message, Toast.LENGTH_SHORT).show());
    }
}
