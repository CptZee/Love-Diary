package com.github.cptzee.lovediary.Menu.Splash;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.cptzee.lovediary.Data.User.User;
import com.github.cptzee.lovediary.Manager.SessionManager;
import com.github.cptzee.lovediary.Menu.MainFragment;
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
                String username = snapshot.getValue(String.class);
                if (username == null) {
                    endGreeting();
                    return;
                }
                TextView textView = view.findViewById(R.id.greetings);
                ImageView logoView = view.findViewById(R.id.logoView);

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
                greetings.setText("Hello there, " + username);
                endGreeting();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Snackbar.make(view, "Running in offline mode", Snackbar.LENGTH_SHORT).show();
                endGreeting();
            }
        });
    }

    private void endGreeting() {
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
                else {
                    //Set the current user
                    SessionManager manager = SessionManager.getInstance();
                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid());
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            manager.setCurrentUser(snapshot.getValue(User.class));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    getView().postDelayed(() ->
                                    getParentFragmentManager().beginTransaction()
                                            .replace(R.id.activity_container, new MainFragment())
                                            .commit()
                            , 3000);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Error occurred
                Log.e("FirebaseDatabase", "Error: " + databaseError.getMessage());
            }
        });
    }
}
