package com.github.cptzee.lovediary.Menu.Profile;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.cptzee.lovediary.Manager.SessionManager;
import com.github.cptzee.lovediary.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AccountFragment extends Fragment {
    public AccountFragment() {
        super(R.layout.fragment_account_settings);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText newUsername = view.findViewById(R.id.setup_username);
        view.findViewById(R.id.setup_link).setOnClickListener(v->
                new ChangePasswordDialog().show(getActivity().getSupportFragmentManager(), "changePassword")
                );

        view.findViewById(R.id.setup_button).setOnClickListener(v->{
            if(newUsername.getText().toString().isEmpty()){
                newUsername.setError("Invalid username!");
                return;
            }
            String oldUsername = SessionManager.getInstance().getCurrentUser().getUsername();
            DatabaseReference usernameRef = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("username");
            usernameRef.setValue(newUsername.getText().toString());
            Snackbar.make(view, "Username successfully changed!", Snackbar.LENGTH_SHORT)
                    .setAction("Undo", x->{
                        usernameRef.setValue(oldUsername);
                        Toast.makeText(view.getContext(), "Username reverted!", Toast.LENGTH_SHORT).show();
                    }).show();
            getActivity().onBackPressed();
        });
    }
}
