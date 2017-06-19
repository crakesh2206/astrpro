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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rj.astro.R;
import com.rj.astro.androidRecyclerView.MessageAdapter;
import com.rj.astro.databases.DbHelper;
import com.rj.astro.databases.Questions;
import com.rj.astro.volly.AppController;
import com.rj.astro.volly.ConstantLinks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private List<Questions> messageList;
    private Handler handler;
    private DbHelper dbHelper;


    public static InboxFragment newInstance()
    {
        InboxFragment f = new InboxFragment();
        return (f);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DbHelper(getActivity());
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
        sendDataToServer();
      //  generatedummy();

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());



        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
//         handler = new Handler();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                handler.postDelayed(new Runnable() {
//                    public void run() {
//                       generatedummy();
//                        handler.postDelayed(this, FIVE_SECONDS);
//                    }
//                }, FIVE_SECONDS);
//
//
//            }
//        }).start();


    }

    private void generatedummy() {
//       Message msg1 = new Message(1,"public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {\n" +
//               "        View root = inflater.inflate(R.layout.frag_inbox,container,false);","1 june 2017","user");
////        Message msg2 = new Message(2,"fine and you","1 june 2017","admin");
////        Message msg3 = new Message(3,"welcome dear","1 june 2017","user");
////        Message msg4 = new Message(4,"what are you doing","1 june 2017","user");
//        Message msg5 = new Message(5,"1 june 2017 Nothing just typing 1 june 2017 Nothing just typing 1 june 2017 Nothing just typing1 june 2017 Nothing just typing 1 june 2017 Nothing just typing","1 june 2017","admin");
//
//        messageList.add(msg1);
////        messageList.add(msg2);
////        messageList.add(msg3);
////        messageList.add(msg4);
//        messageList.add(msg5);
//
//
        messageList = dbHelper.getAllQuestions(32);
        mAdapter.notifyDataSetChanged();

    }

    public void sendDataToServer(){


        // Tag used to cancel the request
        String  obj_req = "obj_req";

        JsonObjectRequest objJsonreq = new JsonObjectRequest(Request.Method.POST,
                ConstantLinks.USER_ALL_QUESTIONS,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String error =  response.getString("error");

                    if(error.equals("false")){

                        JSONArray resType =  response.getJSONArray("questions");
                        for (int i =0;i<resType.length();i++){
                            JSONObject obj = resType.getJSONObject(i);
                          Questions ques = new Questions();
                            ques.KEY_QID = obj.getString("qid");
                            ques.KEY_CATAGORY = obj.getString("cat");
                            ques.KEY_SUB_CATAGORY = obj.getString("sub_cat");
                            ques.KEY_QUESTION = obj.getString("ques");
                             ques.KEY_TIME = obj.getString("tm");
                            ques.KEY_USER_ID = obj.getString("user_id");
                              ques.KEY_USERTYPE = obj.getString("usertype");
                            ques.KEY_TOWHO = obj.getString("towho");
                           dbHelper.createQUESTION(ques);


                        }

                        messageList = dbHelper.getAllQuestions(32);
                        mAdapter.notifyDataSetChanged();

                    }else{
                        Toast.makeText(getActivity(),"error",Toast.LENGTH_SHORT).show();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                mAdapter.notifyDataSetChanged();



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(objJsonreq, obj_req);

    }
}
