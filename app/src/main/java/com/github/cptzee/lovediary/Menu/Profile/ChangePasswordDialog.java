package com.github.cptzee.lovediary.Menu.Profile;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.github.cptzee.lovediary.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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
        view.findViewById(R.id.change_button).setOnClickListener(v-> {
           if(checkPassword()){
               if(!validFields())
                   return;
               FirebaseAuth.getInstance().getCurrentUser().updatePassword(newPassword.getText().toString());
               Snackbar.make(getView(), "Password Successfully Updated!", Snackbar.LENGTH_SHORT).show();
               getActivity().onBackPressed();
           }
        });
    }

    private boolean checkPassword() {
        return FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(FirebaseAuth.getInstance().getCurrentUser().getEmail(), currentPassword.getText().toString())
                .isSuccessful();
    }

    private boolean validFields(){
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
