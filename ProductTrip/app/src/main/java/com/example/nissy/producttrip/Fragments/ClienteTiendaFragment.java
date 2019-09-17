package com.example.nissy.producttrip.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.nissy.producttrip.Activities.LoginActivity;
import com.example.nissy.producttrip.Activities.VistaListaProductosActivity;
import com.example.nissy.producttrip.R;
import com.example.nissy.producttrip.conexion.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ClienteTiendaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClienteTiendaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClienteTiendaFragment extends Fragment {
    private ListView listView;
    private ArrayList<String> nombreTienda  = new ArrayList<>();
    private ArrayList<String> idTienda  = new ArrayList<>();
    private ArrayList<String> coordenadas  = new ArrayList<>();
    ArrayAdapter<String> adaptador;

    private OnFragmentInteractionListener mListener;

    public ClienteTiendaFragment() {

    }
    // TODO: Rename and change types and number of parameters
    public static ClienteTiendaFragment newInstance(String param1, String param2) {
        ClienteTiendaFragment fragment = new ClienteTiendaFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private int getCategoryPos(String category) {
        return nombreTienda.indexOf(category);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_cliente_tienda, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        getDataJSON();
        listView = (ListView)getView().findViewById(R.id.ListViewTienda);
        adaptador = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,nombreTienda);

        listView.setAdapter(adaptador);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), VistaListaProductosActivity.class); //PASAR A OTRA ACTIVIDAD JIJIJ
                intent.putExtra("nombre",nombreTienda.get(i));
                intent.putExtra("idtienda",idTienda.get(i));
                intent.putExtra("coordenadas",coordenadas.get(i));
                startActivity(intent);
            }
        });
    }

    public void getDataJSON(){
        String URL = "all/tienda";
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, LoginActivity.BASE_URL+URL, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            //Log.i("VOLLEY",response.toString());
                            for (int i = 0; i<response.length(); i++) {
                                JSONObject datos = response.getJSONObject(i);
                                nombreTienda.add(datos.getString("nombre"));
                                idTienda.add(datos.getString("idtienda"));
                                coordenadas.add(datos.getString("latitud")+","+datos.getString("longitud"));
                                adaptador.notifyDataSetChanged();
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
