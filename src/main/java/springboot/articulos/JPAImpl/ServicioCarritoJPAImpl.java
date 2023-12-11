package springboot.articulos.JPAImpl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springboot.articulos.ConstantesSQL.ConstantesSQL;
import springboot.articulos.model.Articulo;
import springboot.articulos.model.Carrito;
import springboot.articulos.model.ProductoCarrito;
import springboot.articulos.model.Usuario;
import springboot.articulos.servicios.ServicioCarrito;


@Service
@Transactional
public class ServicioCarritoJPAImpl implements ServicioCarrito{
	
	@Autowired
	private EntityManager entityManager;

	@Override
	public void agregarProducto(int idUsuario, int idProducto, int cantidad) {
		Usuario uBaseDeDatos = (Usuario)entityManager.find(Usuario.class, idUsuario);
		Carrito c = uBaseDeDatos.getCarrito();
		if(c == null) {
			//si el usuario no tiene un carrito asociado lo guardo en la BD
			c = new Carrito();
			c.setUsuario(uBaseDeDatos);
			uBaseDeDatos.setCarrito(c);
			entityManager.persist(c);
		}//end if
		
		boolean producto_en_carrito = false;
		//ver si el producto ya esta en el carrito y si esta incrementarlo
		for(ProductoCarrito pc : c.getProductosCarrito()) {
			if(pc.getArticulo().getId() == idProducto) {
				producto_en_carrito = true;
				pc.setCantidad(pc.getCantidad() + cantidad);
				entityManager.merge(pc);
			}
		}//end for
		//si el producto no esta en el carro, lo creamos un productocarrito nuevo
		if( ! producto_en_carrito) {
			ProductoCarrito pc = new ProductoCarrito();
			pc.setCarrito(c);
			pc.setArticulo((Articulo)entityManager.find(Articulo.class, idProducto));
			pc.setCantidad(cantidad);
			entityManager.persist(pc);
		}//end if
		
	}//end agregarProducto

	@Override
	public void actualizarCantidadCarrito(int idUsuario, int idProducto, int nuevaCantidad) {
		Usuario uBaseDeDatos = (Usuario)entityManager.find(Usuario.class, idUsuario);
		Carrito c = uBaseDeDatos.getCarrito();
	    if (c != null) {
	        Query query = entityManager.createNativeQuery(ConstantesSQL.SQL_ACTUALIZAR_CANTIDAD_PRODUCTO_CARRITO);
	        query.setParameter("carrito_id", c.getId());
	        query.setParameter("id_articulo", idProducto);
	        query.setParameter("nuevaCantidad", nuevaCantidad);
	        query.executeUpdate();
	    }
		
	}//end actualizarCantidadCarrito

	@Override
	public void borrarProductoCarrito(int idUsuario, int idProducto) {
		Usuario u = (Usuario)entityManager.find(Usuario.class, idUsuario);
		Carrito c = u.getCarrito();
		if(c!=null) {
			Query query = entityManager.createNativeQuery(ConstantesSQL.SQL_BORRAR_PRODUCTO_CARRITO);
			query.setParameter("carrito_id", c.getId());
			query.setParameter("id_articulo", idProducto);
			query.executeUpdate();
		}
		
	}

	@Override
	public List<Map<String, Object>> obtenerProductosCarrito(int idUsuario) {
		Usuario u = (Usuario)entityManager.find(Usuario.class, idUsuario);
		Carrito c = u.getCarrito();
		List<Map<String, Object>> res = null;
		if(c != null ) {
			Query query = entityManager.createNativeQuery(
					ConstantesSQL.SQL_OBTENER_PRODUCTOS_CARRITO);
			query.setParameter("par_variable", c.getId());
			NativeQueryImpl nativeQuery = (NativeQueryImpl) query;
			nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			res = nativeQuery.getResultList(); 
		}		
		return res;
	}//end obtenerProductosCarrito
}
