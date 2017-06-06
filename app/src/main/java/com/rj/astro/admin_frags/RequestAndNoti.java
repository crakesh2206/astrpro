package com.rj.astro.admin_frags;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.rj.astro.R;
import com.rj.astro.androidRecyclerView.AndroidVersion;
import com.rj.astro.androidRecyclerView.DataAdapter;

import java.util.ArrayList;

/**
 * Created by Codefingers-1 on 06-06-2017.
 */

public class RequestAndNoti extends Fragment {


    private RecyclerView mRecyclerView;
    private ArrayList<AndroidVersion> mArrayList;
    private DataAdapter mAdapter;
    public static RequestAndNoti newInstance()
    {
        RequestAndNoti f = new RequestAndNoti();
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

        View root = inflater.inflate(R.layout.ad_request_noti_frag,container,false);
          data();
        initViews(root);
        return root;
    }

    private void initViews(View root){
        mRecyclerView = (RecyclerView)root.findViewById(R.id.card_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new DataAdapter(mArrayList);
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

    public void data(){
        mArrayList = new ArrayList<>();
        mArrayList.add(new AndroidVersion("1.1","cupcake","api 3"));
        mArrayList.add(new AndroidVersion("2.1","GingerBread","api 4"));
        mArrayList.add(new AndroidVersion("3.1","HoneyComb","api 5"));
        mArrayList.add(new AndroidVersion("4.0","Ice creamSandwitch","api 6"));
        mArrayList.add(new AndroidVersion("5.1","Lolipop","api 21"));


    }
}
