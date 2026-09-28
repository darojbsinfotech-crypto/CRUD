package com.example.studyhub.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studyhub.R;
import com.example.studyhub.data.Displayable;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.Holder> {

    public interface Listener {
        void onEdit(Displayable item);
        void onDelete(Displayable item);
    }

    private final List<Displayable> items = new ArrayList<>();
    private final Listener listener;

    public ItemAdapter(Listener listener) { this.listener = listener; }

    public void submit(List<? extends Displayable> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    @NonNull @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder h, int position) {
        Displayable item = items.get(position);
        h.title.setText(item.getTitleText());
        h.body.setText(item.getBodyText());
        String extra = item.getExtraText();
        h.extra.setVisibility(extra == null ? View.GONE : View.VISIBLE);
        h.extra.setText(extra);
        h.edit.setOnClickListener(v -> listener.onEdit(item));
        h.delete.setOnClickListener(v -> listener.onDelete(item));
    }

    @Override public int getItemCount() { return items.size(); }

    static class Holder extends RecyclerView.ViewHolder {
        final TextView title, body, extra;
        final ImageButton edit, delete;
        Holder(View v) {
            super(v);
            title = v.findViewById(R.id.tv_title);
            body = v.findViewById(R.id.tv_body);
            extra = v.findViewById(R.id.tv_extra);
            edit = v.findViewById(R.id.btn_edit);
            delete = v.findViewById(R.id.btn_delete);
        }
    }
}
