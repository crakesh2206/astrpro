package com.rj.astro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.rj.astro.R;
import com.rj.astro.databases.PrefManager;

/**
 * Created by Codefingers-1 on 12-05-2017.
 */

public class Step extends AppCompatActivity {
    private static final long SPLASH_TIME_OUT = 3000;
    PrefManager prefs;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slps);
        prefs = new PrefManager(this);


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if(prefs.isLogedIn()){
                    //which type of user is avail admin/user
                    if(prefs.isUserisAdmin()){
                        //go to admin
                        Intent i = new Intent(Step.this,AdminPanelActivity.class);
                        startActivity(i);
                        finish();

                    }else{
                        // go to normal user
                        Intent i = new Intent(Step.this,NormalUserActivity.class);
                        startActivity(i);
                        finish();


                    }





                }else{
                    //go to login Activity
                    Intent i = new Intent(Step.this,RegistrationActivity.class);
                    startActivity(i);
                    finish();
                }
            }

        }, SPLASH_TIME_OUT);
    }





}
