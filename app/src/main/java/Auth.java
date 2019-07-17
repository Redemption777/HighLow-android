import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.highlowsignin.MainActivity;
import com.example.highlowsignin.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

class Auth {
    private static final String TAG = "Auth";
    private static Auth ourInstance = null;
    public static SharedPreferences pref;
    public RequestQueue requestQueue;

    private static Context context;


    public static synchronized Auth getInstance(Context context) {
        if (ourInstance == null)
            ourInstance = new Auth(context);

        return ourInstance;
    }

    private Auth(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());

    }

    public static void refreshAccess() {


        //First, you need to get the refresh token
        final String refreshToken = pref.getString("Tokens", "REFRESH_TOKEN");

        Log.v("Token: %n %s", refreshToken);

        Toast.makeText(context, refreshToken, Toast.LENGTH_LONG).show();

        final SharedPreferences.Editor editor = pref.edit();

        //Then, you make a volley request to https://api.gethighlow.com/auth/refresh_access with your refreshToken as a parameter
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.gethighlow.com/auth/refresh_access",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            //Creating JsonObject from response String
                            JSONObject jsonObject= new JSONObject(response);

                            //If the server returned "ERROR-INVALID-REFRESH-TOKEN" as an error, log the user out. Otherwise, store the new access token.
                            //TODO Then, we call a callback function of some sort (I haven't implemented that here because I don't know enough Java to do so yet.)
                            if (response.equals("ERROR-INVALID-REFRESH-TOKEN")) {
                                //TODO figure out how to log the user out
                            } else {

                                String access = jsonObject.getString("access");
                                editor.putString("ACCESS_TOKEN", access);

                                editor.apply();

                            }
                        } catch(JSONException e) {
                            Log.v("JSONError", e.getMessage());

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.e("Error: ", error.getMessage());
                    }
                }
        ){

            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("refresh", refreshToken);

                return params;
            }

        };
    }

    //TODO update the makeAuthenticatedRequest
    public static void makeAuthenticatedRequest(String url, int method, Map<String, String> params) {

        //Get our access token
        final String accessToken = ""; //???

        //Then, make the request to the URL with the Authorization header

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "Bearer " + accessToken);

        StringRequest stringRequest = new StringRequest(method, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //If it returns "ERROR-INVALID-TOKEN", we need to refresh the access token using `this.refreshAccess();`

                        //Otherwise, run a callback with the data we retrieved

                        //TODO NOTE: You will have to have some sort of callback parameter set up so that you can let the program know when you're done with the request.

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Handle the error
                    }
                }
        );

    }
}
