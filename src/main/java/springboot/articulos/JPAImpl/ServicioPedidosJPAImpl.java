package springboot.articulos.JPAImpl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springboot.articulos.ConstantesSQL.ConstantesSQL;
import springboot.articulos.constantes.EstadosPedido;
import springboot.articulos.model.Carrito;
import springboot.articulos.model.Pedido;
import springboot.articulos.model.ProductoCarrito;
import springboot.articulos.model.ProductoPedido;
import springboot.articulos.model.Usuario;
import springboot.articulos.servicios.ServicioCarrito;
import springboot.articulos.servicios.ServicioPedidos;
import springboot.articulos.serviciosweb.ResumenPedido;


@Service
@Transactional
public class ServicioPedidosJPAImpl implements ServicioPedidos{
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private ServicioCarrito servicioCarrito;
	
	@Override
	public List<Pedido> obtenerPedidos() {
		return entityManager.createQuery("select p from Pedido p order by p.id desc").getResultList();
	}//end obtenerPedidos

	@Override
	public Pedido obtenerPedidoPorId(int idPedido) {
		return (Pedido)entityManager.find(Pedido.class, idPedido);
	}//end obtenerPedidoPorId

	@Override
	public void actualizarEstadoPedido(int idPedido, String estado) {
		Pedido p = entityManager.find(Pedido.class, idPedido);
		p.setEstado(estado);
		entityManager.merge(p);
	}//end actualizarEstadoPedido

	@Override
	public void procesarPaso1(String nombreCompleto, String email, String direccion, String provincia, String codigoPostal, int idUsuario) {
		//cada usuario podra tener como maximo un solo pedido en estado EN_PROCESO
		//en ese rellenaremos los datos de envio y pago
		//si el usuario finaliza un pedido en estado "ENPROCESO", el estado cambia a 
		// ser "TERMINADO" puede haber muchos pedidos es estado TERMINADO
		Pedido p = obtenerPedidoActual(idUsuario);
		p.setNombreCompleto(nombreCompleto);
		p.setEmail(email);
		p.setDireccion(direccion);
		p.setProvincia(provincia);
		p.setCodigoPostal(codigoPostal);
		p.setEstado(EstadosPedido.EN_PROCESO);
		entityManager.merge(p);	//merge crea y actualiza
	}//end procesarPaso1

	@Override
	public void procesarPaso2(String titular, String numero, String tipoTarjeta, String expiracion, String cvv, int idUsuario) {
		Pedido p = obtenerPedidoActual(idUsuario);
		p.setNumeroTarjeta(numero);
		p.setTipoTarjeta(tipoTarjeta);
		p.setTitularTarjeta(titular);
		p.setExpiracion(expiracion);
		p.setCvv(cvv);
		entityManager.merge(p);//merge crea y actualiza
	}//end procesarPaso2
	
	@Override
	public void procesarPaso2Medio(String telefono, String codigo_descuento, int idUsuario) {
		Pedido p = obtenerPedidoActual(idUsuario);
		p.setTelefono(telefono);
		p.setCodigo_descuento(codigo_descuento);
		entityManager.merge(p);//merge crea y actualiza
	}//end procesarPaso2Medio

	@Override
	public ResumenPedido obtenerResumenDelPedido(int idUsuario) {
		ResumenPedido resumen = new ResumenPedido();
		Pedido p = obtenerPedidoActual(idUsuario);
		resumen.setNombreCompleto(p.getNombreCompleto());
		resumen.setEmail(p.getEmail());
		resumen.setDireccion(p.getDireccion());
		resumen.setProvincia(p.getProvincia());
		resumen.setCodigoPostal(p.getCodigoPostal());
		resumen.setTipoTarjeta(p.getTipoTarjeta());
		resumen.setTitularTarjeta(p.getTitularTarjeta());
		resumen.setExpiracion(p.getExpiracion());
		resumen.setCvv(p.getCvv());
		//no se deberia de mostrar toda la tarjeta, solo los 4 ultimos
		resumen.setNumeroTarjeta(p.getNumeroTarjeta());
		resumen.setTelefono(p.getTelefono());
		resumen.setCodigo_descuento(p.getCodigo_descuento());
		resumen.setArticulos(servicioCarrito.obtenerProductosCarrito(idUsuario));
		return resumen;
	}//end obtenerResumenDelPedido

	@Override
	public void confirmarPedido(int idUusario) {
		Pedido p = obtenerPedidoActual(idUusario);
		Usuario uBaseDatos = (Usuario)entityManager.find(
				Usuario.class, idUusario);
		Carrito c = uBaseDatos.getCarrito();
		//pasar los productos del carrito al pedido
		if(c != null && c.getProductosCarrito().size() > 0) {
			for(ProductoCarrito pc : c.getProductosCarrito()) {
				ProductoPedido pp = new ProductoPedido();
				pp.setCantidad(pc.getCantidad());
				pp.setArticulo(pc.getArticulo());
				p.getProductosPedido().add(pp);
				pp.setPedido(p);
				entityManager.persist(pp);
			}
		}//end if
		//borrar los productos del carrito del usuario
		Query query = entityManager.createNativeQuery(ConstantesSQL.SQL_BORRAR_PRODUCTOS_CARRITO);
		query.setParameter("carrito_id", c.getId());
		query.executeUpdate();
		//finalizamos pedido
		p.setEstado(EstadosPedido.TERMINADO);	
		entityManager.merge(p);
	}//end confirmarPedido
	
	private Pedido obtenerPedidoActual(int idUsuario) {
		Usuario uBaseDatos = (Usuario)
				entityManager.find(Usuario.class, idUsuario);
		Object pedidoEnProceso = null;
		List<Pedido> resultadoConsulta = entityManager.createQuery(
				"select p from Pedido p where p.estado = :estado and p.usuario.id = :usuario_id ")
				.setParameter("estado", EstadosPedido.EN_PROCESO)
				.setParameter("usuario_id", uBaseDatos.getId()).getResultList();
		if(resultadoConsulta.size() == 1) {
			pedidoEnProceso = resultadoConsulta.get(0);
		}else {
			pedidoEnProceso = null;
		}
		Pedido p = null;
		if (pedidoEnProceso != null ) {
			p = (Pedido)pedidoEnProceso;
		}else {
			p = new Pedido();
			p.setUsuario(uBaseDatos);
		}		
		return p;
	}//end obtenerPedidoActual

	@Override
	public List<Map<String, Object>> obtenerMisPedidosParaFormarJSON(int idUsuario) {
		Query query = entityManager.createNativeQuery(
				ConstantesSQL.SQL_OBTENER_MISPEDIDOS_PARA_JSON);
		query.setParameter("idUsuario", idUsuario);
		NativeQueryImpl nativeQuery = (NativeQueryImpl) query;
		nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		return nativeQuery.getResultList();
	}
	
	@Override
	public List<Map<String, Object>> obtenerPedidoPorIdParaFormarJSON(int idPedido) {
		Query query = entityManager.createNativeQuery(
				ConstantesSQL.SQL_OBTENER_PEDIDO_POR_ID_PARA_JSON);
		query.setParameter("idPedido", idPedido);
		NativeQueryImpl nativeQuery = (NativeQueryImpl) query;
		nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		return nativeQuery.getResultList();
	}
	
	@Override
	public List<Map<String, Object>> obtenerProductosPedidoPorIdParaFormarJSON(int idPedido) {
		Query query = entityManager.createNativeQuery(
				ConstantesSQL.SQL_OBTENER_PRODUCTOS_DE_PEDIDO_USUARIO);
		query.setParameter("idPedido", idPedido);
		NativeQueryImpl nativeQuery = (NativeQueryImpl) query;
		nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		return nativeQuery.getResultList();
	}

	@Override
	public ResumenPedido obtenerDetallesMisPedidos(int idUsuario) {
		return obtenerResumenDelPedido(idUsuario);
	}



	
	
	
}
