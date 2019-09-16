package com.example.nissy.producttrip.Clases;

import android.util.Log;

public class Pedido {
    private int idpedido;
    private int idproducto;
    private String nombre_producto;
    private String descripcion;
    private double latitud;
    private double longitud;
    private int idtienda;
    private String nombre_tienda;
    private int idcliente;
    private String nombre_cliente;

    public Pedido(int idpedido, int idproducto, String nombre_producto, String descripcion, double latitud, double longitud, int idtienda, String nombre_tienda, int idcliente, String nombre_cliente) {
        Log.i("VOLLEY","CLASS PEDIDO: idpedido" + idpedido);
        this.idpedido = idpedido;
        this.idproducto = idproducto;
        this.nombre_producto = nombre_producto;
        this.descripcion = descripcion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.idtienda = idtienda;
        this.nombre_tienda = nombre_tienda;
        this.idcliente = idcliente;
        this.nombre_cliente = nombre_cliente;
    }

    public int getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(int idpedido) {
        this.idpedido = idpedido;
    }

    public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public int getIdtienda() {
        return idtienda;
    }

    public void setIdtienda(int idtienda) {
        this.idtienda = idtienda;
    }

    public String getNombre_tienda() {
        return nombre_tienda;
    }

    public void setNombre_tienda(String nombre_tienda) {
        this.nombre_tienda = nombre_tienda;
    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }
}
