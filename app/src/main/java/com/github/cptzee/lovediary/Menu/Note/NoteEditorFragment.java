package com.github.cptzee.lovediary.Menu.Note;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.cptzee.lovediary.R;
import com.google.android.material.snackbar.Snackbar;

public class NoteEditorFragment extends Fragment {
    public NoteEditorFragment() {
        super(R.layout.fragment_notes_editor);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.editor_return_button).setOnClickListener(v->
                onBackPressed()
        );

        view.findViewById(R.id.editor_save_button).setOnClickListener(v->
            saveNote(view)
        );
    }

    private void saveNote(View view){



        Snackbar.make(view, "Note successfully created!", Snackbar.LENGTH_SHORT)
                .setAction("Undo", v->{
                    //Delete the note

                    Toast.makeText(view.getContext(), "Note deleted!", Toast.LENGTH_SHORT).show();
                }).show();
        onBackPressed();
    }

    private void onBackPressed(){
        getActivity().onBackPressed();
    }
}
