package com.github.cptzee.lovediary.Data.Message;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.github.cptzee.lovediary.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<Message> localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView received, sent;

        public TextView getSent() {
            return sent;
        }

        public TextView getReceived() {
            return received;
        }

        public ViewHolder(View view) {
            super(view);
            received = view.findViewById(R.id.message_received);
            sent = view.findViewById(R.id.message_sent);
        }

    }

    public MessageAdapter(List<Message> dataSet) {
        localDataSet = dataSet;
    }

    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_chat, viewGroup, false);
        return new MessageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageAdapter.ViewHolder viewHolder, final int position) {
        Message message = localDataSet.get(position);
        String uuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if(message.getSender().equals(uuid)){
            viewHolder.getSent().setText(message.getMessage());
            viewHolder.getReceived().setVisibility(View.INVISIBLE);
        }else{
            viewHolder.getReceived().setText(message.getMessage());
            viewHolder.getSent().setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
