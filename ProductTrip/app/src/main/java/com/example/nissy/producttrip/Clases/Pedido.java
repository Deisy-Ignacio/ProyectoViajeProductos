package com.example.nissy.producttrip.Clases;

import android.util.Log;

public class Pedido {
    private int idpedido;
    private int idproducto;
    private String nombre_producto;
    private double clatitud;
    private double clongitud;
    private double rlatitud;
    private double rlongitud;
    private int idtienda;
    private String nombre_tienda;
    private int idcliente;
    private String nombre_cliente;



    private int idrepartidor;

    public Pedido(int idpedido, int idproducto, double clatitud, double clongitud, int idcliente, int idrepartidor) {
        this.idpedido = idpedido;
        this.idproducto = idproducto;
        this.clatitud = clatitud;
        this.clongitud = clongitud;
        this.idcliente = idcliente;
        this.idrepartidor = idrepartidor;
    }

    public Pedido(int idpedido, int idproducto, String nombre_producto, double clatitud, double clongitud, double rlatitud, double rlongitud, int idtienda, String nombre_tienda, int idcliente, String nombre_cliente) {
        this.idpedido = idpedido;
        this.idproducto = idproducto;
        this.nombre_producto = nombre_producto;
        this.clatitud = clatitud;
        this.clongitud = clongitud;
        this.rlatitud = rlatitud;
        this.rlongitud = rlongitud;
        this.idtienda = idtienda;
        this.nombre_tienda = nombre_tienda;
        this.idcliente = idcliente;
        this.nombre_cliente = nombre_cliente;
    }




    public Pedido(int idpedido, int idproducto, String nombre_producto, double clatitud, double clongitud, int idtienda, String nombre_tienda, int idcliente, String nombre_cliente) {
        this.idpedido = idpedido;
        this.idproducto = idproducto;
        this.nombre_producto = nombre_producto;
        this.clatitud = clatitud;
        this.clongitud = clongitud;
        this.idtienda = idtienda;
        this.nombre_tienda = nombre_tienda;
        this.idcliente = idcliente;
        this.nombre_cliente = nombre_cliente;
    }

    public int getIdpedido() {
        return idpedido;
    }

    public int getIdrepartidor() {
        return idrepartidor;
    }

    public void setIdrepartidor(int idrepartidor) {
        this.idrepartidor = idrepartidor;
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

    public double getClatitud() {
        return clatitud;
    }

    public void setClatitud(double clatitud) {
        this.clatitud = clatitud;
    }

    public double getClongitud() {
        return clongitud;
    }

    public void setClongitud(double clongitud) {
        this.clongitud = clongitud;
    }

    public double getRlatitud() {
        return rlatitud;
    }

    public void setRlatitud(double rlatitud) {
        this.rlatitud = rlatitud;
    }

    public double getRlongitud() {
        return rlongitud;
    }

    public void setRlongitud(double rlongitud) {
        this.rlongitud = rlongitud;
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
