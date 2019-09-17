package com.example.nissy.producttrip.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.nissy.producttrip.Activities.LoginActivity;
import com.example.nissy.producttrip.Activities.MapsActivity;
import com.example.nissy.producttrip.Clases.Pedido;
import com.example.nissy.producttrip.R;
import com.example.nissy.producttrip.conexion.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class PedidosAdapterCliente extends RecyclerView.Adapter<PedidosAdapterCliente.PedidoHolder>{
    Context mContext;
    List<Pedido> mData;
    int idcliente;
    View v;
    private ProgressDialog progress;

    public PedidosAdapterCliente(Context mContext, List<Pedido> mData, int idcliente){
        this.mContext = mContext;
        this.mData = mData;
        this.idcliente = idcliente;
    }

    @NonNull
    @Override
    public PedidosAdapterCliente.PedidoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        v =inflater.inflate(R.layout.item_pedido_cliente,viewGroup,false);
        return new PedidoHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidosAdapterCliente.PedidoHolder pedido, int position) {
        bind(mData.get(position), v);
        pedido.nombre_producto.setText(mData.get(position).getNombre_producto());
        pedido.nombre_tienda.setText(mData.get(position).getNombre_tienda()+"");
        pedido.direccion_entrega.setText(mData.get(position).getClatitud()+"");
    }


    Pedido extra;
    private void bind(final Pedido item, View view) {
        extra = item;
        ImageButton button = (ImageButton) view.findViewById(R.id.button_aceptar);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(mContext,MapsActivity.class);
                intent.putExtra("idpedido",item.getIdpedido()+"");
                intent.putExtra("clatitud",item.getClatitud()+"");
                intent.putExtra("clongitud",item.getClongitud()+"");
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class PedidoHolder extends RecyclerView.ViewHolder{

        TextView nombre_producto;
        TextView nombre_tienda;
        TextView direccion_entrega;

        public PedidoHolder(View itemView){
            super(itemView);
            nombre_producto= (TextView) itemView.findViewById(R.id.nombre_producto);
            nombre_tienda= (TextView) itemView.findViewById(R.id.nombre_tienda);
            direccion_entrega= (TextView) itemView.findViewById(R.id.direccion_entrega);
        }


    }
}
