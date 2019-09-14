package com.example.nissy.producttrip.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.nissy.producttrip.R;

public class VistaTiendaActivity extends AppCompatActivity {
    private ImageButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_tienda);
        add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent producto = new Intent(VistaTiendaActivity.this, RegistrarProductoActivity.class);//CLIENTE
                startActivity(producto);
            }
        });

    }
    @Override //REGRESAR ACTIVITY
    public void onBackPressed() {
        Intent inten = new Intent();
        inten.setClass(VistaTiendaActivity.this,LoginActivity.class);
        startActivity(inten);
        finish();
    }
}
