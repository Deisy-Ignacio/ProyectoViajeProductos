package com.example.nissy.producttrip.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.nissy.producttrip.Adapter.CustomRecyclerAdapterProducto;
import com.example.nissy.producttrip.Clases.Producto;
import com.example.nissy.producttrip.R;

import java.util.ArrayList;
import java.util.List;

public class VistaListaProductosActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    List<Producto> personUtilsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_lista_productos);

        recyclerView = (RecyclerView) findViewById(R.id.recycleViewContainer);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);

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