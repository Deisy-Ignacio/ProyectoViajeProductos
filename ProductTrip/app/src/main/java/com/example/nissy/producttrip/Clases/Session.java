package com.example.nissy.producttrip.Clases;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {

    private SharedPreferences prefs;

    public Session(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setcorreo(String correo) {
        prefs.edit().putString("correo", correo).commit();
    }

    public String getcorreo() {
        String usename = prefs.getString("correo","");
        return usename;
    }

    public void setcontrasena(String contrasena) {
        prefs.edit().putString("contrasena", contrasena).commit();
    }

    public String getcontrasena() {
        String contrasena = prefs.getString("contrasena","");
        return contrasena;
    }

    public void settipo(String tipo) {
        prefs.edit().putString("tipo", tipo).commit();
    }

    public String gettipo() {
        String tipo = prefs.getString("tipo","");
        return tipo;
    }
}
