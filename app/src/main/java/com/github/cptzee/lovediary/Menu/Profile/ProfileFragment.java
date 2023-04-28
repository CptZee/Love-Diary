package com.github.cptzee.lovediary.Menu.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.cptzee.lovediary.AuthActivity;
import com.github.cptzee.lovediary.R;

public class ProfileFragment extends Fragment {
    public ProfileFragment() {
        super(R.layout.fragment_profile);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.profile_logout_button).setOnClickListener(v->{
            getActivity().finish();
            startActivity(new Intent(getContext(), AuthActivity.class));
        });

        view.findViewById(R.id.profile_account_button).setOnClickListener(v->
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_container, new AccountFragment())
                    .commit()
        );

        view.findViewById(R.id.profile_preference_button).setOnClickListener(v->
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_container, new PreferenceFragment())
                    .commit()
        );
    }
}
