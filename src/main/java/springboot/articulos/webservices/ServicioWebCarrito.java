package springboot.articulos.webservices;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import springboot.articulos.model.Usuario;
import springboot.articulos.servicios.ServicioCarrito;



@Controller
@RestController
@RequestMapping("servicioWebCarrito/")
public class ServicioWebCarrito {
	
	@Autowired
	private ServicioCarrito servicioCarrito;
	
	@RequestMapping("agregarArticulo")
	public String agregarArticulo(String id, String cantidad, HttpServletRequest request){
		String respuesta = "";
		if(request.getSession().getAttribute("usuario_identificado") != null) {
			//esto es que el usuario se identifico correctamente
			Usuario u = (Usuario)request.getSession().getAttribute("usuario_identificado");
			//u es el usuario que meti en sesion cuando el usuario se identifico
			respuesta = " agregar producto de id: " + id + " cantidad: " + cantidad + " " +
					" al usuario de id: " + u.getId();	
			servicioCarrito.agregarProducto(
					u.getId(), 
					Integer.parseInt(id), 
					Integer.parseInt(cantidad));
		}else {
			respuesta = "usuario no identificado, identificate para poder comprar productos";
		}
		
		return respuesta;
	}//end agregarArticulo
	
	@RequestMapping("obtenerProductosCarrito")//esto es la ruta a JSP incio
	public List<Map<String, Object>> obtenerProductosCarrito(HttpServletRequest request) throws Exception{
		if(request.getSession().getAttribute("usuario_identificado") != null) {
			
			return servicioCarrito.obtenerProductosCarrito(
							((Usuario)request.getSession().getAttribute("usuario_identificado")).getId()
								);
		}else {
			throw new Exception("*** USUARIO NO IDENTIFICADO ***");
		}
	}//end obtenerProductosCarrito
	
	@RequestMapping("borrarProducto")//esto sale de funcionesmenu.js
	public String borrarProducto(@RequestParam("id") Integer id, HttpServletRequest request) { //id es porque lo pone en el js
		servicioCarrito.borrarProductoCarrito(((Usuario)request.getSession().getAttribute("usuario_identificado")).getId(), id);
		return "ok";
	}//end borrarProducto
	
	@RequestMapping("modificarCantidadProducto")//esto sale de funcionesmenu.js
	public String modificarCantidadProducto(@RequestParam("id") Integer id, @RequestParam("nuevaCantidad") Integer nuevaCantidad, HttpServletRequest request) { //id es porque lo pone en el js
		servicioCarrito.actualizarCantidadCarrito(((Usuario)request.getSession().getAttribute("usuario_identificado")).getId(), id, nuevaCantidad);
		return "ok";
	}//end modificarCantidadProducto
	
	
	
	
}
