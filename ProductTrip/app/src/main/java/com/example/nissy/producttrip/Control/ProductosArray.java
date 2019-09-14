package com.example.nissy.producttrip.Control;

import com.example.nissy.producttrip.Clases.Producto;

import java.util.ArrayList;

public class ProductosArray {
    private ArrayList<Producto> productosList;

    public ProductosArray() {
        productosList = new ArrayList<Producto>();

    }

    public ArrayList<Producto> agregarProducto(Producto p){
        productosList.add(p);
        System.out.println("sixxe: "+productosList.size());
        return productosList;
    }

    public ArrayList<Producto> eliminarProducto(Producto p){
        productosList.remove(p);
        return productosList;
    }

    public ArrayList<Producto> lista(){
        return productosList;
    }
}
