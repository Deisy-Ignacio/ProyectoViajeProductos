package com.example.nissy.producttrip.Activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nissy.producttrip.R;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class VistaPagoActivity extends AppCompatActivity {
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;

    private static final String CONFIG_CLIENT_ID = "AbC0uyb9LSZakzFNe7M5kSs0FnK7_brNmn0gliJPzBhRT-2AOLB7hUqOFunzP5-T4YaPzMHqS-ka3qWX";
    private static final int REQUEST_CODE_PAYMENT = 1;
    String precio;
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

        String nombre = getIntent().getStringExtra("nombre_producto");
        String descrip = getIntent().getStringExtra("descripcion_producto");
        precio = getIntent().getStringExtra("precio");

        TextView tNombre = findViewById(R.id.nombre_p);
        TextView tDescrip = findViewById(R.id.descripcion);
        TextView tPrecio = findViewById(R.id.precio);
        Button pagar = findViewById(R.id.pagar_producto);

        tNombre.setText(nombre);
        tDescrip.setText(descrip);
        tPrecio.setText(precio);
        /*pagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //procersarPago();
            }
        });*/


    }

    public void pay(View view) {
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(precio)), "MX", "Pagado",
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
