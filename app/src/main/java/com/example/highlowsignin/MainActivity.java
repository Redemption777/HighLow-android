package com.example.highlowsignin;



import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
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


public class MainActivity extends AppCompatActivity {

    public void Login(View view) {
        RequestQueue queue = Volley.newRequestQueue(this);

        TextInputEditText email = findViewById(R.id.emailInput);
        TextInputEditText password = findViewById(R.id.passwordInput);

         final String inputEmail = email.getText().toString();
         final String inputPassword = password.getText().toString();
         Log.d("Email", inputEmail);
         Log.d("Password", inputPassword);

        String url = "https://api.gethighlow.com/auth/sign_in";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
                        VolleyLog.v("Response:%n %s", response.toString());
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



}







