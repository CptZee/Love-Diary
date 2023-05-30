package com.github.cptzee.lovediary;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.github.cptzee.lovediary.Menu.Gallery.GalleryFragment;
import com.github.cptzee.lovediary.Menu.Message.MessageFragment;
import com.github.cptzee.lovediary.Menu.Note.NoteFragment;
import com.github.cptzee.lovediary.Menu.Profile.ProfileFragment;
import com.github.cptzee.lovediary.Menu.Social.SocialFragment;
import com.github.cptzee.lovediary.Menu.Splash.GreetingFragment;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainActivity extends AppCompatActivity {

    private AnimatedBottomBar navBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navBar = findViewById(R.id.nav_bar);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_container, new GreetingFragment())
                .commit();

        navBar.selectTabAt(1, false);
        navBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int i, @Nullable AnimatedBottomBar.Tab tab, int i1, @NonNull AnimatedBottomBar.Tab tab1) {
                updateView(i1);
            }

            @Override
            public void onTabReselected(int i, @NonNull AnimatedBottomBar.Tab tab) {
                updateView(i);
            }

            private void updateView(int i) {
                Fragment fragment = null;
                String label = "";
                switch (i) {
                    case 0:
                        fragment = new SocialFragment();
                        label = "social";
                        break;
                    case 1:
                        fragment = new NoteFragment();
                        label = "note";
                        break;
                    case 2:
                        fragment = new GalleryFragment();
                        label = "gallery";
                        break;
                    case 3:
                        fragment = new MessageFragment();
                        label = "chat";
                        break;
                    case 4:
                        fragment = new ProfileFragment();
                        label = "profile";
                        break;
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, fragment)
                        .addToBackStack(label)
                        .commit();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.activity_container);

        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            return;
        }

        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit the application?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        finish();
                    })
                    .setNegativeButton("No", (dialog, which) -> {
                        //Ignore
                    })
                    .create()
                    .show();
            return;
        }
        super.onBackPressed();
        String name = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
        getSupportFragmentManager().popBackStack();
        if (name == null)
            return;
        switch (name) {
            case "social":
                navBar.selectTabAt(0, true);
                break;
            case "note":
                navBar.selectTabAt(1, true);
                break;
            case "gallery":
                navBar.selectTabAt(2, true);
                break;
            case "chat":
                navBar.selectTabAt(3, true);
                break;
            case "profile":
                navBar.selectTabAt(4, true);
                break;
        }
    }
}