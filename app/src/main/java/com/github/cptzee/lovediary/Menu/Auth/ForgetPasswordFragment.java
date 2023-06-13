package com.github.cptzee.lovediary.Menu.Auth;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.cptzee.lovediary.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordFragment extends Fragment {
    public ForgetPasswordFragment() {
        super(R.layout.fragment_recovery);
    }

    private EditText email;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        email = view.findViewById(R.id.recover_email);

        view.findViewById(R.id.recover_button).setOnClickListener(v->{
           if(email.getText().toString().isEmpty()){
               email.setError("Field is required!");
               return;
           }
            FirebaseAuth.getInstance().sendPasswordResetEmail(email.getText().toString()).addOnSuccessListener(s->{
                Snackbar.make(getView(), "Successfully sent the password recovery link to your email.", Snackbar.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }).addOnFailureListener(f->{
                email.setError("Invalid Email!");
            });
        });
    }
}
