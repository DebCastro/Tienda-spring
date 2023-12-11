package springboot.articulos.servicios;

import java.util.List;
import java.util.Map;


public interface ServicioCarrito {
	
	void agregarProducto(int idUsuario, int idProducto, int cantidad);

	void actualizarCantidadCarrito(int idUsuario, int idProducto, int cantidad);

	void borrarProductoCarrito(int idUsuario, int idProducto);
	
	
	//Operaciones para AJAX
	List<Map<String, Object>> obtenerProductosCarrito (int idUsuario);

}
