package springboot.articulos.servicios;

import java.util.List;

import springboot.articulos.model.Valoracion;

public interface ServicioValoraciones {
	
void registrarValoracion (String titulo, String descripcion, int idUsuario, int idArticulo);
	
	List<Valoracion> obtenerValoraciones ();

}
