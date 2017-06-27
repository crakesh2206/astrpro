package com.rj.astro.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.materialdrawer.util.KeyboardUtil;
import com.rj.astro.R;
import com.rj.astro.admin_frags.AllFeedBacks;
import com.rj.astro.admin_frags.AllUsers;
import com.rj.astro.admin_frags.RequestsAndNoti;
import com.rj.astro.databases.PrefManager;
import com.rj.astro.volly.AppController;
import com.rj.astro.volly.ConstantLinks;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AdminPanelActivity extends AppCompatActivity {

    private Toolbar toolbar;
    LayoutInflater inflater;
    private PrimaryDrawerItem itemRequestNotificaton;
    private PrimaryDrawerItem itemFeedbackS;
    private PrimaryDrawerItem itemInboxs;
    private PrimaryDrawerItem itemUserlist;
    private PrimaryDrawerItem itemSignOut;
    private FragmentManager fragmentManager;
    private PrefManager pRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String from = getIntent().getStringExtra("from");
        pRef = new PrefManager(this);
        if(from.equals("login")){

            final String toks = pRef.getToken();
            if(toks != null) {
                   updateToken(toks);
            }

        }else{
            if(!pRef.isTokenSent()){
                final String tk = pRef.getToken();
                if(tk != null) {
                    updateToken(tk);
                }
            }
        }







        fragmentManager = getSupportFragmentManager();
//if you want to update the items at a later time it is recommended to keep it in a variable

        itemRequestNotificaton = new PrimaryDrawerItem().withName("Requests & Notification").withIcon(FontAwesome.Icon.faw_user_circle);

        itemFeedbackS = new PrimaryDrawerItem().withName("FeedBacks").withIcon(FontAwesome.Icon.faw_globe);
        itemUserlist = new PrimaryDrawerItem().withName("Users List").withIcon(FontAwesome.Icon.faw_users);
        itemSignOut = new PrimaryDrawerItem().withName("Sign Out").withIcon(FontAwesome.Icon.faw_power_off);


        inflater = getLayoutInflater();
//create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar).withHeader(R.layout.nav_header_main)
                .addDrawerItems(
                        itemRequestNotificaton,
                        itemFeedbackS,
                        itemUserlist,
                        new DividerDrawerItem(),
                        itemSignOut
                ).withDisplayBelowStatusBar(true)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        if (drawerItem != null && drawerItem instanceof Nameable) {

                            if (position == 1) {
                                toolbar.setTitle("Requests & Notifications");
                                Fragment f = RequestsAndNoti.newInstance();
                                fragmentManager.beginTransaction().replace(R.id.fragment_container, f).commit();

                            }
                            if (position == 2) {
                                toolbar.setTitle("FeedBacks");
                                Fragment f = AllFeedBacks.newInstance();
                                fragmentManager.beginTransaction().replace(R.id.fragment_container, f).commit();

                            }

                            if (position == 3) {
                                toolbar.setTitle("UserList");
                                Fragment f = AllUsers.newInstance();
                                fragmentManager.beginTransaction().replace(R.id.fragment_container, f).commit();

                            }
                            //signout
                            if (position == 5) {

                                // TODO Auto-generated method stub
                                pRef.clear();
                                Intent i = new Intent(AdminPanelActivity.this, LoginActivity.class);

                                startActivity(i);
                                finish();
                            }
                        }

                        return false;
                    }
                }).withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        KeyboardUtil.hideKeyboard(AdminPanelActivity.this);
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {

                    }

                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {

                    }


                }).withFireOnInitialOnClick(true).withSavedInstance(savedInstanceState)
                .withSelectedItem(0).build();

        View ve = inflater.inflate(R.layout.nav_header_main, null);
        ImageView im = (ImageView) ve.findViewById(R.id.img);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminPanelActivity.this, "fdfd", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void updateToken(final String tok){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstantLinks.SET_TOKEN,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean error = jsonObject.getBoolean("error");
                                    if(error== false){
                                        pRef.setTokenSent(true);

                                    }




                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {

                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("email", pRef.getUserEmail());
                        params.put("token", tok);


                        return params;
                    }
                };

                // Adding request to request queue
                AppController.getInstance().addToRequestQueue(stringRequest,"req");

    }
}
