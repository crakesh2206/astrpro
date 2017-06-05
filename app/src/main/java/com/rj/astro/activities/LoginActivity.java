package com.rj.astro.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.rj.astro.R;
import com.rj.astro.databases.PrefManager;
import com.rj.astro.volly.AppController;
import com.rj.astro.volly.ConstantLinks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Codefingers-1 on 17-05-2017.
 */

public class LoginActivity extends AppCompatActivity {

    private static final String ST_TAG = "login";
    private CheckBox mCbShowPwd;
    EditText etUsername,etPassword;
    Button btnLogin;
    TextView tvToReg;
    private ProgressDialog pDialog;
    private PrefManager pRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

       pDialog = new ProgressDialog(LoginActivity.this);
        pDialog.setMessage("Loading...");

        pRef = new PrefManager(this);
        btnLogin = (Button)findViewById(R.id.buttonLogin);
        tvToReg = (TextView)findViewById(R.id.link_to_register);
        etUsername = (EditText)findViewById(R.id.editTextuser);
        etPassword = (EditText)findViewById(R.id.editTextPassword);
        // get the show/hide password Checkbox
        mCbShowPwd = (CheckBox) findViewById(R.id.cbShowPwd);
        // when user clicks on this checkbox, this is the handler.
        mCbShowPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // checkbox status is changed from uncheck to checked.
                if (!isChecked) {
                    // show password
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = FirebaseInstanceId.getInstance().getToken();

                pRef.setToken(token);
               String userName= etUsername.getText().toString().trim();
               String password = etPassword.getText().toString().trim();
               String tok = pRef.getToken();
                if(!userName.isEmpty() && !password.isEmpty() && tok != null){
                     sendDataToServer(userName,password);
                }else{
                    Toast.makeText(LoginActivity.this,"Please fill Details",Toast.LENGTH_SHORT).show();
                }
        tvToReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });




            }
        });

    }
     public void sendDataToServer(final String username,final String password){

        // Tag used to cancel the request
        String  tag_string_req = "string_req_TeacherLogin";

        pDialog.show();

        StringRequest strReqTeacherLogin = new StringRequest(Request.Method.POST,
                ConstantLinks.LOGIN_USER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(ST_TAG, response.toString());
                pDialog.hide();
                JSONArray jsonArray = null;
                try {
                    JSONObject obj = new JSONObject(response);
                   if(!obj.has("error")){

                       jsonArray = obj.getJSONArray("result");
                       for(int i=0; i<jsonArray.length(); i++){
                           JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                           String sUserId = jsonObject.getString("id");
                           String sUserName = jsonObject.getString("name");
                           String sEmail = jsonObject.getString("email");
                           String sMobile = jsonObject.getString("mobile");
                           String sUsertype = jsonObject.getString("usertype");
                           String sGender = jsonObject.getString("gender");
                           String sDob = jsonObject.getString("dob");
                           String sDot = jsonObject.getString("dot");
                           String sBirthplace = jsonObject.getString("birthplace");

                           if(sUsertype.equals("admin")){
                               pRef.setUserIsAdmin(true);
                           }else{
                               pRef.setUserIsAdmin(false);
                               pRef.setUserId(sUserId);
                               pRef.setUserName(sUserName);
                               pRef.setUserEmail(sEmail);
                               pRef.setUserMobile(sMobile);
                               pRef.setGender(sGender);
                               pRef.setDOB(sDob);
                               pRef.setDot(sDot);
                               pRef.setBirthPlace(sBirthplace);
                           }



                       }

                           pRef.setLogIn(true);

                          if(pRef.isUserisAdmin()){
                              Intent intent = new Intent(LoginActivity.this,AdminPanelActivity.class);
                              startActivity(intent);
                              finish();
                          }else{



                           Intent intent = new Intent(LoginActivity.this,NormalUserActivity.class);
                           startActivity(intent);
                           finish();
                          }


                   }



                } catch (JSONException e) {

                }




            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(ST_TAG, "Error: " + error.getMessage());
                pDialog.hide();
            }
        }){

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email",username);
                params.put("password", password);
                params.put("token", pRef.getToken());


                return params;
            }};

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReqTeacherLogin, tag_string_req);

    }

}
