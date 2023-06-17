package com.github.cptzee.lovediary.Menu.Splash;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
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
        ImageView logoView = view.findViewById(R.id.logoView);

        textView.setText(R.string.app_name);

        /**
        Animation textAnimation = new TranslateAnimation(0, 0, 0, 60);
        textAnimation.setDuration(1000);
        textAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        textAnimation.setFillAfter(true);
        textView.startAnimation(textAnimation);

        Animation imageAnimation = new TranslateAnimation(0, 0, -200, 0);
        imageAnimation.setDuration(1000);
        imageAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        imageAnimation.setFillAfter(true);
        logoView.startAnimation(imageAnimation);
*/

        new Handler().postDelayed(() -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.auth_container, new LoginFragment())
                    .commit();
        }, 3000);
    }
}