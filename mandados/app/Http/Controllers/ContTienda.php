<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\Http\Models\MTienda;
use Illuminate\Database\QueryException;

class ContTienda extends Controller{
    public function login(Request $req){
        $correo = $req->correo;
        $contrasena = $req->contrasena;
        $tienda = MTienda::select('*')
        ->where('correo','=', $correo)
        ->first();

        if(is_null($tienda)){
            return response()->json(['mensaje' => 'Correo no existe']);
        }elseif($correo == $tienda->correo && $contrasena == $tienda->contrasena){
            return response()->json([
                'mensaje' => 'ok',
                'tienda' => $tienda
                ]);
        }
        else{
            if($contrasena != $tienda->contrasena){
                return response()->json(['mensaje' => 'Contraseña incorrecta']);
            }else{
                return response()->json(['mensaje' => 'Correo o contraseña incorrectos']);
            }
        }
    }

    public function registro(Request $request){
        MTienda::insert([
            'nombre'=>$request->nombre,
            'giro'=>$request->giro,
            'direccion'=>$request->direccion,
            'correo' => $request->correo,
            'contrasena'=>$request->contrasena,
            'latitud'=>$request->latitud,
            'longitud'=>$request->longitud,
            'encargado'=>$request->encargado
        ]);
        return response()->json(['mensaje' => 'Se ha agregado con éxito']);
    }

    public function tiendas(){
        return response()->json(MTienda::select('*')->get());
    }
}