package com.github.cptzee.lovediary.Menu.Message;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.cptzee.lovediary.Data.Message.Message;
import com.github.cptzee.lovediary.Data.Message.MessageAdapter;
import com.github.cptzee.lovediary.Manager.SessionManager;
import com.github.cptzee.lovediary.R;
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

public class MessageFragment extends Fragment {
    public MessageFragment() {
        super(R.layout.fragment_message);
    }

    private RecyclerView messages;
    private EditText newMessage;
    private final DatabaseReference messageReference = FirebaseDatabase.getInstance().getReference("Messages");
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        messages = view.findViewById(R.id.message_view);
        newMessage = view.findViewById(R.id.message_new);
        TextView indicator = view.findViewById(R.id.message_indicator);

        indicator.setText("Loading...");

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

                Collections.sort(list, (Comparator<Message>) (m1, m2) -> Long.compare(m1.getSentDate(), m2.getSentDate()));

                messages.setAdapter(new MessageAdapter(list));
                if(list.size() > 0)
                    indicator.setVisibility(View.INVISIBLE);
                indicator.setText("No messages yet, start a conversation now!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        view.findViewById(R.id.message_button).setOnClickListener(v->{
            if(newMessage.getText().toString().isEmpty()){
                new AlertDialog.Builder(getContext())
                        .setTitle("Error")
                        .setMessage("Please write something to send!")
                        .setPositiveButton("Okay", (ignored, ignored2) -> {
                        })
                        .create()
                        .show();
                return;
            }
            Message message = new Message();
            message.setMessage(newMessage.getText().toString());
            message.setPartnerCode(SessionManager.getInstance().getCurrentUser().getCode());
            message.setSender(FirebaseAuth.getInstance().getCurrentUser().getUid());
            message.setSentDate(Calendar.getInstance().getTimeInMillis());
            DatabaseReference  newMessageRef = messageReference.child(String.valueOf(UUID.randomUUID()));
            newMessageRef.setValue(message);
            newMessage.setText("");
        });
    }
}
