package com.github.cptzee.lovediary.Data.Post;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.github.cptzee.lovediary.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, date, message;
        private RecyclerView comments;
        private EditText commentText;
        private AppCompatButton commentAdd;

        public TextView getName() {
            return name;
        }

        public TextView getDate() {
            return date;
        }

        public TextView getMessage() {
            return message;
        }

        public RecyclerView getComments() {
            return comments;
        }

        public EditText getCommentText() {
            return commentText;
        }

        public AppCompatButton getCommentAdd() {
            return commentAdd;
        }

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.post_name);
            date = view.findViewById(R.id.post_date);
            message = view.findViewById(R.id.post_message);
            comments = view.findViewById(R.id.post_comments);
            commentText = view.findViewById(R.id.post_comment);
            commentAdd = view.findViewById(R.id.post_add_comment);
        }

    }

    public PostAdapter(List<Post> dataSet) {
        localDataSet = dataSet;
    }

    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_post, viewGroup, false);
        return new PostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostAdapter.ViewHolder viewHolder, final int position) {
        Post post = localDataSet.get(position);
        FirebaseDatabase.getInstance().getReference("Users").child(post.getOwner())
                .child("username")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        viewHolder.getName().setText(snapshot.getValue(String.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        viewHolder.getName().setText("User");
                    }
                });
        viewHolder.getDate().setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date(post.getDatePosted() * 1000)));
        viewHolder.getMessage().setText(post.getMessage());
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}