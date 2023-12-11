package springboot.articulos.servicios;

import java.util.List;
import java.util.Map;

import springboot.articulos.model.Categoria;


public interface ServicioCategorias {
	
	List<Categoria> obtenerCategorias();
	
	Map<String, String>  obtenerCategoriasParaDesplegable();
	
	void borrarCategoria(int id);
	
	Categoria obtenerCategoriaPorId(int id);
	
	void guardarCambiosCategoria(Categoria c);
	
	void registrarCategoria(Categoria c);
}
