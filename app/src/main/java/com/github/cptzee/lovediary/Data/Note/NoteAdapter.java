package com.github.cptzee.lovediary.Data.Note;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.github.cptzee.lovediary.Menu.Note.NoteViewFragment;
import com.github.cptzee.lovediary.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private List<Note> localDataSet;
    private Fragment fragment;

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

    public NoteAdapter(List<Note> dataSet, Fragment fragment) {
        localDataSet = dataSet;
        this.fragment = fragment;
    }

    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_note, viewGroup, false);
        return new NoteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteAdapter.ViewHolder viewHolder, final int position) {
        viewHolder.getTitle().setText(localDataSet.get(position).getTitle());
        viewHolder.getShared().setText("Shared: " + localDataSet.get(position).isShared());

        Note note = localDataSet.get(position);
        viewHolder.getViewBtn().setOnClickListener(v->
            fragment.getParentFragmentManager().beginTransaction()
                    .replace(R.id.activity_container, new NoteViewFragment(localDataSet.get(position)))
                    .addToBackStack("note")
                    .commit()
        );

        viewHolder.getShare().setOnClickListener(v->{
            if(!note.getOwner().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                Snackbar.make(fragment.getView(), "Unable to set the privacy of the note: \"You don't own this!\"", Snackbar.LENGTH_SHORT).show();
                return;
            }
            if(note.isShared())
                note.setShared(false);
            else
                note.setShared(true);
            FirebaseDatabase.getInstance().getReference("Notes")
                    .child(note.getId()).child("shared")
                    .setValue(note.isShared());
            Snackbar.make(fragment.getView(), "Successfully changed the note's privacy", Snackbar.LENGTH_SHORT).show();
        });
        viewHolder.getDelete().setOnClickListener(v->{
            if(!note.getOwner().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                Snackbar.make(fragment.getView(), "Unable to delete the note: \"You don't own this!\"", Snackbar.LENGTH_SHORT).show();
                return;
            }
            FirebaseDatabase.getInstance().getReference("Notes")
                    .child(note.getId()).child("archived")
                    .setValue(true);
            Snackbar.make(fragment.getView(), "Successfully removed the note!", Snackbar.LENGTH_SHORT)
                    .setAction("Undo", ignored ->
                            FirebaseDatabase.getInstance().getReference("Notes")
                                    .child(note.getId()).child("archived")
                                    .setValue(false)
                    ).show();
        });
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
