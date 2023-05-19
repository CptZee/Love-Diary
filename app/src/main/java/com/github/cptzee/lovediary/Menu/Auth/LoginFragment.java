package com.github.cptzee.lovediary.Menu.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.cptzee.lovediary.MainActivity;
import com.github.cptzee.lovediary.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class LoginFragment extends Fragment {
    public LoginFragment() {
        super(R.layout.fragment_login);
    }

    private FirebaseAuth authentication;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText email = view.findViewById(R.id.login_email);
        EditText password = view.findViewById(R.id.login_password);

        authentication = FirebaseAuth.getInstance();
        view.findViewById(R.id.setup_button).setOnClickListener(v-> {
            getActivity().getSupportFragmentManager().popBackStackImmediate();
            authentication.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    startActivity(new Intent(getContext(), MainActivity.class));
                    getActivity().finish();
                }else {
                    email.setError("Invalid Email");
                    password.setError("Invalid Password");
                }
            });
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