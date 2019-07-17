import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

class Auth {
    private static final String TAG = "Auth";
    private static Auth ourInstance = null;

    public RequestQueue requestQueue;

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
        final String refreshToken = "";//???

        //Then, you make a volley request to https://api.gethighlow.com/auth/refresh_access with your refreshToken as a parameter
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.gethighlow.com/auth/refresh_access",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //If the server returned "ERROR-INVALID-REFRESH-TOKEN" as an error, log the user out. Otherwise, store the new access token.
                        //Then, we call a callback function of some sort (I haven't implemented that here because I don't know enough Java to do so yet.)

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //Response to the error

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

                        //NOTE: You will have to have some sort of callback parameter set up so that you can let the program know when you're done with the request.

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
