package com.rj.astro.fragments.frags;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rj.astro.R;


/**
 * Created by Codefingers-1 on 23-05-2017.
 */

public class ContactUs extends Fragment {
    Button btnRequest;


    public static ContactUs newInstance()
    {
        ContactUs f = new ContactUs();
        return (f);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View root = inflater.inflate(R.layout.frag_contact,container,false);


        return root;
    }
}
