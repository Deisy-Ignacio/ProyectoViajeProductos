<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\Http\Models\MCliente;
use Illuminate\Database\QueryException;

class ContCliente extends Controller{
    public function login(Request $req){
        $correo = $req->correo;
        $contrasena = $req->contrasena;
        $cliente = MCliente::select('*')
        ->where('correo','=', $correo)
        ->first();

        if(is_null($cliente)){
            return response()->json(['mensaje' => 'Correo no existe']);
        }elseif($correo == $cliente->correo && $contrasena == $cliente->contrasena){
            return response()->json([
                'mensaje' => 'ok',
                'cliente' => $cliente
                ]);
        }
        else{
            if($contrasena != $cliente->contrasena){
                return response()->json(['mensaje' => 'Contraseña incorrecta']);
            }else{
                return response()->json(['mensaje' => 'Correo o contraseña incorrectos']);
            }
        }
    }

    public function registro(Request $request){
        MCliente::insert([
            'nombre'=>$request->nombre,
            'apaterno'=>$request->apaterno,
            'apmaterno'=>$request->apmaterno,
            'direccion'=>$request->direccion,
            'telefono'=>$request->telefono,
            'correo' => $request->correo,
            'contrasena'=>$request->contrasena,
            'latitud'=>$request->latitud,
            'longitud'=>$request->longitud
        ]);

        return response()->json([
            'status' => '1',
            'mensaje' => 'Se ha agregado con éxito'
            ]);
    }
}