<?php
namespace App\Http\Models;
use Illuminate\Database\Eloquent\Model;

class MRepartidor extends Model{
    //nombre de la tabla
    protected $table = 'repartidor';

    //llave primaria
    protected $primaryKey = 'idrepartidor';
    public $timestamps = false;

    //aqui los elementos a mostrarse en la tabla 
    protected $filltable = [
        'idrepartidor',
        'nombre',
        'apaterno',
        'apmaterno',
        'direccion',
        'telefono',
        'correo',
        'contrasena',
    ];
}