package com.rj.astro.admin_frags;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rj.astro.R;
import com.rj.astro.androidRecyclerView.UserData;
import com.rj.astro.androidRecyclerView.UserListDataAdapter;
import com.rj.astro.volly.AppController;
import com.rj.astro.volly.ConstantLinks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Codefingers-1 on 06-06-2017.
 */

public class AllUsers extends Fragment {


    private RecyclerView mRecyclerView;
    private ArrayList<UserData> mArrayList;
    private UserListDataAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static AllUsers newInstance()
    {
        AllUsers f = new AllUsers();
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
                ConstantLinks.FEEDBACK_ALL_USERS,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String error =  response.getString("error");

                    if(error.equals("false")){

                        JSONArray resType =  response.getJSONArray("users");
                        for (int i =0;i<resType.length();i++){
                              JSONObject obj = resType.getJSONObject(i);
                            UserData userData = new UserData();
                            userData.setUser_id(obj.getString("user_id"));
                            userData.setUsername(obj.getString("name"));
                            userData.setEmail(obj.getString("email"));
                            userData.setMobile(obj.getString("mobile"));
                            userData.setDob(obj.getString("dob"));
                            userData.setDot(obj.getString("dot"));
                            userData.setDevicetoken(obj.getString("devicetoken"));
                            userData.setUsertype(obj.getString("usertype"));
                            userData.setBirthplace(obj.getString("birthplace"));
                            userData.setGender(obj.getString("gender"));
                            mArrayList.add(userData);
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
        mAdapter = new UserListDataAdapter(mArrayList,getActivity());
        mRecyclerView.setAdapter(mAdapter);

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


}
