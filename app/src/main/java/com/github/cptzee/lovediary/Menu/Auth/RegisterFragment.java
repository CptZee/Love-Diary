package com.github.cptzee.lovediary.Menu.Auth;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.cptzee.lovediary.R;

public class RegisterFragment extends Fragment {
    public RegisterFragment() {
        super(R.layout.fragment_register);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.register_link).setOnClickListener(v->
            getActivity().onBackPressed()
        );
        view.findViewById(R.id.register_button).setOnClickListener(v->{

            getActivity().onBackPressed();
        });
    }
}
