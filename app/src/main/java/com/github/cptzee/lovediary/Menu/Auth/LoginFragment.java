package com.github.cptzee.lovediary.Menu.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.cptzee.lovediary.MainActivity;
import com.github.cptzee.lovediary.R;

public class LoginFragment extends Fragment {
    public LoginFragment() {
        super(R.layout.fragment_login);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.setup_button).setOnClickListener(v-> {
            getActivity().getSupportFragmentManager().popBackStackImmediate();
            startActivity(new Intent(getContext(), MainActivity.class));
            getActivity().finish();
        });
        view.findViewById(R.id.setup_link).setOnClickListener(v->
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.auth_container, new RegisterFragment())
                    .addToBackStack("login")
                    .commit()
        );
        view.findViewById(R.id.login_password_link).setOnClickListener(v->
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.auth_container, new ForgetPasswordFragment())
                        .addToBackStack("login")
                        .commit()
        );
    }
}