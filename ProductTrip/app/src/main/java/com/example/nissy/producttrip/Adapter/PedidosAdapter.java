package com.example.nissy.producttrip.Adapter;

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

public class PedidosAdapter extends RecyclerView.Adapter<PedidosAdapter.PedidoHolder> {
    Context mContext;
    List<Pedido> mData;
    int idrepartidor;
    View v;

    public PedidosAdapter(Context mContext, List<Pedido> mData, int idrepartidor){
        this.mContext = mContext;
        this.mData = mData;
        this.idrepartidor = idrepartidor;
    }

    @NonNull
    @Override
    public PedidosAdapter.PedidoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        v =inflater.inflate(R.layout.item_pedido,viewGroup,false);
        return new PedidoHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidosAdapter.PedidoHolder pedido, int position) {
        bind(mData.get(position), v);
        pedido.nombre_producto.setText(mData.get(position).getNombre_producto());
        pedido.nombre_tienda.setText(mData.get(position).getNombre_tienda()+"");
        pedido.direccion_entrega.setText(mData.get(position).getLatitud()+"");
    }

    public void login(int idpedido, int idrepartidor){


    }

    Pedido extra;
    private void bind(Pedido item, View view) {
        extra = item;
        ImageButton button = (ImageButton) view.findViewById(R.id.button_aceptar);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String URL = "asignar/pedido";
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("idpedido", extra.getIdpedido());
                    jsonBody.put("idrepartidor", idrepartidor);
                    Log.i("VOLLEY","Adapter" + jsonBody.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final String requestBody = jsonBody.toString();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, LoginActivity.BASE_URL+URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String status = jsonObject.getString("status");
                                    Log.i("VOLLEY",jsonObject.toString());
                                    Toast.makeText(mContext,jsonObject.getString("mensaje"),Toast.LENGTH_SHORT).show();
                                    if(status.equals("1")){
                                        Intent intent = new Intent(mContext, MapsActivity.class);
                                        intent.putExtra("idpedido", extra.getIdproducto());
                                        intent.putExtra("idproducto", extra.getIdproducto());
                                        intent.putExtra("nombre_producto", extra.getNombre_producto()+"");
                                        intent.putExtra("descripcion", extra.getDescripcion());
                                        intent.putExtra("latitud", extra.getLatitud());
                                        intent.putExtra("longitud", extra.getLongitud());
                                        intent.putExtra("idtienda", extra.getIdtienda());
                                        intent.putExtra("nombre_tienda", extra.getNombre_tienda());
                                        intent.putExtra("idcliente", extra.getIdcliente());
                                        intent.putExtra("nombre_cliente", extra.getNombre_cliente());
                                        mContext.startActivity(intent);
                                    }


                                } catch (JSONException e) {
                                    Log.e("VOLLEY", e.toString());
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", error.toString());
                    }
                }) {
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            return requestBody == null ? null : requestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                            return null;
                        }
                    }

                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        //String statusCode = String.valueOf(response.statusCode);
                        return super.parseNetworkResponse(response);
                    }
                };
                VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);

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
