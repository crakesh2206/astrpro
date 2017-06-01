package com.rj.astro.frags;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rj.astro.R;
import com.rj.astro.androidRecyclerView.Message;
import com.rj.astro.androidRecyclerView.MessageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Codefingers-1 on 01-06-2017.
 */

public class InboxFragment extends Fragment{
    private static final long FIVE_SECONDS = 5000;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private MessageAdapter mAdapter;
    private List<Message> messageList;
    private Handler handler;


    public static InboxFragment newInstance()
    {
        InboxFragment f = new InboxFragment();
        return (f);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frag_inbox,container,false);
        messageList = new ArrayList<>();
        mRecyclerView = (RecyclerView) root.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MessageAdapter(getActivity(), messageList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
         generatedummy();


        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
         handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {

                handler.postDelayed(new Runnable() {
                    public void run() {
                        generatedummy();
                        handler.postDelayed(this, FIVE_SECONDS);
                    }
                }, FIVE_SECONDS);


            }
        }).start();


    }

    private void generatedummy() {
       Message msg1 = new Message(1,"public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {\n" +
               "        View root = inflater.inflate(R.layout.frag_inbox,container,false);","1 june 2017","user");
//        Message msg2 = new Message(2,"fine and you","1 june 2017","admin");
//        Message msg3 = new Message(3,"welcome dear","1 june 2017","user");
//        Message msg4 = new Message(4,"what are you doing","1 june 2017","user");
        Message msg5 = new Message(5,"1 june 2017 Nothing just typing 1 june 2017 Nothing just typing 1 june 2017 Nothing just typing1 june 2017 Nothing just typing 1 june 2017 Nothing just typing","1 june 2017","admin");

        messageList.add(msg1);
//        messageList.add(msg2);
//        messageList.add(msg3);
//        messageList.add(msg4);
        messageList.add(msg5);
        mAdapter.notifyDataSetChanged();

    }
}
