let nombre_a_buscar = "";
let comienzo_resultados = 0;

function mostrar_articulos(){
	$.getJSON("servicioWebArticulos/obtenerArticulos", {nombre : nombre_a_buscar, comienzo : comienzo_resultados}).done(function(res){
	//usar una plantilla de mustache para listar los Articulos
	let texto_html = "";
	let articulos = res.articulos;
	//ante de mostrar el resultado usando la plantilla
	for(i in articulos){
		articulos[i].fecha_hora_actual = new Date();
		articulos[i].precio = articulos[i].precio.toString().replace(".", ",");
	}
	texto_html = Mustache.render(plantillaArticulos, articulos);
	$("#contenedor").html(texto_html);
	//para mantener la busqueda
	$("#nombre_buscador").val(nombre_a_buscar);
	$("#nombre_buscador").focus();
	//indicar que hace el buscador
	$("#nombre_buscador").keyup(function(e){
		nombre_a_buscar = $(this).val();
		comienzo_resultados = 0;//resetea la paginacion
		mostrar_articulos();
	});//end nombre_buscador
	if(comienzo_resultados <= 0){
		$("#enlace_anterior").hide();
	}else{
		$("#enlace_anterior").show();
	}//ocultar enlace de anterior
	let totalArticulos = res.totalArticulos;
	$("#total_resultados").html(totalArticulos);
	if((comienzo_resultados + 9) < totalArticulos){
		$("#enlace_siguiente").show(); 
	}else{
		$("#enlace_siguiente").hide();
	}//ocultar enlace de anterior
	$("#enlace_anterior").click(function(e){
		comienzo_resultados -= 9;
		mostrar_articulos();
	});//end enlace_anterior
	$("#enlace_siguiente").click(function(e){
		comienzo_resultados += 9;
		mostrar_articulos();
	});//end enlace_anterior		
	//indicar que hace el enlace comprar:
	$(".enlace_comprar_listado_principal").click(function(res){//sacamo esto de articulos.html 
		if(nombre_login != ""){
			var id_producto = $(this).attr("id-producto");//sacamo esto de articulos.html
			alert("agregar producto con id: " + id_producto + " al carrito");
			$.post("servicioWebCarrito/agregarArticulo", {//esto es en servicioWebCarrito
				id: id_producto,
				cantidad: 1
			}).done(function(res){
				alert(res);
			});//end function
		}else{
			alert("tienes que identificarte para comprar");
		}//end if
	});//end enlace_comprar_listado_principal
			
	//indicar que hace el enlace ver detalles
	$(".enlace_ver_detalles_articulo").click(function(){//esto lo sacamos de articulos.html
	var id_producto = $(this).attr("id-producto");
	$.getJSON("servicioWebArticulos/obtenerArticuloDetalles",
			{
				id: id_producto
			}
	).done(function(res){
		var html = Mustache.render(plantillaDetallesArticulo, res);
		$("#contenedor").html(html);
		$(".enlace_comprar_listado_principal").click(function(res){//sacamo esto de articulos.html 
		if(nombre_login != ""){
			var id_producto = $(this).attr("id-producto");//sacamo esto de articulos.html
			alert("agregar producto con id: " + id_producto + " al carrito");
			$.post("servicioWebCarrito/agregarArticulo", {//esto es en servicioWebCarrito
				id: id_producto,
				cantidad: 1
			}).done(function(res){
				alert(res);
			});//end function
		}else{
			alert("tienes que identificarte para comprar");
		}//end if
	});
	$(".enlace_escribir_reseña").click(function(res){
		if(nombre_login != ""){
			var html = Mustache.render(plantillaCrearValoraciones, res);
			$("#contenedor").html(html);
			var id_producto = $(this).attr("id-producto");
			
			$.post("servicioWebValoraciones/registrarValoracion", {
				
				id: id_producto,
				titulo: $("#titulo").val(),
				descripcion: $("#descripcion").val()
			})
		}else{
			alert("tienes que identificarte para escribir la reseña");
		}//end if
	})//end enlace_escribir_reseña
	});//end done
});//end click enlace_ver_detalles_articulo
})//end JSON function
}//end mostrar_articulos

function mostrar_inicio(){
	$("#contenedor").html(plantillaInicio);
}

//atencion a los enlaces de menu
$("#inicio").click(mostrar_inicio);	//end inicio
				
$("#Articulos").click(mostrar_articulos);//end Articulos
	
$("#registrarme").click(function (){
	$("#contenedor").html(plantillaRegistro);
	$(".form_registro_usuario").submit(function(event) {
		//validar formulario
		if(!validarNombre($("#nombre").val()) || !validarNombre($("#apellido").val()) || !validarEmail($("#email").val()) || !validarPass($("#pass").val()) ){
			event.preventDefault();
			return;
		}
		let formulario = document.forms[0];
		let formData = new FormData(formulario);
		console.log(formData)
		$.ajax("servicioWebUsuarios/registrarUsuario",{
			type: "POST",
			data: formData,
			cache: false,
			contentType: false, 
			processData: false,
			success: function(res){
				alert(res);
			}//end success
		});
		event.preventDefault(); //esto cancela envio de formulario
	}); //end submit
});//end resgistrarme
	
$("#identificarme").click(function(){
	$("#contenedor").html(plantillaIdentificarUsuario);
	//ver si el usuario guardo su email y pass en una cookie, 
	//en caso positivo ponerlas por defecto en los input correspondiente
	if( typeof(Cookies.get("email")) != "undefined" ){
		$("#email").val(Cookies.get("email"));
	}
	if( typeof(Cookies.get("pass")) != "undefined" ){
		$("#pass").val(Cookies.get("pass"));
	}
	$("#form_login").submit(function(e){ //form_login esto de plantillas mustache
		//validar formulario
		if(!validarEmail($("#email").val()) || !validarPass($("#pass").val()) ){
			event.preventDefault();
			return;
		}
		$.post("servicioWebUsuarios/identificarUsuario", // esto es un metodo en servicioWebUsuario
				{ 	
					email: $("#email").val(),
					pass: $("#pass").val(),
				}).done(function(res){
					alert(res);
					if(res.split(",")[0] == "ok"){
						//esto quiere decir que la identificacion es corerecta
						nombre_login = res.split(",")[1];
						$("#mensaje_login").html("Usuario: " + nombre_login);
						alert("identificado correctamente");
						if($("#recordar_datos").prop('checked')){
							//si esto se cumple la casilla de recordar datos está activada
							Cookies.set("email", $("#email").val(),{expires:100});
							Cookies.set("pass", $("#pass").val(),{expires:100});
						}
					}else{
						alert(res);
					}//end if
				});//end done function res
		e.preventDefault();
	});//end submit
	
});//end identificarme
	
$("#carrito").click(function(){
	if(nombre_login != ""){
		$.getJSON("servicioWebCarrito/obtenerProductosCarrito", function(res){
			if(res.length == 0){
				alert("No hay productos en la cesta");
			}else{
				var html = Mustache.render(plantillaCarrito, res);
				$("#contenedor").html(html);
				$(".enlace_borrar_producto_carrito").click(function(e){
				let id_articulo  = $(this).attr("id-articulo")//coger valor de la etqieta
				$.post("servicioWebCarrito/borrarProducto", {
					id: id_articulo
				}).done(function(res){
					if(res == "ok"){
						$("#div-producto-" + id_articulo).hide();
					}else{
						alert(res);
					}
				});//end done
				e.preventDefault();
			});//end enlace_borrar_producto_carrito
			$(".enlace_cantidad_producto_carrito").change(function() {
			    var id_articulo = $(this).attr("id-articulo");
			    var nuevaCantidad = $(this).val();
			    $.post("servicioWebCarrito/modificarCantidadProducto", {
			        id: id_articulo,
			        nuevaCantidad: nuevaCantidad
			    }).done(function(res){
			        if(res === "ok"){
			            alert("Cantidad actualizada con éxito");
			        } else {
			            alert(res); 
			        }
			    }).fail(function() {
			        alert("Error al actualizar la cantidad");
			    });
			});//end enlace_cantidad_producto_carrito
				$("#realizar_pedido").click(checkout_paso_0);
			}//end if
		}).fail(function(){
			alert("error, identificate de nuevo");
		});//end Fail
	}else{
		alert("Debes identificarte");
	}//end if nombre_login
});//end click carrito

$("#misPedidos").click(function(){
    if(nombre_login !== ""){
        $.getJSON("servicioWebPedidos/obtenerMisPedidos", function(res){
            var html = Mustache.render(plantillaMisPedidos, res);
				$("#contenedor").html(html);

       }).done( function (res){
		   console.log(res)
		     $(".enlace_ver_detalles_mis_pedidos").click(function(e) {
                var idPedido = $(this).attr("id-pedido");
            $.post("servicioWebPedidos/obtenerMisPedidosDetalle", 
            	{
					id: idPedido
				}
			).done(function(res){
				console.log(res)
				var html = Mustache.render(plantillaMisPedidosDetalle, res);
				$("#contenedor").html(html);
                e.preventDefault();
            });//end done
          });//end enlace_ver_detalles_mis_pedidos
	   });
    } else {
        alert("Debes identificarte");
    }
});



$("#misDatos").click(function () {
    if (nombre_login !== "") {
        $.getJSON("servicioWebUsuarios/obtenerMisDatos", function (res) {
            var html = Mustache.render(plantillaMisDatos, res);
            $("#contenedor").html(html);
            $(".enlace_editar_mis_datos").click(function (e) {
                e.preventDefault();
                var idUsuario = $(this).attr("id-usuario");
                // Aquí obtienes los datos del usuario para la plantilla de edición
                $.getJSON("servicioWebUsuarios/obtenerMisDatosEditar", 
                	{ 
						id: idUsuario 
                	}).done(function (res) {
							var htmlEditar = Mustache.render(plantillaMisDatosEditar, res);
                        	$("#contenedor").html(htmlEditar);
                        	$(".enlace_guardar_mis_datos").click(function (e) {
								alert("identificado correctamente");
							});//end enlace_guardar_mis_datos
                    });//end done
            });//end enlace_editar_mis_datos
        });//end JSON
    } else {
        alert("Debes identificarte");
    }
});//end misDatos

	
$("#logout").click(function(){
	$.ajax("servicioWebUsuarios/logout",{
		success:function(res){
			if(res == "ok"){
				$("#contenedor").html("Hasta pronto " + nombre_login);
				$("#mensaje_login").html("no estas identificado");
				nombre_login = "";
			}//end if
		}//end success
	});//end ajax
});//end click logout
	
	