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

public class SignUp extends AppCompatActivity {


    public void Login(View view) {
        RequestQueue queue = Volley.newRequestQueue(this);

        TextInputEditText firstName = findViewById(R.id.signUpFirstName);
        TextInputEditText lastName = findViewById(R.id.signUpLastName);
        TextInputEditText email = findViewById(R.id.signUpEmail);
        TextInputEditText password = findViewById(R.id.signUpPassword);
        TextInputEditText confirmPassword = findViewById(R.id.signUpConfirmPassword);


        final String inputFirstName = firstName.getText().toString();
        final String inputLastName = lastName.getText().toString();
        final String inputEmail = email.getText().toString();
        final String inputPassword = password.getText().toString();
        final String inputConfirmPassword = confirmPassword.getText().toString();


        String url = "https://api.gethighlow.com/auth/sign_up";


        com.android.volley.toolbox.StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(SignUp.this,response,Toast.LENGTH_LONG).show();
                        VolleyLog.v("Response:%n %s", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignUp.this,error.toString(),Toast.LENGTH_LONG).show();
                        VolleyLog.e("Error: ", error.getMessage());
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("firstname", inputFirstName);
                params.put("lastname", inputLastName);
                params.put("email", inputEmail);
                params.put("password", inputPassword);
                params.put("confirmpassword", inputConfirmPassword);
                return params;
            }

        };

        queue.add(stringRequest);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void backToSignIn(View view) {
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        startActivity(intent);
    }
}
