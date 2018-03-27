package com.example.notes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.notes.R;
import com.example.notes.model.NotesModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.ViewHolder> {

    private final Context context;
    private final List<NotesModel> notes;
    private final LayoutInflater inflater;
    private RecyclerViewItemClick itemClick;

    public NotesListAdapter(Context context, List<NotesModel> notes) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.notes = notes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.note_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(notes.get(position), position);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setItemClick(RecyclerViewItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements IViewHolderBinder<NotesModel> {

        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvNote)
        TextView tvNote;
        private NotesModel note;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                if (itemClick != null) {
                    itemClick.onItemClicked(NotesListAdapter.this, ViewHolder.this, getLayoutPosition());
                }
            });
        }

        @Override
        public void bind(NotesModel note, int position) {
            this.note = note;
            tvNote.setText(note.getNote());
            tvTitle.setText(note.getTitle());
        }

        public NotesModel getData() {
            return note;
        }
    }
}
