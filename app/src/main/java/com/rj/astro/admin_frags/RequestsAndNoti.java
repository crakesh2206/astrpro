package com.rj.astro.admin_frags;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rj.astro.R;
import com.rj.astro.activities.AskRequset;
import com.rj.astro.androidRecyclerView.RequestsAdapter;
import com.rj.astro.androidRecyclerView.RequestsAndNotiModel;
import com.rj.astro.volly.AppController;
import com.rj.astro.volly.ConstantLinks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Codefingers-1 on 06-06-2017.
 */

public class RequestsAndNoti extends Fragment {


    private RecyclerView mRecyclerView;
    private ArrayList<RequestsAndNotiModel> mArrayList;
    private RequestsAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static RequestsAndNoti newInstance()
    {
        RequestsAndNoti f = new RequestsAndNoti();
        return (f);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.ad_feedback,container,false);

        mArrayList = new ArrayList<>();
        initViews(root);

        mSwipeRefreshLayout.setRefreshing(true);
        sendDataToServer();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sendDataToServer();
            }


        });

        return root;
    }

    public void sendDataToServer(){


        // Tag used to cancel the request
        String  obj_req = "obj_req";

        JsonObjectRequest objJsonreq = new JsonObjectRequest(Request.Method.POST,
                ConstantLinks.REQUESTS_ALL,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String error =  response.getString("error");

                    if(error.equals("false")){

                        JSONArray resType =  response.getJSONArray("questions");
                        for (int i =0;i<resType.length();i++){
                            JSONObject obj = resType.getJSONObject(i);
                            RequestsAndNotiModel reqModelNoti = new RequestsAndNotiModel();
                            reqModelNoti.setUsername(obj.getString("username"));
                            reqModelNoti.setQuestion(obj.getString("ques"));
                            reqModelNoti.setUser_id(obj.getString("user_id"));
                            mArrayList.add(reqModelNoti);
                        }




                    }else{
                        Toast.makeText(getActivity(),"error",Toast.LENGTH_SHORT).show();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                mAdapter.notifyDataSetChanged();


                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(objJsonreq, obj_req);

    }

    private void initViews(View root){
        mRecyclerView = (RecyclerView)root.findViewById(R.id.card_recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.activity_main_swipe_refresh_layout);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RequestsAdapter(mArrayList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                RequestsAndNotiModel temp =mArrayList.get(position);
                Intent x = new Intent(getActivity(), AskRequset.class);
                x.putExtra("name",temp.getUsername());
                x.putExtra("uid",temp.getUser_id());
                startActivity(x);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_noti, menu);

        MenuItem search = menu.findItem(R.id.search);

        super.onCreateOptionsMenu(menu, inflater);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                mAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }
    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private RequestsAndNoti.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final RequestsAndNoti.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}
