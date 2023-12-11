package springboot.articulos.webservices;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springboot.articulos.model.ProductoCarrito;
import springboot.articulos.model.Usuario;
import springboot.articulos.servicios.ServicioUsuario;
import springboot.articulos.servicios.ServicioValoraciones;

@Controller
@RestController
@RequestMapping("servicioWebValoraciones/")
public class ServicioWebValoraciones {

	@Autowired
	private ServicioValoraciones servicioValoraciones;
	
	@RequestMapping("registrarValoracion")
	public ResponseEntity<String> registrarValoracion(int id, String titulo, String descripcion, HttpServletRequest request){
		String respuesta = "";
		Usuario u = (Usuario) request.getSession().getAttribute("usuario_identificado");//esto sale de ServicioWebUsuario
		System.out.println(id + " "+  titulo + " "+  descripcion);
		for(ProductoCarrito pc : u.getCarrito().getProductosCarrito()) {
			if(pc.getArticulo().getId() == id) {
				servicioValoraciones.registrarValoracion(titulo, descripcion, u.getId(), id);
				respuesta = "ok";
				return new ResponseEntity<String>(respuesta, HttpStatus.OK);
			}	
		}
		respuesta = "no has comprado este articulo";
		return new ResponseEntity<String>(respuesta, HttpStatus.OK);	
	}
	
	
}
