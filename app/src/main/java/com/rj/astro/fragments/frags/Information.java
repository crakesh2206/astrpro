package com.rj.astro.fragments.frags;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rj.astro.R;

/**
 * Created by Rakesh on 18/05/2017.
 */

public class Information extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

                  View root = inflater.inflate(R.layout.frg,container,false);

        return root;
    }



}
