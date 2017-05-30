package com.rj.astro.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.rj.astro.R;
import com.rj.astro.databases.PrefManager;
import com.rj.astro.volly.AppController;
import com.rj.astro.volly.ConstantLinks;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.rj.astro.R.id.btnCancle;


/**
 * Created by Codefingers-1 on 30-05-2017.
 */

public class Add extends AppCompatActivity {
    private Spinner spinCatagory;
    private Spinner spinSubCatagory;
    private ArrayList<String> catagoryList;
    private ArrayList<String> subCatagorylist;
    private ArrayAdapter<String> catagoryAdapter;
    private ArrayAdapter<String> subCatagoryAdapter;
    EditText etQuestion;
    private Button btncancle;
    private Button btnSub;
    private PrefManager pRef;
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addfrag);
        spinCatagory = (Spinner) findViewById(R.id.spinnerCatagory);
        spinSubCatagory = (Spinner) findViewById(R.id.spinnerSubCatagory);
        etQuestion = (EditText)findViewById(R.id.etques);
        btnSub = (Button) findViewById(R.id.btnsub);
        btncancle = (Button) findViewById(btnCancle);
        pRef = new PrefManager(this);
        // Spinner Drop down elements
        catagoryList = new ArrayList<String>();
        catagoryList.add("Select Catagory");

        subCatagorylist = new ArrayList<String>();
        subCatagorylist.add("Select Subcatagory");

        pDialog = new ProgressDialog(Add.this);
        pDialog.setMessage("Loading...");

        // Creating adapter for spinner
        catagoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, catagoryList);
        subCatagoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subCatagorylist);

        // Drop down layout style - list view with radio button
        catagoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subCatagoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinCatagory.setAdapter(catagoryAdapter);
        spinSubCatagory.setAdapter(subCatagoryAdapter);

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String catagory = spinCatagory.getSelectedItem().toString();
                String subCatagory = spinSubCatagory.getSelectedItem().toString();
                String ques = etQuestion.getText().toString();

                sentToServer(catagory,subCatagory,ques,pRef.getUserId(),setCreated(),"user",pRef.getUserName());




            }
        });

        btncancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void sentToServer(String catagory, String subCatagory, final String ques, final String userId, final String time, final String usertype, final String userName) {
        // Tag used to cancel the request
        String  tag_sendmsg = "strMSG";

        pDialog.show();

        StringRequest strReqTeacherLogin = new StringRequest(Request.Method.POST,
                ConstantLinks.SINGLE_SEND_USER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                pDialog.hide();
                JSONArray jsonArray = null;



            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("eerror", "Error: " + error.getMessage());
                pDialog.hide();
            }
        }){

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ques",ques);
                params.put("user_id",userId);
                params.put("time",time);
                params.put("usertype",usertype);
                params.put("username",userName);
                params.put("towho","35");

                return params;
            }};

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReqTeacherLogin, tag_sendmsg);

    }

    public static String setCreated(){
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        System.out.println(strDate);
        return strDate;
    }
}
