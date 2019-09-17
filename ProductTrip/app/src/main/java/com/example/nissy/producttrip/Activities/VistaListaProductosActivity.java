package com.example.nissy.producttrip.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.nissy.producttrip.Adapter.CustomRecyclerAdapterProducto;
import com.example.nissy.producttrip.Clases.Producto;
import com.example.nissy.producttrip.R;
import com.example.nissy.producttrip.conexion.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VistaListaProductosActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    List<Producto> personUtilsList;

    int idtienda, idproducto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_lista_productos);

        idtienda = Integer.parseInt(getIntent().getStringExtra("idtienda"));
        personUtilsList = new ArrayList<>();
        getDataJSON();
        recyclerView = (RecyclerView) findViewById(R.id.recycleViewContainer);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new CustomRecyclerAdapterProducto(this, personUtilsList);
        recyclerView.setAdapter(mAdapter);

    }


    public void getDataJSON(){
        String URL = "all/producto/"+idtienda;
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, LoginActivity.BASE_URL+URL, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            //Log.i("VOLLEY",response.toString());
                            for (int i = 0; i<response.length(); i++) {
                                JSONObject datos = response.getJSONObject(i);

                                Producto p = new Producto(
                                        Integer.parseInt(datos.getString("idproducto")),
                                        Integer.parseInt(datos.getString("idtienda")),
                                        datos.getString("nombre"),
                                        datos.getString("descripcion"),
                                        Integer.parseInt(datos.getString("precio")),
                                        Integer.parseInt(datos.getString("stock")));
                                personUtilsList.add(p);
                                mAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            Log.e("VOLLEY",e.toString());
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY",error.toString());
                    }
                });
        VolleySingleton.getInstance(VistaListaProductosActivity.this).addToRequestQueue(jsonObjectRequest);
    }
}