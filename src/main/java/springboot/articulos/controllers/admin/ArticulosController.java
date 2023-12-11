package springboot.articulos.controllers.admin;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import springboot.articulos.model.Articulo;
import springboot.articulos.servicios.ServicioArticulos;
import springboot.articulos.servicios.ServicioCategorias;
import springboot.articulos.utilidades.GestorArchivos;


@Controller
@RequestMapping("admin/")
public class ArticulosController {
	
	@Autowired
	private ServicioArticulos servicioArticulos;
	
	@Autowired
	private ServicioCategorias servicioCategorias;
	
	@RequestMapping("obtenerArticulos")
	public String obtenerArticulos(@RequestParam(name = "nombre", defaultValue = "") String nombre, @RequestParam(name = "comienzo", defaultValue = "0") Integer comienzo, Model model) { //esto para recoger la info del buscador @RequestParam(name = "nombre", defaultValue = "")
		model.addAttribute("articulos", servicioArticulos.obtenerArticulosPorNombreYcomienzoFin(nombre, comienzo, 10));
		model.addAttribute("nombre", nombre);
		model.addAttribute("siguiente", comienzo+10);
		model.addAttribute("anterior", comienzo-10);
		model.addAttribute("total", servicioArticulos.obtenerTotalArticulos(nombre));
		return "admin/articulos"; //esto devuelve una vista 
	}//end obtenerArticulos
	
	@RequestMapping("registrarArticulo")
	public String registrarArticulo(Model model) {
		Articulo a = new Articulo();
		a.setPrecio(1);
		model.addAttribute("nuevoArticulo", a); //th:object="${nuevoArticulo}" en html articulod registro
		//formar las categorias para un desplegable
		model.addAttribute("categorias", servicioCategorias.obtenerCategorias());//th:each="c : ${categorias}" en articulos_registro.html
		return "admin/articulos_registro";
	}//end registrarArticulo
	
	@RequestMapping("guardarArticulo")
	public String guardarArticulo(@ModelAttribute("nuevoArticulo") @Valid Articulo nuevoArticulo,BindingResult resultadoValidaciones,  Model model) {//th:object="${nuevoArticulo}" en html articulos registro
		System.out.println("id categoria: " + nuevoArticulo.getIdCategoria());
		if( resultadoValidaciones.hasErrors() ) {
			model.addAttribute("categorias", servicioCategorias.obtenerCategorias());
			return "admin/articulos_registro";
		}
		servicioArticulos.registrarArticulo(nuevoArticulo);
		
		return "admin/articulos_registro_ok";
	}//end guardarArticulo
	
	@RequestMapping("borrarArticulo")
	public String borrarArticulo(@RequestParam("id") Integer id, Model model) {
		servicioArticulos.borrarArticulo(id); 
		return obtenerArticulos("", 0 ,model);
	}//end borrarArticulo
	
	@RequestMapping("editarArticulo")
	public String editarArticulo(@RequestParam("id") Integer id, Model model) {
		Articulo articulo = servicioArticulos.obtenerArticuloPorId(id);
		articulo.setIdCategoria(articulo.getCategoria().getId());
		model.addAttribute("articuloEditar", articulo); //esto a al articulos_editar.html
		model.addAttribute("categorias", servicioCategorias.obtenerCategorias());//th:each="c : ${categorias}" en articulos_registro.html
		return "admin/articulos_editar";
	}//end editarArticulo
	
	@RequestMapping("guardarCambiosArticulo")
	public String guardarCambiosArticulo (@ModelAttribute("articuloEditar") @Valid Articulo articuloEditar,BindingResult resultadoValidaciones, Model model) {
		if( resultadoValidaciones.hasErrors() ) {
			model.addAttribute("categorias", servicioCategorias.obtenerCategorias());
			return "admin/articulos_editar";
		}
		servicioArticulos.guardarCambiosArticulo(articuloEditar);
		return obtenerArticulos("", 0 ,model);
	}//end guardarCambiosArticulo

}
