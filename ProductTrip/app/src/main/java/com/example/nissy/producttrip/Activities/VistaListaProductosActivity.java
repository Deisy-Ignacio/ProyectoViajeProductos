package com.example.nissy.producttrip.Activities;

import android.os.Bundle;
<<<<<<< HEAD
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
=======
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.nissy.producttrip.Adapter.CustomRecyclerAdapterProducto;
>>>>>>> 6c4425bdfd017601e6ecdedf3dfebdef406667f0
import com.example.nissy.producttrip.Clases.Producto;
import com.example.nissy.producttrip.R;
import com.example.nissy.producttrip.conexion.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VistaListaProductosActivity extends AppCompatActivity {
<<<<<<< HEAD
    private ProductosArray listaProduc = new ProductosArray();
    private int idtienda;
    private ArrayList<Producto> listItem = new ArrayList<>();
    MyListAdaper myListAdaper;
=======

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    List<Producto> personUtilsList;

>>>>>>> 6c4425bdfd017601e6ecdedf3dfebdef406667f0
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_lista_productos);
<<<<<<< HEAD
        ListView lv = (ListView) findViewById(R.id.ListViewTienda);

        idtienda = Integer.parseInt(getIntent().getStringExtra("idtienda"));
        getDataJSON();
        myListAdaper = new MyListAdaper(this, R.layout.vista_lista_productos,listItem);
        lv.setAdapter(myListAdaper);

    }

    public void getDataJSON(){
        String URL = "all/producto/"+idtienda;
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, LoginActivity.BASE_URL+URL, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.i("VOLLEY",response.toString());
                            for (int i = 0; i<response.length(); i++) {
                                JSONObject datos = response.getJSONObject(i);

                                Producto p = new Producto(
                                        Integer.parseInt(datos.getString("idproducto")),
                                        Integer.parseInt(datos.getString("idtienda")),
                                        datos.getString("nombre"),
                                        datos.getString("descripcion"),
                                        Integer.parseInt(datos.getString("precio")),
                                        Integer.parseInt(datos.getString("stock")));
                                listItem.add(p);
                                myListAdaper.notifyDataSetChanged();
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

    /*private ArrayList<Producto> getListItemData(){
        ArrayList<Producto> listViewItems = new ArrayList<Producto>();
        listViewItems.add(new Producto(1,idtienda,"Juguete","Rojo",30,5));
        listViewItems.add(new Producto(2,idtienda,"Cuchara","plateada",40,7));
        listViewItems.add(new Producto(4,idtienda,"Mouse","gamer",60,4));
        listViewItems.add(new Producto(3,idtienda,"Pizza","Familiar",80,5));
        listViewItems.add(new Producto(6,idtienda,"Vaso","vidrio",100,85));

        return listViewItems;
        return listItem;
    }
    */


    /*private void generateListContent() {
        for(int i = 0; i < 55; i++) {
            data.add("This is row number " + i);
        }
    }
=======

        recyclerView = (RecyclerView) findViewById(R.id.recycleViewContainer);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
>>>>>>> 6c4425bdfd017601e6ecdedf3dfebdef406667f0

        recyclerView.setLayoutManager(layoutManager);

        personUtilsList = new ArrayList<>();

        //Adding Data into ArrayList
        personUtilsList.add(new Producto(1,"Chocolate","dulce",15,8));
        personUtilsList.add(new Producto(2,"carro","dulce",15,8));
        personUtilsList.add(new Producto(3,"pelota","dulce",85,8));
        personUtilsList.add(new Producto(4,"Camion","dulce",17,8));


        mAdapter = new CustomRecyclerAdapterProducto(this, personUtilsList);

        recyclerView.setAdapter(mAdapter);

    }
}