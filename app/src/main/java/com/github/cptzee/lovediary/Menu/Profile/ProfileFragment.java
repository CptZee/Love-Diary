package com.github.cptzee.lovediary.Menu.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.cptzee.lovediary.AuthActivity;
import com.github.cptzee.lovediary.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {
    public ProfileFragment() {
        super(R.layout.fragment_profile);
    }

    private FirebaseAuth mAuth;
    private final DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");
    private TextView username, code;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        username = view.findViewById(R.id.profile_username);
        code = view.findViewById(R.id.profile_code);

        mAuth = FirebaseAuth.getInstance();

        final DatabaseReference userRef = usersRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        final DatabaseReference usernameRef = userRef.child("username");
        final DatabaseReference codeRef = userRef.child("code");

        usernameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                username.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        codeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                code.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        view.findViewById(R.id.profile_logout_button).setOnClickListener(v->{
            mAuth.signOut();
            getActivity().finish();
            startActivity(new Intent(getContext(), AuthActivity.class));
        });

        view.findViewById(R.id.profile_account_button).setOnClickListener(v->
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_container, new AccountFragment())
                    .addToBackStack(null)
                    .commit()
        );
    }
}