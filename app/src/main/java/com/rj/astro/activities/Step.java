package com.rj.astro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.rj.astro.R;
import com.rj.astro.activities.admin.AdminPanelActivity;
import com.rj.astro.activities.user.NormalUserActivity;
import com.rj.astro.databases.DbHelper;
import com.rj.astro.databases.PrefManager;
import com.rj.astro.databases.Questions;

/**
 * Created by Codefingers-1 on 12-05-2017.
 */

public class Step extends AppCompatActivity {
    private static final long SPLASH_TIME_OUT = 3000;
    PrefManager prefs;
    DbHelper DbHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slps);
        prefs = new PrefManager(this);
        DbHelper = new DbHelper(this);



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
                        i.putExtra("from","step");
                        startActivity(i);
                        finish();

                    }else{
                        // go to normal user
                        Questions qs = new Questions();
                        qs.KEY_QID = "1";
                        qs.KEY_CATAGORY = "x";
                        qs.KEY_SUB_CATAGORY = "y";
                        qs.KEY_QUESTION = "how are you dear";
                        qs.KEY_TIME = "2017-05-05";
                        qs.KEY_USER_ID = "1";
                        qs.KEY_USERTYPE = "user";
                        qs.KEY_TOWHO = "admin";

                        Questions qs1 = new Questions();
                        qs1.KEY_QID = "2";
                        qs1.KEY_CATAGORY = "x";
                        qs1.KEY_SUB_CATAGORY = "y";
                        qs1.KEY_QUESTION = "how are you dear";
                        qs1.KEY_TIME = "2017-05-05";
                        qs1.KEY_USER_ID = "1";
                        qs1.KEY_USERTYPE = "admin";
                        qs1.KEY_TOWHO = "admin";

                        Questions qs2 = new Questions();
                        qs2.KEY_QID = "3";
                        qs2.KEY_CATAGORY = "x";
                        qs2.KEY_SUB_CATAGORY = "y";
                        qs2.KEY_QUESTION = "how are you dear";
                        qs2.KEY_TIME = "2017-05-05";
                        qs2.KEY_USER_ID = "1";
                        qs2.KEY_USERTYPE = "admin";
                        qs2.KEY_TOWHO = "admin";

                        Questions qs3 = new Questions();
                        qs3.KEY_QID = "4";
                        qs3.KEY_CATAGORY = "x";
                        qs3.KEY_SUB_CATAGORY = "y";
                        qs3.KEY_QUESTION = "how are you dear";
                        qs3.KEY_TIME = "2017-05-05";
                        qs3.KEY_USER_ID = "1";
                        qs3.KEY_USERTYPE = "admin";
                        qs3.KEY_TOWHO = "admin";

                        DbHelper.createQUESTION(qs);
                        DbHelper.createQUESTION(qs1);
                        DbHelper.createQUESTION(qs2);
                        DbHelper.createQUESTION(qs3);

                        Intent i = new Intent(Step.this,NormalUserActivity.class);
                        i.putExtra("from","step");
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
