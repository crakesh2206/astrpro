package com.rj.astro.admin_frags;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rj.astro.R;

/**
 * Created by Codefingers-1 on 06-06-2017.
 */

public class AdmInbox extends Fragment {

    public static AdmInbox newInstance()
    {
        AdmInbox f = new AdmInbox();
        return (f);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.ad_request_noti_frag,container,false);

        return root;
    }
}
