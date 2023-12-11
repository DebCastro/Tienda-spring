package springboot.articulos.servicios;

import java.util.List;
import java.util.Map;

import springboot.articulos.model.Usuario;


public interface ServicioUsuario {
	
	void registrarUsuario(Usuario u);
	
	Usuario obtenerUsuarioPorEmailYpass(String email, String pass);

	List<Usuario> obtenerUsuarios();
    
	void borrarUsuario(int id);
	
	Usuario obtenerUsuarioPorId(int id);
	
	void guardarCambiosUsuario(Usuario u);

	List<Map<String, Object>> obtenerDatosUsuariosParaFormarJSON(int idUsuario);

	List<Map<String, Object>> obtenerMisDatosParaFormarJSON(int id);

}
