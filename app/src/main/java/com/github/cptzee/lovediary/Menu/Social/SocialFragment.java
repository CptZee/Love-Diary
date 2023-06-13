package com.github.cptzee.lovediary.Menu.Social;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.cptzee.lovediary.Data.Note.Note;
import com.github.cptzee.lovediary.Data.Post.Post;
import com.github.cptzee.lovediary.Data.Post.PostAdapter;
import com.github.cptzee.lovediary.Manager.SessionManager;
import com.github.cptzee.lovediary.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SocialFragment extends Fragment {
    public SocialFragment() {
        super(R.layout.fragment_social);
    }

    private RecyclerView posts;
    private TextView indicator;
    private final DatabaseReference postRef = FirebaseDatabase.getInstance().getReference("Posts");

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        posts = view.findViewById(R.id.social_list);
        indicator = view.findViewById(R.id.social_indicator);

        posts.setLayoutManager(new LinearLayoutManager(getContext()));

        postRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List list = new ArrayList();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    Post post = childSnapshot.getValue(Post.class);
                    Log.d("PostHelper", "Connected to firebase... running checks on " + childSnapshot.getKey());
                    Log.d("PostHelper", "Parent is " + post.getParent());
                    if(post.getParent().isEmpty())
                        continue;
                    post.setID(childSnapshot.getKey());
                    list.add(post);
                }
                posts.setAdapter(new PostAdapter(list));
                if(list.size() == 0){
                    indicator.setText("No notes found, create one now!");
                    indicator.setVisibility(View.VISIBLE);
                    return;
                }
                indicator.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}
