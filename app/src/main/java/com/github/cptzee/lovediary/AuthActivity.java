package com.github.cptzee.lovediary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.github.cptzee.lovediary.Menu.Auth.LoginFragment;
import com.github.cptzee.lovediary.Menu.Splash.SplashFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}