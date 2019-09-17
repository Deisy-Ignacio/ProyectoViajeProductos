package com.example.nissy.producttrip.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.nissy.producttrip.Activities.LoginActivity;
import com.example.nissy.producttrip.Activities.PedidosDisponibles;
import com.example.nissy.producttrip.Activities.VistaClienteActivity;
import com.example.nissy.producttrip.Activities.VistaListaProductosActivity;
import com.example.nissy.producttrip.Adapter.PedidosAdapter;
import com.example.nissy.producttrip.Adapter.PedidosAdapterCliente;
import com.example.nissy.producttrip.Clases.Pedido;
import com.example.nissy.producttrip.R;
import com.example.nissy.producttrip.conexion.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Cliente_pedidosFragment extends Fragment {

    public Cliente_pedidosFragment() {
        // Required empty public constructor
    }


    private PedidosAdapterCliente adapter;
    private RecyclerView recyclerView;
    private Cliente_pedidosFragment.OnFragmentInteractionListener mListener;
    private List<Pedido> pedidos;
    private LinearLayoutManager layoutManager;

    // TODO: Rename and change types and number of parameters
    public static Cliente_pedidosFragment newInstance(String param1, String param2) {
        Cliente_pedidosFragment fragment = new Cliente_pedidosFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cliente_pedidos, container, false);
    }

    int idclinete = 0;
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        pedidos = new ArrayList<>();
        loadRecycler();
        getDataJSON();

    }

    private void loadRecycler() {
        recyclerView = (RecyclerView) getView().findViewById(R.id.pedidos_recycler_cliente);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        try {
            idclinete = Integer.parseInt(VistaClienteActivity.cliente.getString("idcliente"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter = new PedidosAdapterCliente(getActivity(),pedidos,idclinete);
        recyclerView.setAdapter(adapter);
    }

    //int idpedido, int idproducto, double clatitud, double clongitud, int idcliente, int idrepartidor
    public void getDataJSON(){
        pedidos.clear();
        String URL = "clinete/pedido/"+idclinete;
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
                                adapter.notifyDataSetChanged();
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
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Cliente_pedidosFragment.OnFragmentInteractionListener) {
            mListener = (Cliente_pedidosFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

