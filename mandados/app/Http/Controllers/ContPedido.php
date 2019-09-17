<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\Http\Models\MPedido;
use Illuminate\Support\Facades\DB;
use Illuminate\Database\QueryException;

class ContPedido extends Controller{
    public function nuevo(Request $request){
        MPedido::insert([
            'idcliente'=>$request->idcliente,
            'idproducto'=>$request->idproducto,
            'clatitud'=>$request->latitud,
            'clongitud'=>$request->longitud,
            'entregado'=>False,
            'pagado'=>True,
            'fecha'=>date("d/m/Y")
        ]);
        return response()->json(['mensaje' => 'Se ha agregado con éxito']);
    }

    public function sinconfirmar(Request $request){
        $pedidos = MPEdido::select('*')
        ->where('idrepartidor','=',$request->idrepartidor)
        ->where('entregado','=',false)->get()->first();
        return response()->json($pedidos);
    }

    public function asignarRepartidor(Request $request){
        $pedidos = MPEdido::select('*')
        ->where('idrepartidor','=',$request->idrepartidor)
        ->where('entregado','=',false)->get()->first();

        if(is_null($pedidos)){
            $editar = MPedido::find($request->idpedido);
            if($editar->idrepartidor == '' or is_null($editar->idrepartidor)){
                $editar->idrepartidor = $request->idrepartidor;
                $editar->rlatitud = $request->rlatitud;
                $editar->rlongitud = $request->rlongitud;
                $editar->save();
                return response()->json(['status'=>'1','mensaje'=>'Haz aceptado el pedido']);
            }else{
                return response()->json(['status'=>'0','mensaje'=>'Te han ganado el pedido :c']);
            }
        }else{
            return response()->json(['status'=>'2','mensaje'=>'Tienes pedidos activos']);
        }
    }

    public function actualizarUbicacion(Request $request){
        $editar = MPedido::find($request->idpedido);
        if($editar->confirmado == False){
            $editar->rlatitud = $request->rlatitud;
            $editar->rlongitud = $request->rlongitud;
            $editar->save();
            return response()->json(['status'=>'1','mensaje'=>'Sigue avanzando']);
        }else{
            return response()->json(['status'=>'0','mensaje'=>'Ya se ha confirmado tu pedido']);
        }
    }

    public function ubicacionPedido($idpedido){
        $editar = MPedido::find($idpedido);
        return response()->json(['status'=>$editar->entregado,'latitud'=>$editar->rlatitud,'longitud'=>$editar->rlongitud]);
        
    }

    public function confirmarEntrega(Request $request){
        $editar = MPedido::find($request->idpedido);
        $editar->entregado = True;
        $editar->save();
        return response()->json(['mensaje'=>'Gracias por confirmar la entrega']);
    }

    public function pedidosRepartidor(){
        $productos = DB::select("select pe.idpedido, pro.idproducto, pro.nombre as nombre_producto, 
        pro.descripcion, pe.clatitud, pe.clongitud,
        ti.idtienda, ti.nombre as nombre_tienda, cli.idcliente, cli.nombre as nombre_cliente
        from pedido as pe 
        inner join producto as pro on pe.idproducto = pro.idproducto 
        inner join tienda as ti on pro.idtienda = ti.idtienda
        inner join cliente as cli on pe.idcliente = cli.idcliente
        where idrepartidor is null;");  
        return response()->json($productos);
    }

    public function pedidosActualesCliente($idcliente){
        $productos = DB::select("select pe.idpedido, pro.idproducto, pro.nombre as nombre_producto, 
        pro.descripcion, pe.clatitud, pe.clongitud,
        ti.idtienda, ti.nombre as nombre_tienda, cli.idcliente, cli.nombre as nombre_cliente
        from pedido as pe 
        inner join producto as pro on pe.idproducto = pro.idproducto 
        inner join tienda as ti on pro.idtienda = ti.idtienda
        inner join cliente as cli on pe.idcliente = cli.idcliente
        where pe.idcliente = ? and pe.entregado = false;
        ",[$idcliente]);  
        return response()->json($productos);
    }
}