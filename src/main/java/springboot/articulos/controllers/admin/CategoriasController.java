package springboot.articulos.controllers.admin;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import springboot.articulos.model.Categoria;
import springboot.articulos.model.Usuario;
import springboot.articulos.servicios.ServicioCategorias;


@Controller
@RequestMapping("admin/")
public class CategoriasController {
	@Autowired
	private ServicioCategorias servicioCategoria;
	
	@RequestMapping("obtenerCategorias")
	public String obtenerCategorias(Model model) {
		model.addAttribute("categorias", servicioCategoria.obtenerCategorias());
		return "admin/categorias";
	}
	
	@RequestMapping("registrarCategoria")
	public String registrarCategoria(Model model) {
		Categoria nueva = new Categoria();
		
		model.addAttribute("nuevaCategoria", nueva);
		return "admin/categoria_registro";
	}//end registrarCategoria
	
	@RequestMapping("guardarCategoria") 
	public String guardarCategoria(@ModelAttribute("nuevaCategoria") @Valid Categoria nuevaCategoria,BindingResult resultadoValidaciones, Model model) {
		System.out.println("categoria recibida para registrar: " + nuevaCategoria.toString());
		if( resultadoValidaciones.hasErrors() ) {
			return "admin/categoria_registro";
		}
		servicioCategoria.registrarCategoria(nuevaCategoria);
		
		return "admin/categoria_registro_ok";
	}//end guardarCategoria
	
	@RequestMapping("borrarCategoria")
	public String borrarCategoria(String id, Model model) {
		servicioCategoria.borrarCategoria(Integer.parseInt(id));
		return obtenerCategorias(model);
	}//end borrarCategoria
	
	@RequestMapping("editarCategoria")
	public String editarCategoria(String id, Model model) {
		Categoria c = servicioCategoria.obtenerCategoriaPorId(Integer.parseInt(id));
		model.addAttribute("categoriaEditar", c);
		return "admin/categoria_editar";
	}//end editarCategoria
	
	@RequestMapping("guardarCambiosCategoria")
	public String guardarCambiosCategoria(@ModelAttribute("categoriaEditar") @Valid Categoria categoriaEditar,BindingResult resultadoValidaciones, Model model) {
		if( resultadoValidaciones.hasErrors() ) {
			return "admin/categoria_editar";
		}
		servicioCategoria.guardarCambiosCategoria(categoriaEditar);
		return obtenerCategorias(model);
	}//end guardarCambiosCategoria

}//end class