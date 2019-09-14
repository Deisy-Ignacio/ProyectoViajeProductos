<?php
namespace App\Http\Models;
use Illuminate\Database\Eloquent\Model;

class MPedidoProducto extends Model{
    //nombre de la tabla
    protected $table = 'pedido';

    //llave primaria
    protected $primaryKey = 'idpedido';
    public $timestamps = false;

    //aqui los elementos a mostrarse en la tabla 
    protected $filltable = [
        'idpedido',
        'idproducto',
        'cantidad'
    ];
}