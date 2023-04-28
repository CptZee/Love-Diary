package com.github.cptzee.lovediary.Menu.Note;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.cptzee.lovediary.Menu.Splash.GreetingFragment;
import com.github.cptzee.lovediary.R;

public class NoteFragment extends Fragment {
    public NoteFragment() {
        super(R.layout.fragment_notes);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.note_button).setOnClickListener(v->
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.activity_container, new NoteEditorFragment())
                        .commit()
                );
    }
}
