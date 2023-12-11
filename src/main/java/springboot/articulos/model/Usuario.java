package springboot.articulos.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})}) //nombre de la tabla en la BD
public class Usuario {
	@Size(min = 3, max =60, message = "nombre: entre 3 - 60 caracteres")
	@NotEmpty(message = "nombre no puede estar vacio")
	@Pattern(regexp = "([A-Za-záéíóúÁÉÍÚÓÑñ]+\\s?)+", message = "letras, numeros y espacios")
	private String nombre;
	
	@Size(min = 3, max =60, message = "apellido: entre 3 - 60 caracteres")
	@NotEmpty(message = "apellido no puede estar vacio")
	@Pattern(regexp = "([A-Za-záéíóúÁÉÍÚÓÑñ]+\\s?)+", message = "letras, numeros y espacios")
	private String apellido;
	
	@Size(min = 3, max =60, message = "email: entre 3 - 60 caracteres")
	@NotEmpty(message = "email no puede estar vacio")
	@Pattern(regexp = "[A-Za-z0-9áéíóúÁÉÍÚÓÑñ@.]+", message = "letras, numeros y carcateres especiales")
	private String email;
	
	@Size(min = 2, max =20, message = "pass: entre 3 - 20 caracteres")
	@NotEmpty(message = "pass no puede estar vacio")
	@Pattern(regexp = "[A-Za-z0-9]+", message = "letras y numeros")
	private String pass;
	
	//dato para la img
	@Transient //con esto decimos a hibernate que no considere este campo en la BD es el nombre del path del form
	private MultipartFile fotoSubida;
	
	@Lob
	@Column(name = "avatar")
	private byte[] avatar;
	
	@OneToOne
	private Carrito carrito;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	private List<Valoracion> valoracion;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public MultipartFile getFotoSubida() {
		return fotoSubida;
	}
	public void setFotoSubida(MultipartFile fotoSubida) {
		this.fotoSubida = fotoSubida;
	}
	public Carrito getCarrito() {
		return carrito;
	}
	public void setCarrito(Carrito carrito) {
		this.carrito = carrito;
	}
	
	public byte[] getAvatar() {
		return avatar;
	}
	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
	}
	public Usuario(String nombre, String apellido, String email, int id) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.id = id;
	}
	
	public Usuario() {
		// TODO Auto-generated constructor stub
	}
	public Usuario(String nombre, String apellido,String email, String pass) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.pass = pass;
	}
	
	
}
