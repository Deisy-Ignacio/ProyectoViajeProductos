package com.example.nissy.producttrip.Adapter;

import android.app.LauncherActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nissy.producttrip.Clases.Producto;
import com.example.nissy.producttrip.R;

import java.util.List;

public class OrderAdapter extends ArrayAdapter<Producto> {
    private List<Producto> list;
    private Context context;

    TextView currentProductoName,
            currentCost,
            cantidadText,
            addMeal,
            subtractMeal,
            removeMeal,
            currentProductoPrecio;

    public OrderAdapter(Context context, List<Producto> myOrders) {
        super(context, 0, myOrders);
        this.list = myOrders;
        this.context = context;
    }


    public View getView(final int position, View convertView, ViewGroup parent){
        //View listItemView = inflater.inflate(R.layout.item_carrito, parent, false);
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_carrito,parent,false
            );
        }

        final Producto currentProducto = getItem(position);

        currentProductoName = (TextView)listItemView.findViewById(R.id.selected_producto_name);
        currentProductoPrecio = (TextView)listItemView.findViewById(R.id.precio);
        currentCost = (TextView)listItemView.findViewById(R.id.selected_producto_cantidad);
        subtractMeal = (TextView)listItemView.findViewById(R.id.menos_producto);
        cantidadText = (TextView)listItemView.findViewById(R.id.cantidad);
        addMeal = (TextView)listItemView.findViewById(R.id.mas_producto);
        removeMeal = (TextView)listItemView.findViewById(R.id.delete_producto);

        //Set the text of the meal, amount and quantity
        currentProductoName.setText(currentProducto.getmName());
        currentProductoPrecio.setText("$"+" "+currentProducto.getmCantidad());
                currentCost.setText("$"+" "+ (currentProducto.getmCantidad() * currentProducto.getmQuantity()));
        cantidadText.setText("x "+ currentProducto.getmQuantity());

        //OnClick listeners for all the buttons on the ListView Item
        addMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentProducto.addToQuantity();
                cantidadText.setText("x "+ currentProducto.getmQuantity());
                currentCost.setText("$"+" "+ (currentProducto.getmCantidad() * currentProducto.getmQuantity()));
                notifyDataSetChanged();
            }
        });

        subtractMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentProducto.removeFromQuantity();
                cantidadText.setText("x "+currentProducto.getmQuantity());
                currentCost.setText("$"+ (currentProducto.getmCantidad() * currentProducto.getmQuantity()));
                notifyDataSetChanged();
            }
        });

        removeMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.remove(position);
                notifyDataSetChanged();
            }
        });

        return listItemView;
    }

}
