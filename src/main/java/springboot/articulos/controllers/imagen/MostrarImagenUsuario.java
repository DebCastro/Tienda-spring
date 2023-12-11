package springboot.articulos.controllers.imagen;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import springboot.articulos.servicios.ServicioUsuario;


@Controller
public class MostrarImagenUsuario {

	@Autowired
	private ServicioUsuario servicioUsuarios;
	
	@RequestMapping("mostrar_imagen_usuario")
	public void mostrar_imagen(@RequestParam("id") Integer id, HttpServletResponse response ) throws IOException {
		byte[] info = servicioUsuarios.obtenerUsuarioPorId(id).getAvatar();
		if(info == null) {
			return;
		} 
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
		response.getOutputStream().write(info);
		response.getOutputStream().close();
	}
	
	
}
