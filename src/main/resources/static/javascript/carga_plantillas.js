// usando jquery pedimos el contenido de inicio.html y se lo metemos 	
	//a la variable plantillaInicio
	$.get("plantillas_mustache/inicio.html", function(data){
		plantillaInicio = data;
		setTimeout(mostrar_inicio, 500);
	});
	
	$.get("plantillas_mustache/articulos.html", function(data){
		plantillaArticulos = data;
	});
	
	$.get("plantillas_mustache/registro.html", function(data){
		plantillaRegistro = data;
	});
	
	$.get("plantillas_mustache/identificar_usuario.html", function(data){
		plantillaIdentificarUsuario = data;
	});
	
	$.get("plantillas_mustache/carrito.html",function(data){
		plantillaCarrito = data;
	});
	
	$.get("plantillas_mustache/detalles_articulo.html",function(data){
		plantillaDetallesArticulo = data;
	});
	
	$.get("plantillas_mustache/check_out_1.html",function(data){
		plantillaCheckout_1 = data;
	});
	
	$.get("plantillas_mustache/check_out_2.html",function(data){
	plantillaCheckout_2 = data;
	});
	
	$.get("plantillas_mustache/check_out_2_medio.html",function(data){
	plantillaCheckout_2_medio = data;
	});
	
	$.get("plantillas_mustache/check_out_3.html",function(data){
	plantillaCheckout_3 = data;
	});
	
	$.get("plantillas_mustache/mis_pedidos.html",function(data){
	plantillaMisPedidos = data;
	});
	
	$.get("plantillas_mustache/mis_pedidos_detalle.html",function(data){
	plantillaMisPedidosDetalle = data;
	});
	
	$.get("plantillas_mustache/mis_datos.html",function(data){
	plantillaMisDatos = data;
	});
	
	$.get("plantillas_mustache/mis_datos_editar.html",function(data){
	plantillaMisDatosEditar = data;
	});
	
	$.get("plantillas_mustache/crear_valoracion.html",function(data){
	plantillaCrearValoraciones = data;
	});
	
	
	