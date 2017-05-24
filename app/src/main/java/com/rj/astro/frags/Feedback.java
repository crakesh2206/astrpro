package com.rj.astro.frags;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rj.astro.R;
import com.rj.astro.databases.PrefManager;
import com.rj.astro.volly.AppController;
import com.rj.astro.volly.ConstantLinks;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Codefingers-1 on 24-05-2017.
 */

public class Feedback extends Fragment {


    private EditText inputEmail;
    private TextInputLayout inputLayoutEmail;
    private EditText inputMobile;
    private TextInputLayout inputLayoutMobile;
    private EditText inputName;
    private TextInputLayout inputLayoutName;
    private TextInputLayout inputLayoutFeedback;
    private EditText inputFeedback;
    Button btnSubmit;
    private PrefManager pref;

    public static Feedback newInstance()
    {
        Feedback f = new Feedback();
        return (f);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frag_feedback, container, false);
        pref = new PrefManager(getActivity());
        inputLayoutName = (TextInputLayout) root.findViewById(R.id.input_layout_uname);
        inputLayoutEmail = (TextInputLayout) root.findViewById(R.id.input_layout_email);
        inputLayoutMobile = (TextInputLayout) root.findViewById(R.id.input_layout_mobile);
        inputLayoutFeedback = (TextInputLayout) root.findViewById(R.id.input_layout_feedback);
        inputName = (EditText) root.findViewById(R.id.input_uname);
        inputEmail = (EditText) root.findViewById(R.id.input_email);
        inputMobile = (EditText)root.findViewById(R.id.input_mobile);
        inputFeedback = (EditText) root.findViewById(R.id.input_feedback);

        btnSubmit = (Button)root.findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });


        return root;
    }

    private void submitForm() {
//
        String name = inputName.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();
        String mobile = inputMobile.getText().toString().trim();
        String feedBack = inputFeedback.getText().toString().trim();


        String usertype = "user";
        if (!validateName()) {
            return;
        }

        if (!validateEmail()) {
            return;
        }

        if (!validateMobile()) {
            return;
        }
        if (!validateFeedback()) {
            return;
        }

        sendDataToServer(name, email,mobile, feedBack, usertype,pref.getUserId());





    }
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }
    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateMobile() {
        String mobile = inputMobile.getText().toString().trim();

        if (mobile.isEmpty() || !isValidMobile(mobile)) {
            inputLayoutMobile.setError(getString(R.string.err_msg_mobile));
            requestFocus(inputMobile);
            return false;
        } else {
            inputLayoutMobile.setErrorEnabled(false);
        }

        return true;
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    private boolean validateName() {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(inputName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateFeedback() {
        if (inputFeedback.getText().toString().trim().isEmpty()) {
            inputFeedback.setError(getString(R.string.err_msg_name));
            requestFocus(inputFeedback);
            return false;
        } else {
            inputLayoutFeedback.setErrorEnabled(false);

        }

        return true;
    }


    public void sendDataToServer(final String name, final String email,String mobile,final String feedback,String usertype, final String userid){

        // Tag used to cancel the request
        String  obj_req = "obj_req";



        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();



        JSONObject params = new JSONObject();
        try {
            params.put("name",name);
            params.put("email",email);
            params.put("mobile",mobile);
            params.put("feedback", feedback);
            params.put("usertype", usertype);
            params.put("userid",userid);


        } catch (JSONException e) {
            e.printStackTrace();
        }





        JsonObjectRequest objJsonreq = new JsonObjectRequest(Request.Method.POST,
                ConstantLinks.FEEDBACK_USER, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String error =  response.getString("error");
                    String resMessage =  response.getString("message");
                    if(error.equals("true")){

                        String resType =  response.getString("type");




                        if(resType.equals("opps") || resType.equals("unknown")){
                            Toast.makeText(getActivity(),resMessage,Toast.LENGTH_SHORT).show();
                        }





                    }else{
                        Toast.makeText(getActivity(),resMessage,Toast.LENGTH_SHORT).show();



                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }





                pDialog.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.hide();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(objJsonreq, obj_req);

    }



}
