//funciones de los pasos para el check out
	function checkout_paso_0(){
		//mostrar al usuario un formulario donde insertar la informacion de envio
		$("#contenedor").html(plantillaCheckout_1);
		$("#aceptar_paso_1").click(checkout_paso_1_aceptar); 	
	}//end checkout_paso_0

	function checkout_paso_1_aceptar(){
		//recoger los valores introducidos y mandarlos al servidor
		var nombre = $("#campo_nombre").val();
		var email = $("#campo_email").val();
		var direccion = $("#campo_direccion").val();
		var provincia = $("#campo_provincia").val();
		var codigo_postal = $("#campo_codigo_postal").val();
		
		//validar formulario
		if(!validarNombre($("#campo_nombre").val()) || !validarEmail($("#campo_email").val()) || !validarAlfaNum($("#campo_direccion").val()) || 
		!validarAlfaNum($("#campo_provincia").val()) || !validarNum($("#campo_codigo_postal").val())){
			event.preventDefault();
			return;
		}
		//mandar los valores al servicio web de pedidos
		$.post("servicioWebPedidos/paso1",
				{
					nombre: nombre,
					email: email,
					direccion: direccion,
					provincia: provincia,
					codigoPostal: codigo_postal,
					//esto se pone como lo tenemos en Servicio web pedidos codigoPostal y codigo_postal es como lo tenemos aqui var codigo_postal
				}
		).done(function(res){
			if(res == "ok"){
				$("#contenedor").html(plantillaCheckout_2);
				$("#checkout2_aceptar").click(checkout_paso_2_aceptar);
			}else{
				alert(res);
			}
		});//end done	
	}//end checkout_paso_1_aceptar
	
function checkout_paso_2_aceptar(){
	var tipo_tarjeta = $("#tipo_tarjeta").find(":selected").val();
	var numero_tarjeta = $("#numero_tarjeta").val(); //esto es como esta en HTML
	var titular_tarjeta = $("#titular_tarjeta").val();
	var expiracion = $("#expiracion").val();
	var cvv = $("#cvv").val();
	//validar 
	if( !validarNum($("#numero_tarjeta").val()) || !validarNombre($("#titular_tarjeta").val()) || !validarFecha($("#expiracion").val()) || 
		 !validarNum($("#cvv").val())){
			event.preventDefault();
			return;
		}
	$.post("servicioWebPedidos/paso2", {
		tarjeta: tipo_tarjeta, //1 servicio web y 2 es var en js
		numero: numero_tarjeta,
		titular: titular_tarjeta,
		expiracion: expiracion,
		cvv: cvv
	}).done(function(res){
			if(res == "ok"){
				$("#contenedor").html(plantillaCheckout_2_medio);
				$("#checkout2_medio_aceptar").click(checkout_paso_2_medio_aceptar);
			}else{
				alert(res);
			}
		});//end done
}//end checkout_paso_2_aceptar

function checkout_paso_2_medio_aceptar(){
	var telefono = $("#telefono").val();
	var codigo_descuento = $("#codigo_descuento").val(); //esto es como esta en HTML
	//validar 
	if( !validarNum($("#telefono").val())){
			event.preventDefault();
			return;
		}
    if (codigo_descuento === "holaInvierno") {
        alert("Se aplicará un descuento del 15% en tu pedido.");
    }
	$.post("servicioWebPedidos/paso2_medio", {
		telefono: telefono, //1 servicio web y 2 es var en js
		codigo_descuento: codigo_descuento,
	}).done(function(res){
		var resumen_pedido = res;//Ya nos llega en JSON no necesitamos parsearlo
		var html = Mustache.render(plantillaCheckout_3, resumen_pedido)
		 $("#contenedor").html(html);
		 $("#boton_confirmar_pedido").click(function(){
			 $.ajax("servicioWebPedidos/paso3", {
				success: function(res){
					alert("respuesta del servicio web: " +res);
					mostrar_articulos();
				} 
			 });//end servicioWebPedidos/paso3
		 })//end function boton_confirmar_pedido
	});//end function done
}//end checkout_paso_2_aceptar



