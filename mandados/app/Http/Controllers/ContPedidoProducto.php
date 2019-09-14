<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\Http\Models\MPedidoProducto;
use Illuminate\Database\QueryException;

class ContProducto extends Controller{
    public function registro(Request $request){
        MProducto::insert([
            'idpedido'=>$request->idtienda,
            'idproducto'=>$request->nombre,
            'cantidad'=>$request->descripcion
        ]);
        return response()->json(['mensaje' => 'Se ha agregado con éxito']);
    }
}