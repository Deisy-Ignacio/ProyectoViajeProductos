<?php
namespace App\Http\Models;
use Illuminate\Database\Eloquent\Model;

class MTienda extends Model{
    //nombre de la tabla
    protected $table = 'tienda';

    //llave primaria
    protected $primaryKey = 'idtienda';
    public $timestamps = false;

    //aqui los elementos a mostrarse en la tabla 
    protected $filltable = [
        'idtienda',
        'nombre',
        'giro',
        'direccion',
        'latitud',
        'longitud',
        'correo',
        'contrasena',
        'encargado'
    ];
}