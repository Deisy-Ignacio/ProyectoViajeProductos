package com.example.nissy.producttrip.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.nissy.producttrip.Adapter.PedidosAdapter;
import com.example.nissy.producttrip.Clases.Pedido;
import com.example.nissy.producttrip.R;
import com.example.nissy.producttrip.conexion.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PedidosDisponibles extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PedidosAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Pedido> pedidos;
    int idrepartidor = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_disponibles);


        try {
            JSONObject repa = new JSONObject(getIntent().getStringExtra("repartidor"));
            idrepartidor = repa.getInt("idrepartidor");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        pedidos = new ArrayList<>();
        getDataJSON();

        loadRecycler();

        ImageButton actualizar = (ImageButton) findViewById(R.id.actualizar);
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataJSON();
                loadRecycler();
            }
        });

        ImageButton mapa = (ImageButton) findViewById(R.id.mapa);
        mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapa = new Intent(PedidosDisponibles.this, MapsActivity.class);
                startActivity(mapa);
            }
        });
    }

    private void loadRecycler() {
        recyclerView = (RecyclerView) findViewById(R.id.pedidos_recycler);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new PedidosAdapter(this,pedidos,idrepartidor);
        recyclerView.setAdapter(mAdapter);
    }


    public void getDataJSON(){
        pedidos.clear();
        String URL = "disponibles/pedido";
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, LoginActivity.BASE_URL+URL, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.i("VOLLEY",response.toString());
                            for (int i = 0; i<response.length(); i++) {
                                JSONObject datos = response.getJSONObject(i);
                                pedidos.add(new Pedido(Integer.parseInt(datos.getString("idpedido")),
                                        Integer.parseInt(datos.getString("idproducto")),
                                        datos.getString("nombre_producto"),
                                        Double.parseDouble(datos.getString("clatitud")),
                                        Double.parseDouble(datos.getString("clongitud")),
                                        Integer.parseInt(datos.getString("idtienda")),
                                        datos.getString("nombre_tienda"),
                                        Integer.parseInt(datos.getString("idcliente")),
                                        datos.getString("nombre_cliente")
                                        ));
                                mAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
        VolleySingleton.getInstance(PedidosDisponibles.this).addToRequestQueue(jsonObjectRequest);
    }


    @Override
    protected void onPause() {
        super.onPause();
        getDataJSON();
        loadRecycler();
    }
}
