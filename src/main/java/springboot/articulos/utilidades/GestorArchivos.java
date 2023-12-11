package springboot.articulos.utilidades;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import springboot.articulos.model.Articulo;
import springboot.articulos.model.Usuario;



public class GestorArchivos {
	
	public static void guardarPortadaArticulos(Articulo a, String rutaReal) {
		MultipartFile archivo = a.getFotoSubida();
		String nombreArchivo = a.getId() + ".jpg";
		
		//vamos a crear una carpeta de archivos subidos en la ruta real del proyecto
		String rutaSubidas = rutaReal + "subidas";
		File fileRutaSubidas = new File(rutaSubidas);
		if(!fileRutaSubidas.exists()) {
			fileRutaSubidas.mkdirs();
		}
		//mover el archivo subido a esa ruta poniendole el nobre indicado
		if(archivo.getSize() > 0) {
			try {
				archivo.transferTo(new File (rutaSubidas, nombreArchivo));
				System.out.println("archivo añadido a: " + rutaSubidas);
			} catch (IllegalStateException | IOException e) {
				System.out.println("no pude mover el archivo a la ruta de subidas");
				e.printStackTrace();
			}
		}else {
			System.out.println("Se registro un producto sin imagen, es opcional");
		}
	}//end guardarPortadaArticulos
	
	//mejor usar el de arriba
	public static void guardarAvatarUsuario(Usuario usuario, String rutaReal, MultipartFile avatar) {
		String nombreArchivo = usuario.getId() + ".jpg";
		File fileCarpetaAvatares = new File(rutaReal + "subidas_usuarios");
		if(!fileCarpetaAvatares.exists()) {
			fileCarpetaAvatares.mkdirs();
		}
		if(avatar.getSize() > 0) {
			try {
				avatar.transferTo(new File(rutaReal + "subidas_usuarios", nombreArchivo));
				System.out.println("avatar de usuario disponible en : " + rutaReal + "subidas_usuarios");
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}//end if
	}//end guardarAvatarUsuario
}
