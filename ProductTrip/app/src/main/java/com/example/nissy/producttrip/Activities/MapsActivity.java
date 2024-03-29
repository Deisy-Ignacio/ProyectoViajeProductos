package com.example.nissy.producttrip.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.nissy.producttrip.Clases.Pedido;
import com.example.nissy.producttrip.R;
import com.example.nissy.producttrip.conexion.VolleySingleton;
import com.example.nissy.producttrip.directioshelpers.FetchURL;
import com.example.nissy.producttrip.directioshelpers.TaskLoadedCallback;
import com.example.nissy.producttrip.directioshelpers.mapsPojos;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback , TaskLoadedCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;//ayuda a encontrar la ultima ubicacion conocida
    Location mCurrentLocation;//variable para almacenar la ubicacion actual
    MarkerOptions posActual, marca1;//para manejar las coordenadas de la posicion actual
    Polyline actualPolyline;//para dibujar la ruta
    DatabaseReference mDatabase;//referencia a la BD de Firbase
    private Marker marcador;//Marcador para la ubicacion
    double lat = 0.0, lon = 0.0;//variable para guardar latitud y longitud de la posicion actual
    double clatitud = 0.0, clongitud = 0.0;
    int idpedido;
    boolean status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        status = false;

        idpedido = Integer.parseInt(getIntent().getStringExtra("idpedido"));
        clatitud = Double.parseDouble(getIntent().getStringExtra("clatitud"));
        clongitud = Double.parseDouble(getIntent().getStringExtra("clongitud"));

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        Thread actualizacoord = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!status){
                    getRepartidorLocation();
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        actualizacoord.start();

    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        marca1 = new MarkerOptions().position(new LatLng(clatitud, clongitud)).title("Yo");
        mMap.addMarker(marca1);
        miUbicacion();
    }

    /**
     * "agregarMarcador" sirve para agregar el marcador al mapa, ademas crea un objeto de tipo
     * latlng donde se guardan las coordenadss
     */
    private void agregarMarcador(double lat, double lon) {
        LatLng coordenadas = new LatLng(lat, lon);
        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 16);
        if (marcador != null)
            marcador.remove();
        marcador = mMap.addMarker(new MarkerOptions()//se agrega el marcador y se confguran alguna cosas
                .position(coordenadas)
                .title("Repartidor"));
        mMap.animateCamera(miUbicacion);

        /**
         * se llama el metodo para crear la URL con la cual se hace uso de la la API de direcciones
         * de google, y se calcula la ruta
         */
        String url = getUrl(marca1.getPosition(), marcador.getPosition(),"driving");
        new FetchURL(MapsActivity.this).execute(url, "driving");

    }

    public void getRepartidorLocation(){
        String URL = "ubicacion/pedido/"+idpedido;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                LoginActivity.BASE_URL+URL,
                null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String lat = response.getString("latitud");
                            if(lat != null) {
                                double latitud = Double.parseDouble(lat);
                                double longitud = Double.parseDouble(response.getString("longitud"));
                                status = Boolean.parseBoolean(response.getString("status"));
                                actualizarUbicacion(latitud, longitud);
                            }
                            else{
                                Toast.makeText(MapsActivity.this,"El repartidor aún no comienza el viaje",Toast.LENGTH_SHORT).show();
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
        VolleySingleton.getInstance(MapsActivity.this).addToRequestQueue(jsonObjectRequest);
    }

    private void actualizarUbicacion(double rlatitud, double rlongitud) {
        agregarMarcador(rlatitud, rlongitud);
    }

    /**
     * Se implementa un Objeto de tipo LocationListener, del cual usaremos el metodo
     * onLocationChanged el cual se ejecuta cada vez que se detecta un cambio en la ubicacion
     * cada que se ejecute llamamos el metodo actualizarUbicacion
     */


    /**
     * Este metodo pide permisos al usuario para poder usar el GPS ya que es autorizado busca la
     * ultima ubicacion conocida y la guarda en un objeto de tipo Location y actualiza su ubicacion
     * en el mapa, esto lo hace cada 2 segundos. Este valor del iempo se puede ajustar
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void miUbicacion() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
    }

    /**
     * "getUrl" se encarga de armar la url para hacer uso de la API de direcciones de google
     */
    private String getUrl(LatLng origen, LatLng destino, String transporte) {
        //coordenadas origen
        String str_origen = "origin=" + origen.latitude + "," + origen.longitude;
        //coordenadas destino
        String str_destino = "destination=" + destino.latitude + "," + destino.longitude;
        //Modo de Transporte
        String mTransporte = "mode=" + transporte;
        String parametros = str_origen + "&" + str_destino + "&" + mTransporte;
        //String salida = "json";
        //URL Final
        String url = "https://maps.googleapis.com/maps/api/directions/json?" + parametros + "&key=" + "AIzaSyC7B5_V6BakLh4_k5hgQ9ppm5QEXC79HAY";
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (actualPolyline != null)
            actualPolyline.remove();
        actualPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }
}
