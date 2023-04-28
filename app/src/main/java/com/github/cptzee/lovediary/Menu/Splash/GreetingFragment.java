package com.github.cptzee.lovediary.Menu.Splash;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.github.cptzee.lovediary.R;

public class GreetingFragment extends Fragment {
    public GreetingFragment() {
        super(R.layout.fragment_greeting);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = view.findViewById(R.id.greetings);

        new Handler().postDelayed(() -> {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentById(R.id.activity_container);

            if (fragment != null) {
                fragmentManager.beginTransaction().remove(fragment).commit();
            }
        }, 3000);
    }
}
