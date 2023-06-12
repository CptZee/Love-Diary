package com.github.cptzee.lovediary.Menu.Note;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.cptzee.lovediary.Data.Note.Note;
import com.github.cptzee.lovediary.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;

public class NoteViewFragment extends Fragment {
    private Note note;

    public NoteViewFragment(Note note) {
        super(R.layout.fragment_notes_editor);
        this.note = note;
    }

    private EditText title, body;
    private String originalTitle, originalBody;
    private long originalUpdateDate;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title = view.findViewById(R.id.editor_title);
        body = view.findViewById(R.id.editor_content);

        title.setText(note.getTitle());
        body.setText(note.getBody());

        originalTitle = note.getTitle();
        originalBody = note.getBody();
        originalUpdateDate = note.getDateUpdated();

        view.findViewById(R.id.editor_return_button).setOnClickListener(v ->
                onBackPressed()
        );

        view.findViewById(R.id.editor_save_button).setOnClickListener(v ->
                saveNote(view)
        );
    }


    private void saveNote(View view) {
        note.setTitle(title.getText().toString());
        note.setBody(body.getText().toString());
        note.setDateUpdated(Calendar.getInstance().getTimeInMillis() / 1000);
        DatabaseReference noteRef = FirebaseDatabase.getInstance().getReference("Notes").child(note.getId());
        noteRef.child("title").setValue(note.getTitle());
        noteRef.child("body").setValue(note.getBody());
        noteRef.child("dateUpdated").setValue(note.getDateUpdated());
        Snackbar.make(view, "Note successfully updated!", Snackbar.LENGTH_SHORT)
                .setAction("Undo", v -> {
                    note.setTitle(originalTitle);
                    note.setBody(originalBody);
                    note.setDateUpdated(originalUpdateDate);
                    noteRef.child("title").setValue(note.getTitle());
                    noteRef.child("body").setValue(note.getBody());
                    noteRef.child("dateUpdated").setValue(note.getDateUpdated());
                    Toast.makeText(view.getContext(), "Note reverted!", Toast.LENGTH_SHORT).show();
                }).show();
        onBackPressed();
    }

    private void onBackPressed() {
        getActivity().onBackPressed();
    }
}
