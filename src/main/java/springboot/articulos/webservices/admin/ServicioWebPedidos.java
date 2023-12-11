package springboot.articulos.webservices.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springboot.articulos.servicios.ServicioPedidos;



@Controller(value = "servicioWebPedidosAdmin")//value es el nombre del objeto que va gestionar la clase, si hay dos clase se lo damos para que se cree una bencon otro nombre y no sea el mismo 
@RestController
@RequestMapping("admin/servicioWebPedidos/")
public class ServicioWebPedidos {

	@Autowired
	private ServicioPedidos servicioPedidos;
	
	@RequestMapping("actualizarEstadoPedido")
	public String actualizarEstadoPedido(@RequestParam("id") Integer id, String estado) {
		servicioPedidos.actualizarEstadoPedido(id, estado);
		return "ok";
	}
}
