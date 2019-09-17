package com.example.nissy.producttrip.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.nissy.producttrip.R;
import com.example.nissy.producttrip.conexion.VolleySingleton;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

public class VistaPagoActivity extends AppCompatActivity implements  LocationListener{
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;

    private static final String CONFIG_CLIENT_ID = "AdcMmBMyQT_mduxOle6vKKqGzKGIL7Fsv301fmqhQ7MZvknwu51ykKQ-vCTJUnyET2Ca6q_2rtqmIFAe";
    private static final int REQUEST_CODE_PAYMENT = 1;

    String precio;
    String idproducto;
    String idtienda;
    double latitud, longitud;

    Button pagar;

    private LocationManager locManager;
    private Location loc;
    private ProgressDialog progress;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
            // ya llévame Diosito :c
            .merchantName("ProductTrip")
            .merchantPrivacyPolicyUri(
                    Uri.parse("https://www.mi_tienda.com/privacy"))
            .merchantUserAgreementUri(
                    Uri.parse("https://www.mi_tienda.com/legal"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_pago);
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);

        Log.i("VOLLEY",getIntent().getExtras().toString());

        String nombre = getIntent().getStringExtra("nombre_producto");
        String descrip = getIntent().getStringExtra("descripcion_producto");
        precio = getIntent().getStringExtra("precio");
        idtienda = getIntent().getStringExtra("idtienda");
        idproducto = getIntent().getStringExtra("idproducto");

        TextView tNombre = findViewById(R.id.nombre_p);
        TextView tDescrip = findViewById(R.id.descripcion);
        TextView tPrecio = findViewById(R.id.precio);
        TextView tTienda = findViewById(R.id.tienda);
        TextView tIDProducto = findViewById(R.id.producto);
        pagar = findViewById(R.id.pagar_producto);

        tNombre.setText(nombre);
        tDescrip.setText(descrip);
        tPrecio.setText(precio);
        tTienda.setText(idtienda);
        tIDProducto.setText(idproducto);

        checkpermisos();
        pagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pay();
            }
        });

        progress =new ProgressDialog(VistaPagoActivity.this);
        progress.setMessage("Obteniendo Ubicacion");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.show();

    }

    public void pay() {
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(precio)), "MXN", "Pagado",
                PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);

        // send the same configuration for restart resiliency
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
        /*Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payment);
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    Log.i("paymentExample", confirm.toJSONObject().toString(4));

                    if (confirm != null) {
                        try {

                            JSONObject jsonObject = new JSONObject(confirm.toJSONObject().toString(4));
                            JSONObject response = new JSONObject(jsonObject.getString("response"));

                            doPedido();

                            Toast.makeText(this, "Payment Successful transction Id:-" + response.getString("id"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            System.err.println("confirmacion nula");e.printStackTrace();
                        }
                    }
                    // TODO: send 'confirm' to your server for verification.
                    // see https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                    // for more details.

                } catch (JSONException e) {
                    Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("paymentExample", "The user canceled.");
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
        }
    }



    public void doPedido(){


        String URL = "nuevo/pedido";
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("idcliente", VistaClienteActivity.cliente.get("idcliente"));
            jsonBody.put("idproducto", idproducto);
            jsonBody.put("latitud", latitud);
            jsonBody.put("longitud", longitud);
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
                            String mensaje = jsonObject.getString("mensaje");
                            Log.i("VOLLEY", jsonObject.toString());
                            Intent activity = null;
                            finish();

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
        VolleySingleton.getInstance(VistaPagoActivity.this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            loc=location;
            latitud=loc.getLatitude();
            longitud=loc.getLongitude();
            progress.cancel();
            pagar.setEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void checkpermisos(){

        ActivityCompat.requestPermissions(VistaPagoActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //direccion.setText("No se han definido los permisos necesarios.");
            return;
        } else {
            try {
                locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
            }
            catch(SecurityException e) {
                e.printStackTrace();
            }

        }
    }

    /*@Override
    protected void onDestroy() {
        stopService(new Intent(this,PayPalService.class));
        super.onDestroy();
    }

    private void procersarPago() {
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(precio)),"MX","Pagado",PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PAYPAL_REQUEST_CODE){
            if(requestCode == RESULT_OK){
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if(confirmation!=null){
                    try{
                        String paymentDetails = confirmation.toJSONObject().toString(4);
                        startActivity(new Intent(this,detalle_pago.class).putExtra("PaymentDetails",paymentDetails).putExtra("PaymentAmount",precio));
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }else if(requestCode == Activity.RESULT_CANCELED)
                Toast.makeText(this,"Cancelada",Toast.LENGTH_LONG).show();

        }else if (requestCode == PaymentActivity.RESULT_EXTRAS_INVALID)
            Toast.makeText(this,"Invalida",Toast.LENGTH_LONG).show();
    }*/
}
