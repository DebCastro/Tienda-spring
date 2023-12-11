package springboot.articulos.serviciosweb;

import java.util.List;
import java.util.Map;

//esta es la informacion que recibira el usuario para confirmar el pedido
public class ResumenPedido {
	//estos son los productos que hay en el carrito
	private List<Map<String, Object>> articulos;
	
	//datos del paso 1
	private String nombreCompleto;
	private String email;
	private String direccion;
	private String provincia;
	private String codigoPostal;
	
	//datos del paso 2
	private String titularTarjeta;
	private String numeroTarjeta;
	private String tipoTarjeta;
	private String expiracion;
	private String cvv;
	
	//datos del paso 2 medio
		private String telefono;
		private String codigo_descuento;
	
	public List<Map<String, Object>> getArticulos() {
		return articulos;
	}
	public void setArticulos(List<Map<String, Object>> articulos) {
		this.articulos = articulos;
	}
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getTitularTarjeta() {
		return titularTarjeta;
	}
	public void setTitularTarjeta(String titularTarjeta) {
		this.titularTarjeta = titularTarjeta;
	}
	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}
	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}
	public String getTipoTarjeta() {
		return tipoTarjeta;
	}
	public void setTipoTarjeta(String tipoTarjeta) {
		this.tipoTarjeta = tipoTarjeta;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getExpiracion() {
		return expiracion;
	}
	public void setExpiracion(String expiracion) {
		this.expiracion = expiracion;
	}
	public String getCvv() {
		return cvv;
	}
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCodigo_descuento() {
		return codigo_descuento;
	}
	public void setCodigo_descuento(String codigo_descuento) {
		this.codigo_descuento = codigo_descuento;
	}
	
	
	
}
