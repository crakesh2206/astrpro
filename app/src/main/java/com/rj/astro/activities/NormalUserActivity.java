package com.rj.astro.activities;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.materialdrawer.util.KeyboardUtil;
import com.rj.astro.R;
import com.rj.astro.frags.ContactUs;
import com.rj.astro.frags.Feedback;
import com.rj.astro.frags.InboxFragment;
import com.rj.astro.frags.Myrequest;

public class NormalUserActivity extends AppCompatActivity {
    Drawer result;
    private Toolbar toolbar;
    private FragmentManager fragmentManager;
    PrimaryDrawerItem itemMyrequest, itemInbox, itemFeedback, itemCOntactUs, itemRateUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_user);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        fragmentManager = getSupportFragmentManager();
//if you want to update the items at a later time it is recommended to keep it in a variable
        itemMyrequest = new PrimaryDrawerItem().withName("My Requests").withIcon(FontAwesome.Icon.faw_user_circle);
        itemInbox = new PrimaryDrawerItem().withName("Inbox").withIcon(FontAwesome.Icon.faw_envelope);
        itemFeedback = new PrimaryDrawerItem().withName("FeedBack & Support").withIcon(FontAwesome.Icon.faw_globe);
        itemCOntactUs = new PrimaryDrawerItem().withName("Contant Us").withIcon(FontAwesome.Icon.faw_contao);
        itemRateUs = new PrimaryDrawerItem().withName("Rate Us").withIcon(FontAwesome.Icon.faw_heart);
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
                        itemRateUs).withDisplayBelowStatusBar(true)
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
        result.updateItem(itemMyrequest.withBadge("14").withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700)));

    }
}
