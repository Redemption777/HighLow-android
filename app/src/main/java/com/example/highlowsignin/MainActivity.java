package com.example.highlowsignin;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;



import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    public void Login(View view) {
        RequestQueue queue = Volley.newRequestQueue(this);

        TextInputEditText email = findViewById(R.id.emailInput);
        TextInputEditText password = findViewById(R.id.passwordInput);

         final String inputEmail = email.getText().toString();
         final String inputPassword = password.getText().toString();

        String url = "https://api.gethighlow.com/auth/sign_in";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //Creating JsonObject from response String
                            JSONObject jsonObject= new JSONObject(response);

                            if(jsonObject.has("error")){ //If the jsonObject has an error than display the error

                                String error = jsonObject.getString("error");
                                Toast.makeText(MainActivity.this,"Error: " + error,Toast.LENGTH_LONG).show();
                                VolleyLog.v("Error:%n %s", error.toString());

                            } else{ //If there is not an error than display and save the access and refresh tokens

                                String access = jsonObject.getString("access");
                                String refresh = jsonObject.getString("refresh");
                                String uid = jsonObject.getString("uid");
                                Toast.makeText(MainActivity.this,"ACCESS: " + access,Toast.LENGTH_LONG).show();
                                Toast.makeText(MainActivity.this,"REFRESH: " + refresh,Toast.LENGTH_LONG).show();
                                Toast.makeText(MainActivity.this,"UID: " + uid, Toast.LENGTH_SHORT).show();
                                VolleyLog.v("Response:%n %s", jsonObject.toString());


                                SharedPreferences pref = getApplicationContext().getSharedPreferences("Tokens", 0); // 0 - for private mode
                                SharedPreferences.Editor editor = pref.edit();

                                editor.putString("ACCESS_TOKEN", access); // Storing access token as a string
                                editor.putString("REFRESH_TOKEN", refresh); // Storing refresh token as a string

                                editor.apply(); // commit changes

                                /*Now to retrieve the tokens you need to initialize the preferences like this:

                                 SharedPreferences pref = getApplicationContext().getSharedPreferences("Tokens", 0);

                                 and then retrieve the data like this:

                                 pref.getString("ACCESS_TOKEN");

                                 and

                                 pref.getString("REFRESH_TOKEN");
                                 */
                            }

                        } catch (JSONException e) {
                            Log.v("JSONError", e.getMessage());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        VolleyLog.e("Error: ", error.getMessage());
                    }
                }){

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("email", inputEmail);
                params.put("password", inputPassword);
                return params;
            }

        };

        queue.add(stringRequest);


        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000, //Increases the Volley timeout to 10000 milliseconds or 10 seconds
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView forgot_password = findViewById(R.id.forgot_password);

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ForgotPassword.class);
                startActivity(intent);
            }
        } );

    }


    public void createAccount(View view) {
        Intent intent = new Intent(view.getContext(), SignUp.class);
        startActivity(intent);
    }
}







