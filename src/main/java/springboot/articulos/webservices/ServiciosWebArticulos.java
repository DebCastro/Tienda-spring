package springboot.articulos.webservices;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import springboot.articulos.servicios.ServicioArticulos;
import springboot.articulos.serviciosweb.InfoArticulos;


@Controller
@RestController //asi indicamos que los metodos de este controlador no devuelven vistas, sino respuestas con informacion generalmente en json
@RequestMapping("servicioWebArticulos/")
public class ServiciosWebArticulos {
	
	@Autowired
	private ServicioArticulos servicioArticulos;//hemos pedido bean 
	
	@RequestMapping("obtenerArticulos")//no confundir con el controller
	public InfoArticulos obtenerArticulos(@RequestParam(name = "nombre", defaultValue = "")String nombre, @RequestParam(name = "comienzo", defaultValue = "0")Integer comienzo ){ //esto lo sacamos de funciones menu
		InfoArticulos info = new InfoArticulos();
		info.setArticulos(servicioArticulos.obtenerArticulosParaFormarJSON(nombre, comienzo));
		info.setTotalArticulos(servicioArticulos.obtenerTotalArticulos(nombre));
		return info;
	}//end obtenerArticulos
	
	@RequestMapping("obtenerArticuloDetalles")
	public Map<String,Object> obtenerArticuloDetalles(@RequestParam("id") Integer id){
		return servicioArticulos.obtenerDetallesArticulo(id);
	}//end obtenerArticuloDetalles	
}
