package springboot.articulos.webservices;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import springboot.articulos.model.Usuario;
import springboot.articulos.servicios.ServicioUsuario;
import springboot.articulos.utilidades.GestorArchivos;

@Controller
@RestController
@RequestMapping("servicioWebUsuarios/")
public class ServicioWebUsuarios {

	@Autowired
	private ServicioUsuario servicioUsuarios;
	
	@RequestMapping("registrarUsuario")
	public ResponseEntity<String> registrarUsuario(
			@RequestParam Map<String, Object> formData,
			MultipartHttpServletRequest request ){
		
		System.out.println("recibido formData: " + formData);
		System.out.println("recibido foto: " + request.getFile("avatar"));
		
		//obtener un objeto de Usuario a partir de un form data
		Gson gson = new Gson();
		JsonElement json = gson.toJsonTree(formData);
		Usuario u = gson.fromJson(json, Usuario.class);
		System.out.println(json);
		
		
		try {
			u.setAvatar(request.getFile("avatar").getBytes());
		} catch (IOException e) {
			System.out.println("no pude asignar la foto al usuario");
			e.printStackTrace();
		}
		
		servicioUsuarios.registrarUsuario(u);
		
		String rutaRealDelProyecto = request.getServletContext().getRealPath("");
		
		//GestorArchivos.guardarAvatarUsuario(u, rutaRealDelProyecto, foto);		
		
		return new ResponseEntity<String>("usuario registrado, ya puedes identificarte", HttpStatus.OK);
	}//end registrarUsuario
	
	@RequestMapping("identificarUsuario")
	public ResponseEntity<String> identificarUsuario(String email, String pass, HttpServletRequest request){
		Usuario u = servicioUsuarios.obtenerUsuarioPorEmailYpass(email, pass);
		String respuesta = "";
		if ( u != null ) {
			request.getSession().setAttribute("usuario_identificado", u);
			respuesta = "ok," + u.getNombre();
		} else {
			respuesta = "email o pass incorrecto";
		}
		return new ResponseEntity<String>(respuesta, HttpStatus.OK);
	}//end identificarUsuario
	
	@RequestMapping("obtenerMisDatos")
	public List<Map<String, Object>> obtenerMisDatos (HttpServletRequest request) {
		Usuario u = (Usuario) request.getSession().getAttribute("usuario_identificado");
		return servicioUsuarios.obtenerMisDatosParaFormarJSON(u.getId());
	}//end obtenerMisDatos
	
	@RequestMapping("obtenerMisDatosEditar")
	public List<Map<String, Object>> obtenerMisDatosEditar (@RequestParam("id") Integer id, HttpServletRequest request) {
		Usuario u = (Usuario) request.getSession().getAttribute("usuario_identificado");
		return servicioUsuarios.obtenerMisDatosParaFormarJSON(id);
	}

	@RequestMapping("logout")
	public ResponseEntity<String> logout(HttpServletRequest request){
		request.getSession().invalidate();
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}//end logout
	
	
}//end class
