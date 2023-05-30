package com.github.cptzee.lovediary.Menu.Auth;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.cptzee.lovediary.R;
import com.github.cptzee.lovediary.Utils.EmailValidator;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterFragment extends Fragment {
    public RegisterFragment() {
        super(R.layout.fragment_register);
    }

    private FirebaseDatabase database;
    private FirebaseAuth authentication;
    private DatabaseReference userReference;
    private EditText registerEmail, registerUsername, registerPassword, registerConfirmPassword;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        authentication = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        userReference = database.getReference("Users");
        registerEmail = view.findViewById(R.id.register_email);
        registerUsername = view.findViewById(R.id.register_username);
        registerPassword = view.findViewById(R.id.register_password);
        registerConfirmPassword = view.findViewById(R.id.register_password_confirm);

        view.findViewById(R.id.register_link).setOnClickListener(v ->
                getActivity().onBackPressed()
        );

        view.findViewById(R.id.register_button).setOnClickListener(v -> {
            if (checkFields())
                registerUser();
        });
    }

    private boolean checkFields() {
        boolean validFields = true;
        if(EmailValidator.isValidEmail(registerEmail.getText().toString())){
            registerEmail.setError("Invalid Email Address!");
            validFields = false;
        }
        if (registerEmail.getText().toString().isEmpty()) {
            registerEmail.setError("Field is required!");
            validFields = false;
        }
        if (registerUsername.getText().toString().isEmpty()) {
            registerUsername.setError("Field is required!");
            validFields = false;
        }
        if (registerPassword.getText().toString().isEmpty()) {
            registerPassword.setError("Field is required!");
            validFields = false;
        }
        if (registerConfirmPassword.getText().toString().isEmpty()) {
            registerPassword.setError("Field is required!");
            validFields = false;
        }
        if (!registerConfirmPassword.getText().toString().equals(registerPassword.getText().toString())) {
            registerConfirmPassword.setError("Passwords does not match!");
            validFields = false;
        }
        return validFields;
    }

    private void registerUser() {
        authentication.createUserWithEmailAndPassword(registerEmail.getText().toString(), registerPassword.getText().toString())
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        Log.d("UserManager", "Successfully created a user with the email of " + registerEmail.getText().toString());
                        authentication.getCurrentUser().updateProfile(new UserProfileChangeRequest.Builder()
                                .setDisplayName(registerUsername.getText().toString())
                                .build());
                        Snackbar.make(getView(), "Account registration complete! Please login!",
                                Snackbar.LENGTH_SHORT).show();
                        getActivity().onBackPressed();
                    } else {
                        Log.w("UserManager", "Failed to create the user.", task.getException());
                        Snackbar.make(getView(), "Cannot access the authentication servers at the moment!",
                                Snackbar.LENGTH_SHORT).show();
                    }
                });
    }
}
