package com.example.nissy.producttrip.Activities;

import android.Manifest;
import android.content.Context;
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

import com.example.nissy.producttrip.R;
import com.example.nissy.producttrip.directioshelpers.FetchURL;
import com.example.nissy.producttrip.directioshelpers.TaskLoadedCallback;
import com.example.nissy.producttrip.directioshelpers.mapsPojos;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import java.util.HashMap;
import java.util.Map;

public class MapsActivityRepartidor extends FragmentActivity implements OnMapReadyCallback, TaskLoadedCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;//ayuda a encontrar la ultima ubicacion conocida
    Location mCurrentLocation;//variable para almacenar la ubicacion actual
    MarkerOptions posActual, marca1;//para manejar las coordenadas de la posicion actual
    Polyline actualPolyline;//para dibujar la ruta
    DatabaseReference mDatabase;//referencia a la BD de Firbase
    private Marker marcador;//Marcador para la ubicacion
    double lat = 0.0, lon = 0.0;//variable para guardar latitud y longitud de la posicion actual
    double clatitud = 0.0, clongitud = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_repartidor);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        /**
         * Instanciacion de firebase ey creacion de getFusedLocationProviderClient para obtener la
         * ubicacion y subirla a firebase
         */
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        /**
         * Para detectar cambios en la BD
         */
        mDatabase.child("coordenadas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    mapsPojos mp = snapshot.getValue(mapsPojos.class);
                    Double latitud = mp.getLatitud();
                    Double longitud = mp.getLongitud();
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(latitud, longitud));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    /**
     * Este metodo obtiene la ultima posiscion conocida del dispositivo y los manda a firebase
     */
    //POST
    /*private void subirLatLongFirebase() {
        mFusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            Log.e("Latitud", location.getLatitude() + "Longitud" + location.getLongitude());//imprime lat y long

                            mCurrentLocation = location;
                            Map<String, Object> latlang = new HashMap<>();//codigo para realizar un push a firebase con las coodenadas del dispositivo
                            latlang.put("latitud", location.getLatitude());
                            latlang.put("longitud", location.getLongitude());
                            mDatabase.child("coordenadas").push().setValue(latlang);
                        }
                    }
                });
    }*/

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        marca1 = new MarkerOptions().position(new LatLng(17.06688, -96.7146414)).title("Tienda1");
        mMap.addMarker(marca1);
        //mMap.addMarker(posActual);
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
                .title("PosicionActual"));
        mMap.animateCamera(miUbicacion);

        /**
         * se llama el metodo para crear la URL con la cual se hace uso de la la API de direcciones
         * de google, y se calcula la ruta
         */
        String url = getUrl(marca1.getPosition(), marcador.getPosition(),"driving");
        new FetchURL(MapsActivityRepartidor.this).execute(url, "driving");

    }

    /**
     * "actualizarUbicacion" sirve para obtener las coordenadas de la ubicacion y luego las almacena
     * en las variables globales lat lon, comprobando primero que la ubicacion no sea igual a null
     * para evitar que se cierre la aplicacion por ultimo llama al metodo agregar marcador para
     * dibujarlo*/
    private void actualizarUbicacion(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lon = location.getLongitude();
            agregarMarcador(lat, lon);
            /**
             * llamando al metodo para guardar valores de latlon en firebase
             */
            //subirLatLongFirebase();
        }

    }

    /**
     * Se implementa un Objeto de tipo LocationListener, del cual usaremos el metodo
     * onLocationChanged el cual se ejecuta cada vez que se detecta un cambio en la ubicacion
     * cada que se ejecute llamamos el metodo actualizarUbicacion
     */
    LocationListener locListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            actualizarUbicacion(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    /**
     * Este metodo pide permisos al usuario para poder usar el GPS ya que es autorizado busca la
     * ultima ubicacion conocida y la guarda en un objeto de tipo Location y actualiza su ubicacion
     * en el mapa, esto lo hace cada 2 segundos. Este valor del iempo se puede ajustar
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void miUbicacion() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualizarUbicacion(location);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,2000,0,locListener);
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




    //metodo para escribir los marcadores
    /*public void marcadores(GoogleMap googleMap){
        mMap = googleMap;

        final LatLng marca1 = new LatLng(17.06688,-96.7146414);
        mMap.addMarker(new MarkerOptions().position(marca1).title("Una pinshe tienda"));
    }*/
}



