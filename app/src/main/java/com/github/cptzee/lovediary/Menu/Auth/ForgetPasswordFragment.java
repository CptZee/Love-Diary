package com.github.cptzee.lovediary.Menu.Auth;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.cptzee.lovediary.R;
import com.google.android.material.textfield.TextInputLayout;

public class ForgetPasswordFragment extends Fragment {
    public ForgetPasswordFragment() {
        super(R.layout.fragment_recovery);
    }

    private TextInputLayout code;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        code = view.findViewById(R.id.textInputLayout2);
        code.setVisibility(View.GONE);
    }
}
