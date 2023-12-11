package springboot.articulos.ConstantesSQL;

public class ConstantesSQL {
	
	public final static String SQL_OBTENER_ARTICULOS_PARA_JSON = "SELECT a.id, a.nombre, a.color, a.estancia, a.precio, c.nombre AS nombre_categoria "
			+ "FROM articulo AS a, categoria AS c "
			+ "WHERE a.categoria_id = c.id AND a.alta = 1 AND a.nombre like :nombre ORDER BY a.id DESC LIMIT :comienzo, 9;";
	
	public static final String SQL_OBTENER_CATEGORIAS_PARA_DESPLEGABLE = "SELECT id, nombre FROM categoria ORDER BY id ASC";

	public static final String SQL_OBTENER_PRODUCTOS_CARRITO = "SELECT a.id AS articulo_id, a.nombre, a.precio AS precio_articulo, pc.cantidad \r\n"
			+ "			FROM articulo AS a, producto_carrito AS pc "
			+ "			WHERE pc.articulo_id = a.id AND pc.carrito_id = :par_variable "
			+ "			ORDER BY pc.id ASC";
	//si llamamos aqui categoria en ver detalles.html tiene que llamarse igual: categoria
	public static final String SQL_OBTENER_DETALLES_ARTICULO = "SELECT a.id, a.nombre, a.color, a.estancia, a.precio, c.nombre AS categoria "
			+ "FROM articulo AS a, categoria AS c WHERE a.categoria_id = c.id AND a.id = :id";
	
	public static final String SQL_BORRAR_PRODUCTOS_CARRITO = 
			"DELETE FROM producto_carrito WHERE carrito_id = :carrito_id";
	
	public static final String SQL_BORRAR_PRODUCTO_CARRITO = 
			"DELETE FROM producto_carrito WHERE carrito_id = :carrito_id "
					+ "and articulo_id = :id_articulo";
	
	public static final String SQL_OBTENER_TOTAL_ARTICULOS = 
			"SELECT COUNT(id) FROM articulo WHERE alta = 1 and nombre like :nombre";

	public static final String SQL_ACTUALIZAR_CANTIDAD_PRODUCTO_CARRITO = "UPDATE producto_carrito SET cantidad = :nuevaCantidad "
			+ "WHERE carrito_id = :carrito_id AND articulo_id = :id_articulo";

	public static final String SQL_OBTENER_MISPEDIDOS_PARA_JSON = "SELECT DISTINCT pedido.id, COUNT(producto_pedido.id), pedido.estado FROM pedido INNER JOIN producto_pedido ON pedido.id = producto_pedido.pedido_id "
			+ "INNER JOIN articulo ON producto_pedido.articulo_id = articulo.id WHERE pedido.usuario_id = :idUsuario GROUP BY pedido.id;";
	
	public static final String SQL_OBTENER_PEDIDO_POR_ID_PARA_JSON = "SELECT p.* FROM `pedido`as p, usuario as u WHERE p.usuario_id = u.id and p.id = :idPedido ;";
	
	public static final String SQL_OBTENER_DATOS_DE_USUARIO_PARA_JSON = "SELECT u.nombre, u.apellido, u.email FROM `usuario` as u where u.id = :idUsuario ;";

	public static final String SQL_OBTENER_PRODUCTOS_DE_PEDIDO_USUARIO= "SELECT pp.cantidad, a.nombre, a.precio, a.id FROM `producto_pedido` as pp, articulo as a WHERE a.id = pp.articulo_id and pp.pedido_id = :idPedido ;";

	public static final String SQL_OBTENER_MISDATOS_PARA_JSON = "SELECT id, nombre, apellido, email FROM usuario WHERE id = :idUsuario";

	
}
