package com.github.cptzee.lovediary.Menu.Profile;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.github.cptzee.lovediary.R;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordDialog extends DialogFragment {
    public ChangePasswordDialog() {
        super(R.layout.dialog_password);
    }

    private EditText currentPassword, newPassword, confirmPassword;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentPassword = view.findViewById(R.id.current_password);
        newPassword = view.findViewById(R.id.change_password);
        confirmPassword = view.findViewById(R.id.change_password_confirm);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        view.findViewById(R.id.change_button).setOnClickListener(v -> {
            if (currentPassword.getText().toString().isEmpty()) {
                currentPassword.setError("Field is required!");
                return;
            }
            AuthCredential credential = EmailAuthProvider
                    .getCredential(currentUser.getEmail(),
                            currentPassword.getText().toString());
            currentUser.reauthenticate(credential).addOnSuccessListener(s->{
                if (!validFields())
                    return;
                currentUser.updatePassword(newPassword.getText().toString()).addOnSuccessListener(s1->{
                    Toast.makeText(getContext(), "Password Successfully Updated!", Toast.LENGTH_SHORT).show();
                    dismiss();
                    getActivity().onBackPressed();
                }).addOnFailureListener(f1->
                        newPassword.setError("Invalid Password!"));
            }).addOnFailureListener(f-> currentPassword.setError("Invalid Password!"));
        });
    }

    private boolean validFields() {
        boolean validFields = true;
        if (newPassword.getText().toString().isEmpty()) {
            newPassword.setError("Field is required!");
            validFields = false;
        }
        if (confirmPassword.getText().toString().isEmpty()) {
            confirmPassword.setError("Field is required!");
            validFields = false;
        }
        if (!confirmPassword.getText().toString().equals(newPassword.getText().toString())) {
            confirmPassword.setError("Passwords does not match!");
            validFields = false;
        }
        return validFields;
    }
}
