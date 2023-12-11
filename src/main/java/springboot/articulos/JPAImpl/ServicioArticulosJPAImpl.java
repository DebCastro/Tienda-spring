package springboot.articulos.JPAImpl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springboot.articulos.ConstantesSQL.ConstantesSQL;
import springboot.articulos.model.Articulo;
import springboot.articulos.model.Categoria;
import springboot.articulos.servicios.ServicioArticulos;


@Service
@Transactional
public class ServicioArticulosJPAImpl implements ServicioArticulos{
	
	//esto es el sessionFactory de antes
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void registrarArticulo(Articulo a) {
		Categoria c = entityManager.find(Categoria.class, a.getIdCategoria());
		a.setCategoria(c);
		try {
			a.setImagenPortada(a.getFotoSubida().getBytes());
		} catch (IOException e) {
			System.out.println("no pude procesar la foto subida");
			e.printStackTrace();
		}
		a.setAlta(true);	
		entityManager.persist(a);		
	}

	@Override
	public List<Articulo> obtenerArticulos() {
		// JPQL: lenguaje de consultas de jpa, similar a SQL
		//ventaja: devuelve objetos, funciona como con alias
		return entityManager.createQuery("select a from Articulo a where a.alta = true order by a.id DESC").getResultList();
	}

	@Override
	public void borrarArticulo(int id) {
		//entityManager.remove(entityManager.find(Articulo.class, id)); 
		//ya no borramos producto sino que lo damos de baja
				Articulo a = entityManager.find(Articulo.class, id);
				a.setAlta(false);
				entityManager.merge(a);
	}

	@Override
	public Articulo obtenerArticuloPorId(int id) {
		return entityManager.find(Articulo.class, id);
	}

	@Override
	public void guardarCambiosArticulo(Articulo a) {
		Categoria c = entityManager.find(Categoria.class, a.getIdCategoria());
		a.setCategoria(c);
		a.setAlta(true);
		if(a.getFotoSubida().getSize() == 0) {
			System.out.println("no se subio una nueva foto, mantener foto");
			Articulo articuloAnterior = entityManager.find(Articulo.class, a.getId());
			a.setImagenPortada(articuloAnterior.getImagenPortada());
		}else {
			System.out.println("Asignar nueva foto");
			try {
				a.setImagenPortada(a.getFotoSubida().getBytes());
			} catch (IOException e) {
				System.out.println("No pude procesar la foto suya");
				e.printStackTrace();
			}
		}
		entityManager.merge(a);
	}

	@Override
	public List<Map<String, Object>> obtenerArticulosParaFormarJSON(String nombre, int comienzo) {
		Query query = entityManager.createNativeQuery(
				ConstantesSQL.SQL_OBTENER_ARTICULOS_PARA_JSON);
		NativeQueryImpl nativeQuery = (NativeQueryImpl) query;
		nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		nativeQuery.setParameter("nombre", "%" + nombre + "%");
		nativeQuery.setParameter("comienzo" , comienzo);
		return nativeQuery.getResultList();
	}

	@Override
	public Map<String, Object> obtenerDetallesArticulo(int idArticulo) {
		Query query = entityManager.createNativeQuery(ConstantesSQL.SQL_OBTENER_DETALLES_ARTICULO);
		NativeQueryImpl nativeQuery = (NativeQueryImpl) query;
		nativeQuery.setParameter("id", idArticulo);
		nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		return (Map<String, Object>) nativeQuery.getResultList().get(0);
	}

	@Override
	public List<Articulo> obtenerArticulosPorNombre(String nombre) {
		return entityManager.createQuery("select a from Articulo a where a.alta = true and a.nombre like :nombre order by a.id DESC").setParameter("nombre", "%" + nombre + "%").getResultList();

	}

	@Override
	public List<Articulo> obtenerArticulosPorNombreYcomienzoFin(String nombre, int comienzo, int resultadosPorPagina) {
		return entityManager.createQuery("select a from Articulo a where a.alta = true and a.nombre like :nombre order by a.id DESC").setParameter("nombre", "%" + nombre + "%").setFirstResult(comienzo).setMaxResults(resultadosPorPagina).getResultList();

	}

	@Override
	public int obtenerTotalArticulos(String nombre) {
		Query q = entityManager.createNativeQuery(ConstantesSQL.SQL_OBTENER_TOTAL_ARTICULOS);
		q.setParameter("nombre","%" + nombre + "%");
		int totalArticulos = Integer.parseInt(q.getSingleResult().toString()) ;
		return totalArticulos;
	}
	
	
	

}
