<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\Http\Models\MRepartidor;
use Illuminate\Database\QueryException;

class ContRepartidor extends Controller{
    public function login(Request $req){
        $correo = $req->correo;
        $contrasena = $req->contrasena;
        $repartidor = MRepartidor::select('*')
        ->where('correo','=', $correo)
        ->first();

        if(is_null($repartidor)){
            return response()->json(['mensaje' => 'Correo no existe']);
        }elseif($correo == $repartidor->correo && $contrasena == $repartidor->contrasena){
            return response()->json([
                'mensaje' => 'ok',
                'repartidor' => $repartidor
                ]);
        }
        else{
            if($contrasena != $repartidor->contrasena){
                return response()->json(['mensaje' => 'Contraseña incorrecta']);
            }else{
                return response()->json(['mensaje' => 'Correo o contraseña incorrectos']);
            }
        }
    }

    public function registro(Request $request){
        $status = '1';
        $mensaje = 'Se ha agregado con exito';
        try{
            MRepartidor::insert([
                'nombre'=>$request->nombre,
                'apaterno'=>$request->apaterno,
                'apmaterno'=>$request->apmaterno,
                'direccion'=>$request->direccion,
                'telefono'=>$request->telefono,
                'correo' => $request->correo,
                'contrasena'=>$request->contrasena,
            ]);
        } catch(QueryException $ex){ 
            $status = '0';
            $mensaje = $ex;
            if($ex->errorInfo[0] == '23505'){
                $mensaje = 'El correo ya ha sido registrado';
            }
        }
        return response()
        ->json([
            'status' => $status,
            'mensaje' => $mensaje
        ]);
    }
}