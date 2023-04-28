package com.github.cptzee.lovediary.Menu.Splash;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.cptzee.lovediary.Menu.Auth.LoginFragment;
import com.github.cptzee.lovediary.R;

public class SplashFragment extends Fragment {
    public SplashFragment() {
        super(R.layout.fragment_greeting);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = view.findViewById(R.id.greetings);

        textView.setText(R.string.app_name);

        new Handler().postDelayed(() -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.auth_container, new LoginFragment())
                    .commit();
        }, 3000);
    }
}
