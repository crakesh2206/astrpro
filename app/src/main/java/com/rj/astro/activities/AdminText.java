package com.rj.astro.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.rj.astro.R;

/**
 * Created by Codefingers-1 on 23-05-2017.
 */
public class AdminText extends AppCompatActivity{
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ask_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
       String username = getIntent().getStringExtra("name");
        String uid = getIntent().getStringExtra("uid");
        toolbar.setTitle(username);

    }
}
