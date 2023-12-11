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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "articulo") //nombre de la tabla en la BD
public class Articulo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Size(min = 3, max =100, message = "nombre: entre 3 - 40 caracteres")
	@NotEmpty(message = "titulo no puede estar vacio")
	@Pattern(regexp = "([A-Za-z0-9áéíóúÁÉÍÚÓÑñ]+\\s?)+", message = "letras numeros y espacios")
	private String nombre;
	
	@NotNull(message = "Número requerido")
	@DecimalMin(value = "1.0", inclusive = true, message = "Mínimo 1.0")
	@DecimalMax(value = "999.0", inclusive = true, message = "Máximo 999.0")
	private double precio;
	
	@Size(min = 3, max =40, message = "color: entre 3 - 40 caracteres")
	@NotEmpty(message = "color no puede estar vacio")	
	@Pattern(regexp = "([A-Za-záéíóúÁÉÍÚÓÑñ]+\\s?)+", message = "letras y espacios")
	private String color;
	
	@Size(min = 3, max =80, message = "estancia: entre 3 - 40 caracteres")
	@NotEmpty(message = "estancia no puede estar vacio")
	@Pattern(regexp = "([A-Za-záéíóúÁÉÍÚÓÑñ]+\\s?)+", message = "letras y espacios")
	private String estancia;
	private boolean alta;
	
	//vamos a indicar la asociacion entre la clase libro y categoria
	@ManyToOne
	private Categoria categoria;
	
	@Transient
	private int idCategoria; //esto es el path del option select en libros_registro.jsp
	
	@Lob // para tipos clob blob = para almacenar un array de bytes
	@Column(name = "imagen_portada")
	private byte[] imagenPortada;
		
	//dato para la img
	@Transient //con esto decimos a hibernate que no considere este campo en la BD es el nombre del path del form
	private MultipartFile fotoSubida;
	
	@OneToMany(mappedBy = "articulo", cascade = CascadeType.ALL)
	private List<Valoracion> valoracion;
	
	public Articulo() {
		// TODO Auto-generated constructor stub
	}

	public Articulo(int id, String nombre, double precio, String color, String estancia) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.color = color;
		this.estancia = estancia;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getEstancia() {
		return estancia;
	}

	public void setEstancia(String estancia) {
		this.estancia = estancia;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public int getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	public MultipartFile getFotoSubida() {
		return fotoSubida;
	}

	public void setFotoSubida(MultipartFile fotoSubida) {
		this.fotoSubida = fotoSubida;
	}

	public byte[] getImagenPortada() {
		return imagenPortada;
	}

	public void setImagenPortada(byte[] imagenPortada) {
		this.imagenPortada = imagenPortada;
	}

	public boolean isAlta() {
		return alta;
	}

	public void setAlta(boolean alta) {
		this.alta = alta;
	}

	//Constructor para que se cargue la info segun inicias
	public Articulo(String nombre, double precio, String color, String estancia) {
		super();
		this.nombre = nombre;
		this.precio = precio;
		this.color = color;
		this.estancia = estancia;
	}

	public Articulo(
			@Size(min = 3, max = 10, message = "nombre: entre 3 - 10 caracteres") @NotEmpty(message = "titulo no puede esetar vacio") @Pattern(regexp = "[A-Za-z0-9 áéíóúÁÉÍÚÓÑn]+", message = "letras, numeros y espacios") String nombre,
			@NotNull(message = "numeros") @Min(value = 1, message = "minimo 1") @Max(value = 999, message = "maximo 999") double precio,
			String color) {
		super();
		this.nombre = nombre;
		this.precio = precio;
		this.color = color;
	}
	
	
	
	
	
	
}
