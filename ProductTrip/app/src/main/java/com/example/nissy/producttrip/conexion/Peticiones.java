package com.example.nissy.producttrip.conexion;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Peticiones {

    private Context contx;
    private String respPOST;

    public Peticiones (Context contexto){
        contx=contexto;
    }

    private void sendGET(String urll){

        String url = urll;
        JsonObjectRequest objetojson = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //loquellega =response;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(contx, "Revisa tu conexión",Toast.LENGTH_LONG).show();
                    }
                });
        VolleySingleton.getInstance(contx).addToRequestQueue(objetojson);

        objetojson.setRetryPolicy(
                new DefaultRetryPolicy(
                        40000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                ));

    }

    public void sendPOST(String URL, JSONObject jsonBody) {
            //RequestQueue requestQueue = VolleySingleton.getInstance(contx);
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //Toast.makeText(contx, response,Toast.LENGTH_LONG).show();
                    Log.i("VOLLEY", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    //String statusCode = String.valueOf(response.statusCode);
                    return super.parseNetworkResponse(response);
                }
            };
            VolleySingleton.getInstance(contx).addToRequestQueue(stringRequest);
    }

    public JSONArray getRespPOST() throws JSONException {
        return new JSONArray(respPOST);
    }
}
