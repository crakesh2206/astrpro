package com.rj.astro.fragments.frags;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rj.astro.R;
import com.rj.astro.activities.user.AddQuestion;


/**
 * Created by Codefingers-1 on 23-05-2017.
 */

public class Myrequest extends Fragment {
    Button btnRequest;


    public static Myrequest newInstance()
    {
        Myrequest f = new Myrequest();
        return (f);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View root = inflater.inflate(R.layout.freg_request,container,false);
              btnRequest = (Button)root.findViewById(R.id.btnAskRequset);
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),AddQuestion.class);
                startActivity(i);
            }
        });

        return root;
    }
}
