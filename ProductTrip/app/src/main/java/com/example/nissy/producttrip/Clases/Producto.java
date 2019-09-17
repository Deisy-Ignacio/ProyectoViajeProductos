package com.example.nissy.producttrip.Clases;

import java.io.Serializable;

public class Producto  implements Serializable {
    /**
     * ID Tienda
     * Nombre
     * Descripción
     * Precio
     * Stock
     */

    private  String mName;
    private int mCantidad;
    private int mQuantity;
    private int id;
    private String descripcion;
    private int stock;
    private Producto Producto;
    private int idTienda;


    public void setmQuantity(int mQuantity) {
        this.mQuantity = mQuantity;
    }

    public Producto(){}

    public Producto(int id,int idTienda,String mName,String descripcion, int mCantidad,int stock) {
        this.mName = mName;
        this.mCantidad = mCantidad;
        this.mQuantity = 1;
        this.descripcion = descripcion;
        this.id=id;
        this.stock = stock;
        this.idTienda = idTienda;
    }

    public String getmName() {
        return mName;
    }

    public int getmCantidad() {        return mCantidad;
    }

    public int getmQuantity(){
        return mQuantity;
    }

    public void addToQuantity(){
        this.mQuantity += 1;
    }
    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getStock() {
        return stock;
    }

    public void removeFromQuantity(){
        if(this.mQuantity > 1){
            this.mQuantity -= 1;
        }
    }

    public int getIdTienda() {
        return idTienda;
    }

    public void setIdTienda(int idTienda) {
        this.idTienda = idTienda;
    }

    public Producto getProduct(){return Producto;}
}
