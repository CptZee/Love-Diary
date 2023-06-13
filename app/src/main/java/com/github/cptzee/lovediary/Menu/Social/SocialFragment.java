package com.github.cptzee.lovediary.Menu.Social;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.cptzee.lovediary.Data.Post.Post;
import com.github.cptzee.lovediary.Data.Post.PostAdapter;
import com.github.cptzee.lovediary.Data.Post.PostType;
import com.github.cptzee.lovediary.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class SocialFragment extends Fragment {
    public SocialFragment() {
        super(R.layout.fragment_social);
    }

    private RecyclerView posts;
    private TextView indicator;
    private EditText newPost;
    private final DatabaseReference postRef = FirebaseDatabase.getInstance().getReference("Posts");

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        posts = view.findViewById(R.id.social_list);
        indicator = view.findViewById(R.id.social_indicator);
        newPost = view.findViewById(R.id.social_new);

        indicator.setText("Loading");

        posts.setLayoutManager(new LinearLayoutManager(getContext()));

        postRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List list = new ArrayList();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    Post post = childSnapshot.getValue(Post.class);
                    post.setID(childSnapshot.getKey());
                    if(!post.getParent().isEmpty())
                        continue;
                    post.setID(childSnapshot.getKey());
                    list.add(post);
                }
                Collections.sort(list, (Comparator<Post>) (m1, m2) -> Long.compare(m2.getDatePosted(), m1.getDatePosted()));

                posts.setAdapter(new PostAdapter(list, PostType.POST));
                if(list.size() == 0){
                    indicator.setText("No posts found, create one now!");
                    indicator.setVisibility(View.VISIBLE);
                    return;
                }
                indicator.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        view.findViewById(R.id.social_button).setOnClickListener(v->{
            if(newPost.getText().toString().isEmpty()) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Error")
                        .setMessage("Please write something to post!")
                        .setPositiveButton("Okay", (ignored, ignored2) -> {
                        })
                        .create()
                        .show();
                return;
            }
            savePost();
        });
    }

    private void savePost(){
        Post post = new Post();
        post.setOwner(FirebaseAuth.getInstance().getCurrentUser().getUid());
        post.setDatePosted(Calendar.getInstance().getTimeInMillis());
        post.setMessage(newPost.getText().toString());
        post.setParent("");

        DatabaseReference newPostRef = postRef.child(String.valueOf(UUID.randomUUID()));
        newPostRef.setValue(post);

        Snackbar.make(getView(), "Post successfully created!", Snackbar.LENGTH_SHORT)
                .setAction("Undo", v->{
                    newPostRef.removeValue();
                    Toast.makeText(getView().getContext(), "Post deleted!", Toast.LENGTH_SHORT).show();
                }).show();
    }

}
