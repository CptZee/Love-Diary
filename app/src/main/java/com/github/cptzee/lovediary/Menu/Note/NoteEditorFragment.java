package com.github.cptzee.lovediary.Menu.Note;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.cptzee.lovediary.Data.Note.Note;
import com.github.cptzee.lovediary.Manager.SessionManager;
import com.github.cptzee.lovediary.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.UUID;

public class NoteEditorFragment extends Fragment {
    public NoteEditorFragment() {
        super(R.layout.fragment_notes_editor);
    }

    private EditText title, body;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title = view.findViewById(R.id.editor_title);
        body = view.findViewById(R.id.editor_content);

        view.findViewById(R.id.editor_return_button).setOnClickListener(v->
                onBackPressed()
        );

        view.findViewById(R.id.editor_save_button).setOnClickListener(v->
            saveNote(view)
        );
    }


    private void saveNote(View view){
        Note note = new Note();
        note.setId(String.valueOf(UUID.randomUUID()));
        note.setArchived(false);
        note.setOwner(FirebaseAuth.getInstance().getCurrentUser().getUid());
        note.setPartner(SessionManager.getInstance().getCurrentUser().getCode());
        note.setShared(false);
        note.setTitle(title.getText().toString());
        note.setBody(body.getText().toString());
        note.setDateUpdated(Calendar.getInstance().getTimeInMillis() / 1000);

        DatabaseReference noteRef = FirebaseDatabase.getInstance().getReference("Notes").child(note.getId());
        noteRef.setValue(note);

        Snackbar.make(view, "Note successfully created!", Snackbar.LENGTH_SHORT)
                .setAction("Undo", v->{
                    noteRef.removeValue();
                    Toast.makeText(view.getContext(), "Note deleted!", Toast.LENGTH_SHORT).show();
                }).show();
        onBackPressed();
    }

    private void onBackPressed(){
        getActivity().onBackPressed();
    }
}
