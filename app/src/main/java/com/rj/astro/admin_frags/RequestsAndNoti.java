package com.rj.astro.admin_frags;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rj.astro.R;

/**
 * Created by Codefingers-1 on 07-06-2017.
 */

public class RequestsAndNoti extends Fragment {
    public static RequestsAndNoti newInstance()
    {
        RequestsAndNoti f = new RequestsAndNoti();
        return (f);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.frag_request_noti,container,false);

        return root;
    }
}
