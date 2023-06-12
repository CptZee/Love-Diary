package com.github.cptzee.lovediary.Menu.Note;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.cptzee.lovediary.Data.Note.Note;
import com.github.cptzee.lovediary.Data.Note.NoteAdapter;
import com.github.cptzee.lovediary.Manager.SessionManager;
import com.github.cptzee.lovediary.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NoteFragment extends Fragment {
    public NoteFragment() {
        super(R.layout.fragment_notes);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView notes = view.findViewById(R.id.note_list);
        TextView indicator = view.findViewById(R.id.note_indicator);
        DatabaseReference notesRef = FirebaseDatabase.getInstance().getReference("Notes");
        view.findViewById(R.id.note_button).setOnClickListener(v->
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.activity_container, new NoteEditorFragment())
                        .addToBackStack("note")
                        .commit()
                );
        notes.setLayoutManager(new LinearLayoutManager(getContext()));

        notesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List noteList = new ArrayList();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    Note note = childSnapshot.getValue(Note.class);
                    Log.d("NoteHelper", "Connected to firebase... running checks.");
                    if(note.isArchived())
                        continue;
                    if(note.getOwner().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        Log.d("NoteHelper", "A note was found and added into the list.");
                        noteList.add(note);
                        continue;
                    }if(!note.isShared())
                        continue;
                    if(note.getPartner().equals(SessionManager.getInstance().getCurrentUser().getCode()))
                        noteList.add(note);
                }
                Log.d("NoteHelper", "Checked all the notes, showing a list of " + noteList.size());
                notes.setAdapter(new NoteAdapter(noteList, NoteFragment.this));
                if(noteList.size() == 0){
                    indicator.setText("No notes found, create one now!");
                    indicator.setVisibility(View.VISIBLE);
                    return;
                }
                indicator.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
