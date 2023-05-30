package com.github.cptzee.lovediary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.github.cptzee.lovediary.Data.User.User;
import com.github.cptzee.lovediary.Manager.SessionManager;
import com.github.cptzee.lovediary.Menu.Auth.LoginFragment;
import com.github.cptzee.lovediary.Menu.Splash.SplashFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AuthActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.auth_container, new SplashFragment())
                    .commit();
            return;
        }
        //Set the current user
        SessionManager manager = SessionManager.getInstance();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                manager.setCurrentUser(snapshot.getValue(User.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}