package com.rj.astro.activities.user;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.materialdrawer.util.KeyboardUtil;
import com.rj.astro.R;
import com.rj.astro.activities.LoginActivity;
import com.rj.astro.databases.PrefManager;
import com.rj.astro.fragments.frags.ContactUs;
import com.rj.astro.fragments.frags.Feedback;
import com.rj.astro.fragments.frags.InboxFragment;
import com.rj.astro.fragments.frags.Myrequest;
import com.rj.astro.volly.AppController;
import com.rj.astro.volly.ConstantLinks;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NormalUserActivity extends AppCompatActivity {
    Drawer result;
    private Toolbar toolbar;
    private FragmentManager fragmentManager;
    PrimaryDrawerItem itemMyrequest, itemInbox, itemFeedback, itemCOntactUs, itemRateUs;
    private PrefManager pRef;
    private PrimaryDrawerItem itemSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_user);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        itemMyrequest = new PrimaryDrawerItem().withName("My Requests").withIcon(FontAwesome.Icon.faw_user_circle);
        itemInbox = new PrimaryDrawerItem().withName("Inbox").withIcon(FontAwesome.Icon.faw_envelope);
        itemFeedback = new PrimaryDrawerItem().withName("FeedBack & Support").withIcon(FontAwesome.Icon.faw_globe);
        itemCOntactUs = new PrimaryDrawerItem().withName("Contant Us").withIcon(FontAwesome.Icon.faw_contao);
        itemRateUs = new PrimaryDrawerItem().withName("Rate Us").withIcon(FontAwesome.Icon.faw_heart);
        itemSignOut = new PrimaryDrawerItem().withName("Sign Out").withIcon(FontAwesome.Icon.faw_power_off);
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName("Chat");

//create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar).withHeader(R.layout.nav_header_main)
                .addDrawerItems(
                        itemMyrequest,
                        itemInbox,
                        itemFeedback,
                        new DividerDrawerItem(),
                        itemCOntactUs,
                        itemRateUs,
                        itemSignOut).withDisplayBelowStatusBar(true)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        if (drawerItem != null && drawerItem instanceof Nameable) {

                            if (position == 1) {
                                toolbar.setTitle("My Requests");
                                Fragment f = Myrequest.newInstance();
                                fragmentManager.beginTransaction().replace(R.id.fragment_container, f).commit();

                            }

                            if (position == 2) {//inbox
                                toolbar.setTitle("Inbox");
                                Fragment f = InboxFragment.newInstance();
                                fragmentManager.beginTransaction().replace(R.id.fragment_container, f).commit();
                            }
                            if (position == 3) {
                                toolbar.setTitle("Feedbacks");
                                Fragment f = Feedback.newInstance();
                                fragmentManager.beginTransaction().replace(R.id.fragment_container, f).commit();

                            }
                            if (position == 5) {
                                toolbar.setTitle("Contact Us");
                                Fragment f = ContactUs.newInstance();
                                fragmentManager.beginTransaction().replace(R.id.fragment_container, f).commit();

                            }
                            if (position == 6) {

                                // TODO Auto-generated method stub
                                Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
                                startActivity(i);

                            }
                            //signout
                            if (position == 7) {

                                // TODO Auto-generated method stub
                                pRef.clear();
                                Intent i = new Intent(NormalUserActivity.this, LoginActivity.class);

                                startActivity(i);
                                finish();
                            }

                        }

                        return false;
                    }
                }).withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        KeyboardUtil.hideKeyboard(NormalUserActivity.this);
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {

                    }

                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {

                    }


                }).withFireOnInitialOnClick(true).withSavedInstance(savedInstanceState)
                .withSelectedItem(0).build();
      //  result.updateItem(itemMyrequest.withBadge("14").withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700)));

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
