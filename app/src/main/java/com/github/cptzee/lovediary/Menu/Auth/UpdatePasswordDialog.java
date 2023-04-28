package com.github.cptzee.lovediary.Menu.Auth;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.github.cptzee.lovediary.R;

public class UpdatePasswordDialog extends DialogFragment {
    public UpdatePasswordDialog() {
        super(R.layout.dialog_password);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.current_password).setVisibility(View.GONE);
    }
}
