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
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rj.astro.R;
import com.rj.astro.databases.PrefManager;
import com.rj.astro.volly.AppController;
import com.rj.astro.volly.ConstantLinks;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import static com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance;

public class RegistrationActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener {
    private static final String ST_TAG = "register";
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    private Toolbar toolbar;
    private EditText inputName, inputEmail, inputPassword,inputConfirmPassword,inputDate,inputTime;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword ,inputLayoutConfirmPassword,inputLayoutDate,inputLayoutTime;
    private Button btnSignUp;
    private EditText inputMobile;
    private TextInputLayout inputLayoutMobile;
    TextView tvLinktoLogin;
    private PrefManager pref;
    private TextInputLayout inputLayoutPlace;
    private EditText inputPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         pref = new PrefManager(this);
        radioSexGroup=(RadioGroup)findViewById(R.id.radioGroup);
        tvLinktoLogin = (TextView) findViewById(R.id.link_to_login);
        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutMobile = (TextInputLayout) findViewById(R.id.input_layout_mobile);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputLayoutConfirmPassword = (TextInputLayout) findViewById(R.id.input_layout_conf_password);
        inputLayoutDate = (TextInputLayout) findViewById(R.id.input_layout_date);
        inputLayoutTime = (TextInputLayout) findViewById(R.id.input_layout_time);
        inputLayoutPlace = (TextInputLayout) findViewById(R.id.input_layout_place);
        inputName = (EditText) findViewById(R.id.input_name);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputMobile = (EditText)findViewById(R.id.input_mobile);
        inputPassword = (EditText) findViewById(R.id.input_password);
        inputConfirmPassword = (EditText) findViewById(R.id.input_conf_password);
        inputDate = (EditText) findViewById(R.id.input_date);
        inputTime = (EditText) findViewById(R.id.input_time);
        inputPlace = (EditText) findViewById(R.id.input_place);
        btnSignUp = (Button) findViewById(R.id.btn_signup);



        inputDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                showDatePicker();
                return true;
            }
        });

        inputTime.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showTimePicker();

                return true;
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
       String birthplace = inputPlace.getText().toString().trim();
        int selectedId=radioSexGroup.getCheckedRadioButtonId();
        radioSexButton=(RadioButton)findViewById(selectedId);
        String gender = radioSexButton.getText().toString().trim();
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
         if(!validatePlace()){
             return;
         }

        String token = pref.getToken();
                  if(token!=null) {
                      sendDataToServer(name, email, password, usertype, token, gender, date, time, mobile, birthplace);

                  }


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
    private boolean validatePlace() {
        if (inputPlace.getText().toString().trim().isEmpty()) {
            inputLayoutPlace.setError(getString(R.string.err_msg_name));
            requestFocus(inputPlace);
            return false;
        } else {
            inputLayoutPlace.setErrorEnabled(false);
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
    public void sendDataToServer(final String name, final String email, final String password, final String usertype, final String token, final String gender, final String dob, final String dot, final String mobile, final String birthplace){

        // Tag used to cancel the request
        String  obj_req = "obj_req";



        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();



     JSONObject params = new JSONObject();
        try {
            params.put("name",name);
            params.put("email",email);
            params.put("mobile",mobile);
            params.put("password", password);
            params.put("usertype", usertype);
            params.put("gender",gender);
            params.put("devicetoken", token);
            params.put("dob",dob);
            params.put("dot",dot);
            params.put("birthplace",birthplace);

        } catch (JSONException e) {
            e.printStackTrace();
        }





        JsonObjectRequest objJsonreq = new JsonObjectRequest(Request.Method.POST,
                ConstantLinks.REGISTER_USER, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String error =  response.getString("error");
                    String resMessage =  response.getString("message");
                    if(error.equals("true")){

                        String resType =  response.getString("type");


                        if(resType.equals("mail")){
                            inputEmail.setError(resMessage);
                        }

                        if(resType.equals("opps") || resType.equals("unknown")){
                            Toast.makeText(RegistrationActivity.this,resMessage,Toast.LENGTH_SHORT).show();
                        }





                    }else{
                        Toast.makeText(RegistrationActivity.this,resMessage,Toast.LENGTH_SHORT).show();
                        Intent log = new Intent(RegistrationActivity.this,LoginActivity.class);
                        startActivity(log);
                        finish();


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
