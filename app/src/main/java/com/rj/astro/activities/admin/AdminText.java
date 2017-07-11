package com.rj.astro.activities.admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.rj.astro.R;
import com.rj.astro.fragments.admin_frags.AdmInbox;

/**
 * Created by Codefingers-1 on 23-05-2017.
 */
public class AdminText extends AppCompatActivity{
    private Toolbar toolbar;
    AdmInbox admInbox;
    public static String uid;
   LinearLayout containerForFrag;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ask_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        String username = getIntent().getStringExtra("name");
        uid = getIntent().getStringExtra("uid");

        toolbar.setTitle(username);

         fm = getSupportFragmentManager();
         fragmentTransaction = fm.beginTransaction();

        AdmInbox adm = new AdmInbox();
        fragmentTransaction.add(R.id.cont,adm,"inbox");
        fragmentTransaction.commit();


    }
}
