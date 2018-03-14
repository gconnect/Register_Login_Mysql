package com.gconnectdroid.crimepaysapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Signin extends AppCompatActivity {
    EditText email, password;
     Button signup;
      TextView login;
    String pass,username;
    String url;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);



        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_dark);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                                @Override
                                                public void onRefresh() {

                                                    Toast.makeText(Signin.this, " Refreshed", Toast.LENGTH_SHORT).show();
                                                    swipeContainer.setRefreshing(false);

                                                }
                                            }
        );

        signup = (Button) findViewById(R.id.register2);
        email = (EditText) findViewById(R.id.username_field);
        password = (EditText) findViewById(R.id.password_field);
        login = (TextView) findViewById(R.id.login);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = email.getText().toString();
                pass = password.getText().toString();


            if (username!= username || pass!= pass) {
                    Toast.makeText(Signin.this, "Invaild username or password", Toast.LENGTH_SHORT).show();
                }
               else if (username.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(Signin.this, "fill details", Toast.LENGTH_SHORT).show();

                }

                else {

                    login(username, pass);
                }


            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Signin.this,SignUp.class);
                startActivity(intent);
            }
        });
    }


    public void login(final String user, final String pass){

        url = "http://gconnectng.com/loginsignup/newlogin.php?username="+user+"&password="+pass+"";
        Log.i("Hiteshurl",""+url);
        RequestQueue requestQueue = Volley.newRequestQueue(Signin.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                   JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    String id = jsonObject1.getString("id");
                    String pass = jsonObject1.getString("password");
                    String username = jsonObject1.getString("username");
                    String zipCode = jsonObject1.getString("zipCode");
                    String email = jsonObject1.getString("email");
                    String country = jsonObject1.getString("country");
                    String state = jsonObject1.getString("state");
                    String city = jsonObject1.getString("city");
                    SharedPreferences shared = getSharedPreferences("Mypref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = shared.edit();
                    editor.putString("id",id);
                    editor.putString("pass",pass);
                    editor.putString("username",username);
                    editor.putString("email",email);
                    editor.putString("zipCode",zipCode);
                    editor.putString("country",country);
                    editor.putString("state",state);
                    editor.putString("city",city);
                    editor.commit();
                  /*  Intent intent = new Intent(Signin.this,YouTubePlayerActivity.class);
                    startActivity(intent);*/
            Intent intent = YouTubeStandalonePlayer.createVideoIntent(Signin.this,YouTubePlayerActivity.GOOGLE_API_KEY,
                     YouTubePlayerActivity.YOUTUBE_PLAYLIST);
                     startActivity(intent);


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),("Username or Password Incorrect"), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
//                Log.i("HiteshURLerror",""+error);
            }
        });

        requestQueue.add(stringRequest);

    }
}
