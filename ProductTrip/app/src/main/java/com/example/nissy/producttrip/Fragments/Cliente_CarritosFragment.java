package com.example.nissy.producttrip.Fragments;

import android.content.Context;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nissy.producttrip.Activities.VistaListaProductosActivity;
import com.example.nissy.producttrip.Adapter.OrderAdapter;
import com.example.nissy.producttrip.Clases.Producto;
import com.example.nissy.producttrip.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Cliente_CarritosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Cliente_CarritosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Cliente_CarritosFragment extends Fragment {
    TextView mealTotalText;
    ArrayList<Producto> orders;

    private OnFragmentInteractionListener mListener;

    public Cliente_CarritosFragment() {
    }

    public static Cliente_CarritosFragment newInstance(String param1, String param2) {
        Cliente_CarritosFragment fragment = new Cliente_CarritosFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        ListView storedOrders = (ListView)getView().findViewById(R.id.selected_producto_list);

        orders =  getListItemData();
        mealTotalText = (TextView)getView().findViewById(R.id.producto_total);
        OrderAdapter adapter = new OrderAdapter(getContext(), orders);

        storedOrders.setAdapter(adapter);
        adapter.registerDataSetObserver(observer);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cliente__carritos, container, false);
    }

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public int calculateMealTotal(){
        int mealTotal = 0;
        for(Producto order : orders){
            mealTotal += order.getmCantidad() * order.getmQuantity();
        }
        return mealTotal;
    }

    DataSetObserver observer = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            setMealTotal();
        }
    };

    private ArrayList<Producto> getListItemData(){
        ArrayList<Producto> listViewItems = new ArrayList<Producto>();
        listViewItems.add(new Producto(1,"Juguete","Rojo",30,5));
        listViewItems.add(new Producto(2,"Cuchara","plateada",40,7));
        listViewItems.add(new Producto(4,"Mouse","gamer",60,4));
        listViewItems.add(new Producto(3,"Pizza","Familiar",80,5));
        listViewItems.add(new Producto(6,"Vaso","vidrio",100,85));

        return listViewItems;
    }

    public void setMealTotal(){
        mealTotalText.setText("$"+" "+ calculateMealTotal());
    }
}
