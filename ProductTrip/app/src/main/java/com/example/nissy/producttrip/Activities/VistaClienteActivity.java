package com.example.nissy.producttrip.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.nissy.producttrip.Fragments.ClienteTiendaFragment;
import com.example.nissy.producttrip.Fragments.Cliente_CarritosFragment;
import com.example.nissy.producttrip.Fragments.Cliente_pedidosFragment;
import com.example.nissy.producttrip.R;

import java.util.ArrayList;

public class VistaClienteActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Cliente_pedidosFragment.OnFragmentInteractionListener,
        Cliente_CarritosFragment.OnFragmentInteractionListener, ClienteTiendaFragment.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_cliente);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Fragment miFragment = new ClienteTiendaFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_vista_cliente,miFragment).commit();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            Intent inten = new Intent();
            inten.setClass(VistaClienteActivity.this,LoginActivity.class);
            startActivity(inten);
            finish();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.vista_cliente, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment miFragment = null;
        boolean fragmentSeleccionaldo = false;
        int id = item.getItemId();
        if (id == R.id.store) {
            miFragment = new ClienteTiendaFragment();
            fragmentSeleccionaldo = true;
        }
        else if (id == R.id.carrito) {
            miFragment = new Cliente_CarritosFragment();
            fragmentSeleccionaldo = true;
        } else if (id == R.id.pedidos) {
            miFragment = new Cliente_pedidosFragment();
            fragmentSeleccionaldo = true;

        }
        if(fragmentSeleccionaldo){
            getSupportFragmentManager().beginTransaction().replace(R.id.content_vista_cliente,miFragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
