<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\Http\Models\MProducto;
use Illuminate\Database\QueryException;

class ContProducto extends Controller{
    public function registro(Request $request){
        MProducto::insert([
            'idtienda'=>$request->idtienda,
            'nombre'=>$request->nombre,
            'descripcion'=>$request->descripcion,
            'precio'=>$request->precio,
            'stock'=>$request->stock
        ]);
        return response()->json(['mensaje' => 'Se ha agregado con éxito']);
    }

    public function productos($idtienda){
        $productos = MProducto::select('*')->where('idtienda','=',$idtienda)->get();
        return response()->json($productos);
    }
}