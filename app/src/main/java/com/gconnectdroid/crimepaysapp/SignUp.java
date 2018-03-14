package com.gconnectdroid.crimepaysapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SignUp extends AppCompatActivity {

    EditText email, username, password, zipCode, country, state, city;
    TextView signup;
    private AwesomeValidation awesomeValidation;
    private SwipeRefreshLayout swipeContainer;
    String url = "http://gconnectng.com/loginsignup/newsignup.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

 /*       awesomeValidation.addValidation(this, R.id.username, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.email, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.password_field, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.passworderror);
//        awesomeValidation.addValidation(this, R.id.editTextMobile, "^[2-9]{2}[0-9]{8}$", R.string.mobileerror);*/


        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_dark);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                                @Override
                                                public void onRefresh() {

                                                    Toast.makeText(SignUp.this, " Refreshed", Toast.LENGTH_SHORT).show();
                                                    swipeContainer.setRefreshing(false);

                                                }
                                            }
        );


        email = (EditText) findViewById(R.id.email);
        username = (EditText) findViewById(R.id.username_field);
        password = (EditText) findViewById(R.id.password_field);
        zipCode = (EditText) findViewById(R.id.zip_field);
        country = (EditText) findViewById(R.id.country_field);
        state = (EditText) findViewById(R.id.state_field);
        city = (EditText) findViewById(R.id.city_field);
        signup = (TextView) findViewById(R.id.signup);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String semail = email.getText().toString();
                String suser = username.getText().toString();
                String spass = password.getText().toString();
                String szip = zipCode.getText().toString();
                String scountry = country.getText().toString();
                String sstate = state.getText().toString();
                String scity = city.getText().toString();

                if (semail.isEmpty() || suser.isEmpty() || spass.isEmpty() || szip.isEmpty()|| scountry.isEmpty() || sstate.isEmpty() || scity.isEmpty()) {

                    Toast.makeText(SignUp.this, "Fill all details", Toast.LENGTH_SHORT).show();
                }
                else {


                    signup(semail, suser, spass, szip,scountry,sstate,scity);
                    SharedPreferences preferences = getSharedPreferences("Mypref", Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.putString("username", suser);
                    editor.putString("password", spass);
                    editor.putString("mobile", szip);
                    editor.putString("email", semail);
                    editor.putString("county", scountry);
                    editor.putString("state", sstate);
                    editor.putString("city", scity);
                    editor.commit();
                    show();

                }

            }
        });

    }

    public void show() {

//        Toast.makeText(SignUp.this, " Successfully registered, proceed to login...", Toast.LENGTH_SHORT).show();
        /*Intent intent = new Intent(SignUp.this, Signin.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);*/
      /*  if (awesomeValidation.validate()) {
            Toast.makeText(SignUp.this, "Registration Successfull", Toast.LENGTH_LONG).show();

            //process the data further
        }*/
    }

    public void signup(final String email, final String username, final String password, final String zipCode, final String country, final String state, final String city) {

        RequestQueue requestQueue = Volley.newRequestQueue(SignUp.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

//                Log.i("Hitesh", "" + response);

                try{
                    JSONObject jsonObject= new JSONObject(response);

                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                }catch (JSONException e){
                    e.printStackTrace();

                    Toast.makeText(getApplicationContext(),("message"), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("Hitesh", "" + error);
                Toast.makeText(SignUp.this, "" + error, Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> stringMap = new HashMap<>();
                stringMap.put("email", email);
                stringMap.put("username", username);
                stringMap.put("password", password);
                stringMap.put("zipCode", zipCode);
                stringMap.put("country", country);
                stringMap.put("state", state);
                stringMap.put("city", city);
                return stringMap;
            }
        };

        requestQueue.add(stringRequest);

    }
    public void login(View viw){
        startActivity(new Intent(getApplicationContext(),Signin.class));
    }
}
