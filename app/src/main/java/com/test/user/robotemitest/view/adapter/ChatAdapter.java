package com.test.user.robotemitest.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.test.user.robotemitest.R;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private List<ChatMessage> mMessageList;
    private Context mContext;

    public ChatAdapter(List<ChatMessage> messageList,Context context) {
        mMessageList = messageList;
        mContext= context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView message;

        public MyViewHolder(View view) {
            super(view);
            message = view.findViewById(R.id.message);
        }
    }

    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.chat_item, parent, false);
        return new ChatAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChatAdapter.MyViewHolder holder, int position) {
        ChatMessage chatMessage = mMessageList.get(position);
        holder.message.setText(chatMessage.getMessage());
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

}
