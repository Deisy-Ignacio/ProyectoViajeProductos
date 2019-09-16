package com.example.nissy.producttrip.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nissy.producttrip.Activities.VistaPagoActivity;
import com.example.nissy.producttrip.Clases.Producto;
import com.example.nissy.producttrip.R;

import java.util.List;

public class CustomRecyclerAdapterProducto extends RecyclerView.Adapter<CustomRecyclerAdapterProducto.ViewHolder> {
    private Context context;
    private List<Producto> personUtils;
    View v;

    public CustomRecyclerAdapterProducto(Context context, List personUtils) {
        this.context = context;
        this.personUtils = personUtils;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        bind(personUtils.get(position), v);
        holder.itemView.setTag(personUtils.get(position));

        Producto pu = personUtils.get(position);

        holder.pName.setText(pu.getmName());
        holder.pJobProfile.setText("$"+String.valueOf(pu.getmCantidad()));

    }

    @Override
    public int getItemCount() {
        return personUtils.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView pName;
        public TextView pJobProfile;


        public ViewHolder(final View itemView) {
            super(itemView);
            pName = (TextView) itemView.findViewById(R.id.item_producto_name);
            pJobProfile = (TextView) itemView.findViewById(R.id.precio);
        }
    }
    private void bind(final Producto item, final View view) {

        Button pagar = (Button) view.findViewById(R.id.pagar);
        final String nom= item.getmName();
        pagar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, VistaPagoActivity.class);
                intent.putExtra("nombre_producto", item.getmName());
                intent.putExtra("precio", String.valueOf(item.getmCantidad()));
                intent.putExtra("descripcion_producto", item.getDescripcion());
                view.getContext().startActivity(intent);
                Toast.makeText(view.getContext(), item.getmName()+" is "+ item.getmCantidad(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}