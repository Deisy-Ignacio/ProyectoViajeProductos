<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\Http\Models\MPedido;
use Illuminate\Database\QueryException;

class ContPedido extends Controller{
    public function nuevo(Request $request){
        MPedido::insert([
            'idcliente'=>$request->idcliente,
            'idtienda'=>$request->idtienda,
            'latitud'=>$request->latitud,
            'longitud'=>$request->longitud,
            'descripcion'=>$request->descripcion,
            'fecha'=>date("d/m/Y")
        ]);
        return response()->json(['mensaje' => 'Se ha agregado con éxito']);
    }

    public function asignarRepartidor(Request $request){

        $editar = MPedido::find($request->idpedido);
        $editar->idrepartidor = $request->idrepartidor;
        $editar->save();
        return response()->json($editar);
    }

    public function confirmarEntrega(Request $request){
        $editar = MPedido::find($request->idpedido);
        $editar->entregado = 1;
        $editar->save();
        return response()->json(['mensaje'=>'Gracias por confirmar la entrega']);
    }

    public function pedidosRepartidor(){
        $productos = MPedido::select('*')
        ->where('idrepartidor','=',null)
        ->get();
        return response()->json($productos);
    }
}