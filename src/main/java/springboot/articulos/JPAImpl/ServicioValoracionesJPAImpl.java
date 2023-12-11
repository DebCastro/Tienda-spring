package springboot.articulos.JPAImpl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springboot.articulos.model.Articulo;
import springboot.articulos.model.Pedido;
import springboot.articulos.model.Usuario;
import springboot.articulos.model.Valoracion;
import springboot.articulos.servicios.ServicioArticulos;
import springboot.articulos.servicios.ServicioUsuario;
import springboot.articulos.servicios.ServicioValoraciones;

@Service
@Transactional
public class ServicioValoracionesJPAImpl implements ServicioValoraciones{
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private ServicioUsuario servicioUsuario;
	
	@Autowired
	private ServicioArticulos servicioArticulos;

	@Override
	public void registrarValoracion(String titulo, String descripcion, int idUsuario, int idArticulo) {
		Usuario u = servicioUsuario.obtenerUsuarioPorId(idUsuario);
		Articulo a = servicioArticulos.obtenerArticuloPorId(idArticulo);
		Valoracion v = new Valoracion();
		v.setTitulo(titulo);
		v.setDescripcion(descripcion);
		v.setArticulo(a);
		v.setUsuario(u);
		entityManager.merge(v);
	}

	@Override
	public List<Valoracion> obtenerValoraciones() {
		return entityManager.createQuery("SELECT v FROM Valoracion v ORDER BY v.contador DESC").getResultList();
	}

}
