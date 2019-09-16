<?php

use Illuminate\Http\Request;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:api')->get('/user', function (Request $request) {
    return $request->user();
});

Route::group(['prefix'=>'auth'], function(){
    Route::post('login/cliente','ContCliente@login');
    Route::post('registro/cliente','ContCliente@registro');

    Route::post('login/tienda','ContTienda@login');
    Route::post('registro/tienda','ContTienda@registro');
    Route::get('all/tienda','ContTienda@tiendas');

    Route::post('login/repartidor','ContRepartidor@login');
    Route::post('registro/repartidor','ContRepartidor@registro');

    Route::get('all/producto/{idtienda}','ContProducto@productos');
    Route::post('registro/producto','ContProducto@registro');

    Route::get('disponibles/pedido','ContPedido@pedidosRepartidor');
    Route::post('asignar/pedido','ContPedido@asignarRepartidor');
    Route::post('sin/pedido','ContPedido@sinconfirmar');
    //asignarRepartidor
});
