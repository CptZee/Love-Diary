package com.github.cptzee.lovediary.Menu.Splash;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.github.cptzee.lovediary.Menu.Setup.PartnerCodeFragment;
import com.github.cptzee.lovediary.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GreetingFragment extends Fragment {
    public GreetingFragment() {
        super(R.layout.fragment_greeting);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String uuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userRef = database.getReference("Users").child(uuid).child("username");
        TextView greetings = view.findViewById(R.id.greetings);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                greetings.setText("Hello there, " + snapshot.getValue(String.class));
                Snackbar.make(view, "Connected to the servers", Snackbar.LENGTH_SHORT).show();
                endGreeting();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Snackbar.make(view, "Running in offline mode", Snackbar.LENGTH_SHORT).show();
                endGreeting();
            }
        });
    }

    private void endGreeting(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        String currentUserUUID = currentUser.getUid();
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChild(currentUserUUID))
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.activity_container, new PartnerCodeFragment())
                            .commit();
                else
                    getView().postDelayed(() -> {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        Fragment fragment = fragmentManager.findFragmentById(R.id.activity_container);

                        if (fragment != null) {
                            fragmentManager.beginTransaction().remove(fragment).commit();
                        }
                    }, 3000);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Error occurred
                Log.e("FirebaseDatabase", "Error: " + databaseError.getMessage());
            }
        });
    }
}
