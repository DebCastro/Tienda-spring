package springboot.articulos.controllers.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;

import springboot.articulos.model.Articulo;
import springboot.articulos.model.Usuario;
import springboot.articulos.servicios.ServicioUsuario;


@Controller
@RequestMapping("admin/")
public class UsuariosController {
	@Autowired
	private ServicioUsuario servicioUsuarios;
	
	@RequestMapping("obtenerUsuarios")
	public String obtenerUsuarios(Model model) {
		List<Usuario> usuarios = servicioUsuarios.obtenerUsuarios();
		model.addAttribute("info", usuarios);
		return "admin/usuarios";
	}
	
	@RequestMapping("registrarUsuario")
	public String registrarUsuario(Model model) {
		Usuario nuevo = new Usuario();
		
		model.addAttribute("nuevoUsuario", nuevo);
		return "admin/usuario_registro";	
	}//end registrarUsuario
	
	@RequestMapping("guardarUsuario") 
	public String guardarUsuario(@ModelAttribute("nuevoUsuario") @Valid Usuario nuevoUsuario,BindingResult resultadoValidaciones, Model model) {
		System.out.println("usuario recibido para registrar: " + nuevoUsuario.toString());
		if( resultadoValidaciones.hasErrors() ) {
			return "admin/usuario_registro";
		}
		servicioUsuarios.registrarUsuario(nuevoUsuario);
		
		return "admin/usuario_registro_ok";
	}//end guardarUsuario
	
	@RequestMapping("borrarUsuario")
	public String borrarUsuario(String id, Model model) {
		servicioUsuarios.borrarUsuario(Integer.parseInt(id));
		return obtenerUsuarios(model);
	}//end borrarUsuario
	
	@RequestMapping("editarUsuario")
	public String editarUsuario(String id, Model model) {
		Usuario u = servicioUsuarios.obtenerUsuarioPorId(Integer.parseInt(id));
		model.addAttribute("usuarioEditar", u);
		return "admin/usuarios_editar";
	}//end editarUsuario
	
	@RequestMapping("guardarCambiosUsuario")
	public String guardarCambiosUsuario(@ModelAttribute("usuarioEditar") @Valid Usuario usuarioEditar, BindingResult resultadoValidaciones, Model model) {
		if( resultadoValidaciones.hasErrors()) {
			
			return "admin/usuarios_editar";
		}
		servicioUsuarios.guardarCambiosUsuario(usuarioEditar);
		
		return obtenerUsuarios(model);	

	}//end guardarCambiosUsuario

}//end class
