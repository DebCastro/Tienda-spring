package springboot.articulos.servicios;

import java.util.*;

import springboot.articulos.model.Articulo;


//Lo que seria el DAO
public interface ServicioArticulos {
	
	void registrarArticulo (Articulo a);
	
	List<Articulo> obtenerArticulos ();
	
	List<Articulo> obtenerArticulosPorNombre (String nombre);
	
	List<Articulo> obtenerArticulosPorNombreYcomienzoFin (String nombre, int comienzo, int resultadosPorPagina);
	
	int obtenerTotalArticulos(String nombre);
	
	void borrarArticulo (int id);
	
	Articulo obtenerArticuloPorId (int id);
	
	void guardarCambiosArticulo(Articulo l);
	
	//metodos para la comunicacion por AJAX
	List<Map<String, Object>> obtenerArticulosParaFormarJSON(String nombre, int comienzo);

	Map<String, Object> obtenerDetallesArticulo(int idArticulo);
	
	
}
