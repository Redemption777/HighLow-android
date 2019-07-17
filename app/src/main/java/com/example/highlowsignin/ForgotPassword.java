package com.example.highlowsignin;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ForgotPassword extends AppCompatActivity {

    public void sendConformationEmail(View view) {
        RequestQueue queue = Volley.newRequestQueue(this);



        String url = "https://api.gethighlow.com/auth/forgot_password";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        if(response.contains("success")){
                            Toast.makeText(ForgotPassword.this,"Please check your email to reset your password",Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(ForgotPassword.this,"There was an error, please make sure your email is correct and try again.",Toast.LENGTH_LONG).show();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(ForgotPassword.this,error.toString(),Toast.LENGTH_LONG).show();
                        VolleyLog.e("Error: ", error.getMessage());
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                TextInputEditText email = findViewById(R.id.forgotPassEmail);


                String inputEmail = email.getText().toString();

                Log.d("Email", inputEmail);

                Map<String,String> params = new HashMap<>();
                params.put("email", inputEmail);

                return params;
            }

        };

        queue.add(stringRequest);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
    }


    public void exit(View view) {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }
}

