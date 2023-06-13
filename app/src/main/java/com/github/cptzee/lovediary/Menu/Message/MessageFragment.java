package com.github.cptzee.lovediary.Menu.Message;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.cptzee.lovediary.Data.Message.Message;
import com.github.cptzee.lovediary.Data.Message.MessageAdapter;
import com.github.cptzee.lovediary.Manager.SessionManager;
import com.github.cptzee.lovediary.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends Fragment {
    public MessageFragment() {
        super(R.layout.fragment_message);
    }

    private RecyclerView messages;
    private final DatabaseReference messageReference = FirebaseDatabase.getInstance().getReference("Messages");
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        messages = view.findViewById(R.id.message_view);

        messages.setLayoutManager(new LinearLayoutManager(getContext()));

        messageReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List list = new ArrayList();
                for(DataSnapshot child : snapshot.getChildren()){
                    Message message = child.getValue(Message.class);
                    Log.d("MessageHelper", "Found " + child.getKey());
                    message.setID(child.getKey());
                    if(message.getPartnerCode().equals(SessionManager.getInstance().getCurrentUser().getCode()))
                        list.add(message);
                }
                messages.setAdapter(new MessageAdapter(list));
                if(list.size() > 0)
                    view.findViewById(R.id.message_indicator).setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
