package com.example.nissy.producttrip.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.nissy.producttrip.Clases.Producto;
import com.example.nissy.producttrip.Control.ProductosArray;
import com.example.nissy.producttrip.R;
import com.example.nissy.producttrip.conexion.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class VistaListaProductosActivity extends AppCompatActivity {
    private ProductosArray listaProduc = new ProductosArray();
    private int idtienda;
    private ArrayList<Producto> listItem = new ArrayList<>();
    MyListAdaper myListAdaper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_lista_productos);
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

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class MyListAdaper extends ArrayAdapter<Producto> {
        private int layout;
        private List<Producto> mObjects;
        private MyListAdaper(Context context, int resource, List<Producto> objects) {
            super(context,resource,objects);
            mObjects = objects;
            layout = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            //setContentView(R.layout.vista_lista_productos);
            ViewHolder mainViewholder = null;
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.list_item_thumbnail);
                viewHolder.title = (TextView) convertView.findViewById(R.id.list_item_text);
                viewHolder.button = (Button) convertView.findViewById(R.id.list_item_btn);
                convertView.setTag(viewHolder);
            }
            mainViewholder = (ViewHolder) convertView.getTag();
            mainViewholder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Se añadió con éxito. ", Toast.LENGTH_SHORT).show();
                    listaProduc.agregarProducto(getItem(position).getProduct());
                                   }
            });
            String texto =  getItem(position).getmName() +"\n $"+ getItem(position).getmCantidad();
            //mainViewholder.title.setText(getItem(position).getmName());
            mainViewholder.title.setText(texto.replaceAll("\\\\n", "\n"));




            return convertView;
        }
    }
    public class ViewHolder {

        ImageView thumbnail;
        TextView title;
        Button button;
    }

    public ProductosArray carrito(){
        return listaProduc;
    }

}
