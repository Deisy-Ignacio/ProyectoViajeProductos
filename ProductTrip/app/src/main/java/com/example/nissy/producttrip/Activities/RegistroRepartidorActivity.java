package com.example.nissy.producttrip.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.nissy.producttrip.conexion.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import com.example.nissy.producttrip.R;

public class RegistroRepartidorActivity extends AppCompatActivity {
    private EditText name;
    private EditText lastname;
    private EditText lastsecondname;
    private EditText address;
    private EditText phone;
    private EditText email;
    private EditText password;
    private Button registo;
    private TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_repartidor);

        name = (EditText) findViewById(R.id.input_name);
        lastname = (EditText) findViewById(R.id.input_paterno);
        lastsecondname = (EditText) findViewById(R.id.input_materno);
        name = (EditText) findViewById(R.id.input_name);
        address = (EditText) findViewById(R.id.input_address);
        phone = (EditText) findViewById(R.id.edit_phone);
        email = (EditText) findViewById(R.id.input_email);
        password = (EditText) findViewById(R.id.input_password);

        registo = (Button) findViewById(R.id.btn_signup);
        registo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPOST(name.getText().toString(),
                        lastname.getText().toString(),
                        lastsecondname.getText().toString(),
                        address.getText().toString(),
                        phone.getText().toString(),
                        email.getText().toString(),
                        password.getText().toString());
            }

        });

        login = (TextView) findViewById(R.id.link_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activity = new Intent(RegistroRepartidorActivity.this, LoginActivity.class);
                startActivity(activity);
            }
        });
    }

    private void sendPOST(String name,String lastname,String lastsecondname, String address, String phone, String email, String password) {
        String URL = "repartidor/cliente";
        final JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("nombre", name);
            jsonBody.put("apaterno", name);
            jsonBody.put("apmaterno", name);
            jsonBody.put("direccion", address);
            jsonBody.put("telefono", phone);
            jsonBody.put("correo", email);
            jsonBody.put("contrasena", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = jsonBody.toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LoginActivity.BASE_URL+URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String mensaje = jsonObject.getString("mensaje");

                            if(status.equals("1")){
                                Toast.makeText(RegistroRepartidorActivity.this, mensaje,Toast.LENGTH_LONG).show();
                                Intent activity = new Intent(RegistroRepartidorActivity.this, VistaClienteActivity.class);//REPARTIDO
                                activity.putExtra("cliente",jsonBody.toString());
                                Log.i("VOLLEY", jsonBody.toString());
                                startActivity(activity);
                            }
                            else{
                                Log.i("VOLLEY", jsonObject.toString());
                                Toast.makeText(RegistroRepartidorActivity.this, mensaje,Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Log.e("VOLLEY", e.toString());
                        }
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
        VolleySingleton.getInstance(RegistroRepartidorActivity.this).addToRequestQueue(stringRequest);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}