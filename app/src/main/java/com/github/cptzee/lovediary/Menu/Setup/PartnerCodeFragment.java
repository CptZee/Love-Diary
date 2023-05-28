package com.github.cptzee.lovediary.Menu.Setup;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.github.cptzee.lovediary.R;
import com.github.cptzee.lovediary.Utils.CodeGenerator;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PartnerCodeFragment extends Fragment {
    public PartnerCodeFragment() {
        super(R.layout.fragment_partner_code);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String uuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userRef = database.getReference("Users").child(uuid);
        EditText code = view.findViewById(R.id.setup_code);
        view.findViewById(R.id.setup_code_generate).setOnClickListener(v ->
            code.setText(CodeGenerator.generateRandomCode())
        );

        view.findViewById(R.id.setup_button).setOnClickListener(v->{
            String codeString = code.getText().toString();
            userRef.child("username").setValue(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            userRef.child("code").setValue(codeString);
            endCodeSetup();
        });
    }


    private void endCodeSetup(){
        getView().postDelayed(() -> {
            Snackbar.make(getView(), "Successfuly set the code!", Snackbar.LENGTH_SHORT).show();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentById(R.id.activity_container);

            if (fragment != null) {
                fragmentManager.beginTransaction().remove(fragment).commit();
            }
        }, 3000);
    }
}
