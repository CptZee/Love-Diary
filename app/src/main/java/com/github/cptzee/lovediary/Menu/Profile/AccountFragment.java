package com.github.cptzee.lovediary.Menu.Profile;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.cptzee.lovediary.R;

public class AccountFragment extends Fragment {
    public AccountFragment() {
        super(R.layout.fragment_account_settings);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.setup_link).setOnClickListener(v->
                new ChangePasswordDialog().show(getActivity().getSupportFragmentManager(), "changePassword")
                );
    }
}
