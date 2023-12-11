package springboot.articulos.controllers.imagen;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import springboot.articulos.servicios.ServicioArticulos;


@Controller
public class MostrarImagenProducto {

	@Autowired
	private ServicioArticulos servicioArticulos;
	
	@RequestMapping("mostrar_imagen") //esto esta en registro img
	public void mostrar_imagen (@RequestParam("id") Integer id, HttpServletResponse response) throws IOException {
		byte[] info = servicioArticulos.obtenerArticuloPorId(id).getImagenPortada();
		if(info == null) {
			return;
		}
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
		response.getOutputStream().write(info);
		response.getOutputStream().close();
	}
}
