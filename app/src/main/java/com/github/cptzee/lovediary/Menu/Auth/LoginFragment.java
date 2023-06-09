package com.github.cptzee.lovediary.Menu.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.cptzee.lovediary.MainActivity;
import com.github.cptzee.lovediary.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;

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
                    if(!authentication.getCurrentUser().isEmailVerified()){
                        Snackbar.make(getView(), "User is not verified yet! Check your email for the verification link", Snackbar.LENGTH_SHORT)
                                .setAction("Resend", a->{
                                    authentication.getCurrentUser().sendEmailVerification()
                                            .addOnSuccessListener(s -> Snackbar.make(getView(), "Verification email resent! Please check your inbox.",
                                                    Snackbar.LENGTH_SHORT).show()
                                            ).addOnFailureListener(f -> {
                                                Log.e("AuthHelper", f.getMessage());
                                                Snackbar.make(getView(), f.getMessage(),
                                                        Snackbar.LENGTH_SHORT).show();
                                            });
                                }).show();
                        email.setError("Unverified account");
                        password.setError("");
                        return;
                    }
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
        view.findViewById(R.id.setup_code_generate).setOnClickListener(v->
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.auth_container, new ForgetPasswordFragment())
                        .addToBackStack("login")
                        .commit()
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(authentication.getCurrentUser() == null)
            return;
        if(!authentication.getCurrentUser().isEmailVerified())
            authentication.signOut();
    }
}