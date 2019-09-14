<?php
namespace App\Http\Models;
use Illuminate\Database\Eloquent\Model;

class MProducto extends Model{
    //nombre de la tabla
    protected $table = 'producto';

    //llave primaria
    protected $primaryKey = 'idproducto';
    public $timestamps = false;

    //aqui los elementos a mostrarse en la tabla 
    protected $filltable = [
        'idproducto',
        'idtienda',
        'nombre',
        'descripcion',
        'precio',
        'stock'
    ];
}