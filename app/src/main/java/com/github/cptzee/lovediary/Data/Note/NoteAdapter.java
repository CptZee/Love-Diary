package com.github.cptzee.lovediary.Data.Note;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.github.cptzee.lovediary.R;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private List<Note> localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, shared;
        private Button viewBtn, share, delete;

        public TextView getTitle() {
            return title;
        }

        public TextView getShared() {
            return shared;
        }

        public Button getViewBtn() {
            return viewBtn;
        }

        public Button getShare() {
            return share;
        }

        public Button getDelete() {
            return delete;
        }

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.item_note_title);
            shared = view.findViewById(R.id.item_note_indicator);
            viewBtn = view.findViewById(R.id.item_note_view_button);
            share = view.findViewById(R.id.item_note_share_button);
            delete = view.findViewById(R.id.item_note_delete_button);
        }

    }

    public NoteAdapter(List<Note> dataSet) {
        localDataSet = dataSet;
    }

    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.items_note, viewGroup, false);
        return new NoteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteAdapter.ViewHolder viewHolder, final int position) {
        viewHolder.getTitle().setText(localDataSet.get(position).getTitle());
        viewHolder.getShared().setText("Shared: " + localDataSet.get(position).isShared());
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
