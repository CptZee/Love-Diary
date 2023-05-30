package com.github.cptzee.lovediary.Menu.Setup;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.github.cptzee.lovediary.Data.User.Partner;
import com.github.cptzee.lovediary.Data.User.User;
import com.github.cptzee.lovediary.Manager.SessionManager;
import com.github.cptzee.lovediary.Menu.MainFragment;
import com.github.cptzee.lovediary.R;
import com.github.cptzee.lovediary.Utils.CodeGenerator;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        DatabaseReference partnersRef = database.getReference("Partners");
        EditText code = view.findViewById(R.id.setup_code);
        view.findViewById(R.id.setup_code_generate).setOnClickListener(v ->
                code.setText(CodeGenerator.generateRandomCode())
        );

        view.findViewById(R.id.setup_button).setOnClickListener(v -> {
            String codeString = code.getText().toString();
            partnersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        Partner partner = childSnapshot.getValue(Partner.class);
                        String partnerKey = childSnapshot.getKey();
                        if (partnerKey.equals(codeString)) {
                            if (partner.isFilled()) {
                                Snackbar.make(getView(), "Code already in use by a pair!", Snackbar.LENGTH_SHORT).show();
                                return;
                            }
                            Snackbar.make(getView(), "Pairing account with the code " + codeString, Snackbar.LENGTH_SHORT).show();
                            userRef.child("username").setValue(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                            userRef.child("code").setValue(codeString);
                            partnersRef.child(codeString).child("partner2").setValue(uuid);
                            partnersRef.child(codeString).child("filled").setValue(true);
                            endCodeSetup();
                            return;
                        }
                    }
                    Log.d("CodeHelper", "Code doesn't exist in the system, creating a new one!");
                    Snackbar.make(getView(), "Successfully set your partner code into " + codeString, Snackbar.LENGTH_SHORT).show();
                    userRef.child("username").setValue(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                    userRef.child("code").setValue(codeString);
                    partnersRef.child(codeString).child("partner1").setValue(uuid);
                    partnersRef.child(codeString).child("filled").setValue(false);
                    endCodeSetup();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });
    }

    private void endCodeSetup() {
        //Set the current user
        SessionManager manager = SessionManager.getInstance();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                manager.setCurrentUser(snapshot.getValue(User.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        getView().postDelayed(() -> {
            Snackbar.make(getView(), "Successfully set the code!", Snackbar.LENGTH_SHORT).show();
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.activity_container, new MainFragment())
                    .commit();
        }, 3000);
    }
}
