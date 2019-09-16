package com.example.nissy.producttrip.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.nissy.producttrip.Activities.VistaListaProductosActivity;
import com.example.nissy.producttrip.R;

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
    private ArrayList<String> nombreTienda  = new ArrayList<String>();

    private OnFragmentInteractionListener mListener;

    public ClienteTiendaFragment() {
        // Required empty public constructor
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
        listView = (ListView)getView().findViewById(R.id.ListViewTienda);
        nombreTienda.add("Abarrotes Mary");
        nombreTienda.add("Miscelanea Paco");
        nombreTienda.add("Walmart");
        nombreTienda.add("SHEIN");
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,nombreTienda);
        listView.setAdapter(adaptador);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), VistaListaProductosActivity.class); //PASAR A OTRA ACTIVIDAD JIJIJ
                intent.putExtra("nombre",nombreTienda.get(i));
                intent.putExtra("ID",i);
                startActivity(intent);
            }
        });
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
