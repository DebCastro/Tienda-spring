package springboot.articulos.webservices;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import springboot.articulos.model.Pedido;
import springboot.articulos.model.Usuario;
import springboot.articulos.servicios.ServicioPedidos;
import springboot.articulos.servicios.ServicioUsuario;
import springboot.articulos.serviciosweb.ResumenPedido;


@Controller
@RestController
@RequestMapping("servicioWebPedidos/")
public class ServicioWebPedidos {
	
	//como pedir una bean de spring cuyo nombre de clase se usa en varios paquetes, como en este caso
	//tenenmos que usar @Qualifier, dentro usamos el valor de value dentro del otro paquete
		//@Autowired
		//@Qualifier("servicioWebPedidosAdmin/")
		//private ServicioWebPedidos servicioWebPedidos;
	
	@Autowired
	private ServicioPedidos servicioPedidos;
	@Autowired
	private ServicioUsuario servicioUsuario;

	@RequestMapping("paso1")
	public ResponseEntity<String> paso1(String nombre, String email, String direccion, String provincia, String codigoPostal, HttpServletRequest request){
		String respuesta = "";
		Usuario u = (Usuario) request.getSession().getAttribute("usuario_identificado");//esto sale de ServicioWebUsuario
		servicioPedidos.procesarPaso1(nombre, email, direccion, provincia, codigoPostal, u.getId());
		respuesta = "ok";
		return new ResponseEntity<String>(respuesta, HttpStatus.OK);
	}
	
	@RequestMapping("paso2")
	public ResponseEntity<String> paso2 (String tarjeta, String numero, String titular, String expiracion, String cvv, HttpServletRequest request){
		String respuesta = "";
		Usuario u = (Usuario) request.getSession().getAttribute("usuario_identificado");//esto sale de ServicioWebUsuario
		servicioPedidos.procesarPaso2(titular, numero, tarjeta, expiracion, cvv, u.getId());
		respuesta = "ok";
		return new ResponseEntity<String>(respuesta, HttpStatus.OK);
	}//end paso2
	
	@RequestMapping("paso2_medio")
	public ResumenPedido paso2_medio (String telefono, String codigo_descuento, HttpServletRequest request){
		Usuario u = (Usuario)request.getSession().getAttribute("usuario_identificado");
		servicioPedidos.procesarPaso2Medio(telefono, codigo_descuento, u.getId());
		ResumenPedido resumen = servicioPedidos.obtenerResumenDelPedido(u.getId());
		return resumen;
	}//end paso2
	
	@RequestMapping("paso3")
	public ResponseEntity<String> paso3 (HttpServletRequest request){
		String respuesta = "";
		Usuario u = (Usuario) request.getSession().getAttribute("usuario_identificado");//esto sale de ServicioWebUsuario
		servicioPedidos.confirmarPedido(u.getId());
		respuesta = "pedido completado";
		return new ResponseEntity<String>(respuesta, HttpStatus.OK);
	}//end paso3
	
	@RequestMapping("obtenerMisPedidos")
	public List<Map<String, Object>> obtenerMisPedidos (HttpServletRequest request) {
		Usuario u = (Usuario) request.getSession().getAttribute("usuario_identificado");
		return servicioPedidos.obtenerMisPedidosParaFormarJSON(u.getId());
	}
	
	@RequestMapping("obtenerMisPedidosDetalle")
	public ResponseEntity<Map<String, Object>> obtenerMisPedidosDetalle(@RequestParam("id") Integer id, HttpServletRequest request){
		Usuario u = (Usuario) request.getSession().getAttribute("usuario_identificado");
		Map<String, Object> respuesta = new HashMap<>();
		respuesta.put("usuario", servicioUsuario.obtenerDatosUsuariosParaFormarJSON(u.getId()));
		respuesta.put("pedido", servicioPedidos.obtenerPedidoPorIdParaFormarJSON(id));
		respuesta.put("articulos" , servicioPedidos.obtenerProductosPedidoPorIdParaFormarJSON(id));
		
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}//end obtenerArticuloDetalles	
	
	
	
}
