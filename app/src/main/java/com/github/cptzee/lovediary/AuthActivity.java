package com.github.cptzee.lovediary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.cptzee.lovediary.Menu.Auth.LoginFragment;
import com.github.cptzee.lovediary.Menu.Splash.SplashFragment;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.auth_container, new SplashFragment())
                .commit();
    }
}