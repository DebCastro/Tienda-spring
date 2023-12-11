package springboot.articulos.JPAImpl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springboot.articulos.model.Articulo;
import springboot.articulos.model.Categoria;
import springboot.articulos.servicios.ServicioCategorias;


@Service
@Transactional
public class ServicioCategoriasJPAImpl implements ServicioCategorias{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Map<String, String> obtenerCategoriasParaDesplegable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Categoria> obtenerCategorias() {
		return entityManager.createQuery("select c from Categoria c where c.alta = true order by c.id DESC").getResultList();

	}

	@Override
	public void borrarCategoria(int id) {
		Categoria c = entityManager.find(Categoria.class, id);
		c.setAlta(false);
		entityManager.merge(c);	
	}

	@Override
	public Categoria obtenerCategoriaPorId(int id) {
		return (Categoria)entityManager.find(Categoria.class, id);
	}

	@Override
	public void guardarCambiosCategoria(Categoria c) {
		c.setAlta(true);
		entityManager.merge(c);	
	}

	@Override
	public void registrarCategoria(Categoria c) {
		c.setAlta(true);
		entityManager.persist(c);
		
	}
}
