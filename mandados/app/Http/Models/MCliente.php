<?php
namespace App\Http\Models;
use Illuminate\Database\Eloquent\Model;

class MCliente extends Model{
    //nombre de la tabla
    protected $table = 'cliente';

    //llave primaria
    protected $primaryKey = 'idcliente';
    public $timestamps = false;

    //aqui los elementos a mostrarse en la tabla 
    protected $filltable = [
        'idcliente',
        'nombre',
        'apaterno',
        'apmaterno',
        'direccion',
        'telefono',
        'correo',
        'contrasena',
        'latitud',
        'longitud'
    ];
}