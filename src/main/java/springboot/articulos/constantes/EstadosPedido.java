package springboot.articulos.constantes;

public class EstadosPedido {
	
	//el usuario ha empezado a crear el pedido
	public static final String EN_PROCESO = "en proceso";
	
	//el usuario ha terminado de hacer el pedido y ahora el administrado puede crearlo
	public static final String TERMINADO = "terminado";
	
	//administrador ya ha preparado el envio fisicamente
	public static final String LISTO_PARA_ENVIAR = "listo para enviar";
	
	//la empresa de mensajeria ha confirmado la recepcion del pedido por parte del cliente
	public static final String RECIBIDO_POR_EL_CLIENTE = "Recibido por el cliente";
	
	
}
