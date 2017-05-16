package com.rj.astro.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.rj.astro.R;
import com.rj.astro.volly.AppController;
import com.rj.astro.volly.ConstantLinks;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance;

public class RegistrationActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener {
    private static final String ST_TAG = "register";
    private Toolbar toolbar;
    private EditText inputName, inputEmail, inputPassword,inputConfirmPassword,inputDate,inputTime;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword ,inputLayoutConfirmPassword,inputLayoutDate,inputLayoutTime;
    private Button btnSignUp;
    private EditText inputMobile;
    private TextInputLayout inputLayoutMobile;
    TextView tvLinktoLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLinktoLogin = (TextView) findViewById(R.id.link_to_login);
        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutMobile = (TextInputLayout) findViewById(R.id.input_layout_mobile);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputLayoutConfirmPassword = (TextInputLayout) findViewById(R.id.input_layout_conf_password);
        inputLayoutDate = (TextInputLayout) findViewById(R.id.input_layout_date);
        inputLayoutTime = (TextInputLayout) findViewById(R.id.input_layout_time);
        inputName = (EditText) findViewById(R.id.input_name);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputMobile = (EditText)findViewById(R.id.input_mobile);
        inputPassword = (EditText) findViewById(R.id.input_password);
        inputConfirmPassword = (EditText) findViewById(R.id.input_conf_password);
        inputDate = (EditText) findViewById(R.id.input_date);
        inputTime = (EditText) findViewById(R.id.input_time);
        btnSignUp = (Button) findViewById(R.id.btn_signup);


        inputDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        inputTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showTimePicker();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
        tvLinktoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });


    }
    private void submitForm() {
//
      String name =  inputName.getText().toString().trim();
      String  email =  inputEmail.getText().toString().trim();
      String password =  inputPassword.getText().toString().trim();
       String mobile =  inputMobile.getText().toString().trim();
       String date =  inputDate.getText().toString().trim();
       String time =  inputTime.getText().toString().trim();
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
        if (!validatePassword()) {
            return;
        }
        if (!validateConfirmPassword()) {
            return;
        }
        if (!validateDateTime()) {
            return;
        }
         if(name.equals("admin")){
             Intent i = new Intent(this,AdminPanelActivity.class);
             startActivity(i);
         }else{
             Intent i = new Intent(this,NormalUserActivity.class);
             startActivity(i);
         }


         // sendDataToServer(name,email,password,usertype,token,gender,date,time);
        Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
    }

    private boolean validateDateTime() {
        if (inputDate.getText().toString().trim().isEmpty()) {
            inputLayoutDate.setError(getString(R.string.err_msg_date));
            requestFocus(inputDate);
            return false;
        } else {
            inputLayoutDate.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateConfirmPassword() {
        if (!inputPassword.getText().toString().equals(inputConfirmPassword.getText().toString())) {
            inputLayoutConfirmPassword.setError(getString(R.string.err_msg_conf_pass));
            requestFocus(inputConfirmPassword);
            return false;
        } else {
            inputLayoutConfirmPassword.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
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

    private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputPassword);
            return false;
        } else {

            if(inputPassword.getText().toString().length()<=5){
                inputLayoutPassword.setError(getString(R.string.err_msg_passwordlenth));
            }

            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }
  public void showDatePicker(){
      Calendar now = Calendar.getInstance();
      DatePickerDialog dpd = newInstance(
              RegistrationActivity.this,
              now.get(Calendar.YEAR),
              now.get(Calendar.MONTH),
              now.get(Calendar.DAY_OF_MONTH)
      );
      dpd.show(getFragmentManager(), "Datepickerdialog");
  }

    public void showTimePicker(){
        Calendar now = Calendar.getInstance();
        TimePickerDialog dpd = TimePickerDialog.newInstance(
                RegistrationActivity.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                now.get(Calendar.SECOND),false
        );
        dpd.show(getFragmentManager(), "Timepickerdialog");
    }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        inputDate.setText(date);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String time = hourOfDay+":"+minute;
        inputTime.setText(time);
    }
    public void sendDataToServer(final String name,final String email, final String password, final String usertype, final String token, final String gender, String dob, String dot){

        // Tag used to cancel the request
        String  tag_string_req = "string_req";

        String url = "http://api.androidhive.info/volley/string_response.html";

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.GET,
                ConstantLinks.REGISTER_USER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(ST_TAG, response.toString());
                pDialog.hide();

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
                params.put("username",name);
                params.put("email",email);
                params.put("password", password);
                params.put("usertype", usertype);
                params.put("gender",gender );
                params.put("devicetoken", token);
               // Further Changes HERE need

                return params;
            }};

       // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }



    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_name:
                    validateName();
                    break;
                case R.id.input_email:
                    validateEmail();
                    break;
                case R.id.input_password:
                    validatePassword();
                    break;
            }
        }
    }
}
