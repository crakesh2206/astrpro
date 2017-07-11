package com.rj.astro.androidRecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rj.astro.R;
import com.rj.astro.databases.Questions;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private final boolean isAdmin;
    private List<Questions> messageList;

    public static final int SENDER = 0;
    public static final int RECIPIENT = 1;

    public MessageAdapter(Context context, List<Questions> messages,boolean ustype) {
        messageList = messages;
        this.isAdmin = ustype;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public ViewHolder(LinearLayout v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.text);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.final_left, parent, false);
            ViewHolder vh = new ViewHolder((LinearLayout) v);
            return vh;
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.final_right, parent, false);
            ViewHolder vh = new ViewHolder((LinearLayout) v);
            return vh;
        }
    }

    public void remove(int pos) {
        int position = pos;
        messageList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, messageList.size());

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mTextView.setText(messageList.get(position).KEY_QUESTION);
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Questions message = messageList.get(position);
        int typs = 0;
        if(isAdmin){
            if (message.KEY_USERTYPE.equals("admin")) {
                typs = SENDER;
            } else {
                typs = RECIPIENT;
            }
        }else {
            if (message.KEY_USERTYPE.equals("user")) {
                typs = SENDER;
            } else {
                typs = RECIPIENT;
            }
        }


       return typs;

    }

}