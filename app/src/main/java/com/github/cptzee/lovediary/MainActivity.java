package com.github.cptzee.lovediary;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.github.cptzee.lovediary.Menu.Gallery.GalleryFragment;
import com.github.cptzee.lovediary.Menu.MainFragment;
import com.github.cptzee.lovediary.Menu.Message.MessageFragment;
import com.github.cptzee.lovediary.Menu.Note.NoteFragment;
import com.github.cptzee.lovediary.Menu.Profile.ProfileFragment;
import com.github.cptzee.lovediary.Menu.Social.SocialFragment;
import com.github.cptzee.lovediary.Menu.Splash.GreetingFragment;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_container, new GreetingFragment())
                .commit();

    }
}