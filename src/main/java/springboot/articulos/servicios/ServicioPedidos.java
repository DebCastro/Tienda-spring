package springboot.articulos.servicios;

import java.util.List;
import java.util.Map;

import springboot.articulos.model.Pedido;
import springboot.articulos.serviciosweb.ResumenPedido;


public interface ServicioPedidos {
	
	//gestion administracion
	List<Pedido> obtenerPedidos();
	
	Pedido obtenerPedidoPorId(int idPedido);
	
	void actualizarEstadoPedido(int idPedido, String estado);
	
	
	//funciones AJAX
	void procesarPaso1(String nombreCompleto, String email, String direccion, String provincia, String codigoPostal, int idUsuario);
	
	void procesarPaso2(String titular, String numero, String tipoTarjeta, String expiracion, String cvv, int idUsuario);
	
	void procesarPaso2Medio(String telefono, String codigo_descuento, int idUsuario);
	
	ResumenPedido obtenerResumenDelPedido(int idUsuario);
	
	void confirmarPedido(int idUusario);
	
	//metodos para la comunicacion por AJAX
	List<Map<String, Object>> obtenerMisPedidosParaFormarJSON(int idUsuario);

	ResumenPedido obtenerDetallesMisPedidos(int idUsuario);

	List<Map<String, Object>> obtenerPedidoPorIdParaFormarJSON(int idPedido);

	List<Map<String, Object>> obtenerProductosPedidoPorIdParaFormarJSON(int idPedido);
	
}
