package com.github.cptzee.lovediary.Menu;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.cptzee.lovediary.Menu.Gallery.GalleryFragment;
import com.github.cptzee.lovediary.Menu.Message.MessageFragment;
import com.github.cptzee.lovediary.Menu.Note.NoteFragment;
import com.github.cptzee.lovediary.Menu.Profile.ProfileFragment;
import com.github.cptzee.lovediary.Menu.Social.SocialFragment;
import com.github.cptzee.lovediary.R;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainFragment extends Fragment {
    public MainFragment() {
        super(R.layout.fragment_home);
    }

    public static AnimatedBottomBar navBar;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navBar = view.findViewById(R.id.nav_bar);
        getParentFragmentManager().beginTransaction()
                .replace(R.id.home_container, new NoteFragment())
                .commit();
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
                switch (i) {
                    case 0:
                        fragment = new SocialFragment();
                        break;
                    case 1:
                        fragment = new NoteFragment();
                        break;
                    case 2:
                        fragment = new GalleryFragment();
                        break;
                    case 3:
                        fragment = new MessageFragment();
                        break;
                    case 4:
                        fragment = new ProfileFragment();
                        break;
                }
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.home_container, fragment)
                        .commit();
            }
        });
    }
}
