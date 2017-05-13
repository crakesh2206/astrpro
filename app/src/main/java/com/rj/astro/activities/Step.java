package com.rj.astro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.rj.astro.databases.PrefManager;

/**
 * Created by Codefingers-1 on 12-05-2017.
 */

public class Step extends AppCompatActivity {
    PrefManager prefs;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(android.R.layout.test_list_item);
        prefs = new PrefManager(this);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(prefs.isLogedIn()){
           //which type of user is avail admin/user
            if(prefs.isUserisAdmin()){
                //go to admin
                Intent i = new Intent(this,AdminPanelActivity.class);
                startActivity(i);
                finish();

            }else{
                // go to normal user
                Intent i = new Intent(this,NormalUserActivity.class);
                startActivity(i);
                finish();


            }





        }else{
            //go to login Activity
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
            finish();
        }
    }
}
