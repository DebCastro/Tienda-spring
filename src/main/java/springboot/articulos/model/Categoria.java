package springboot.articulos.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class Categoria {
	
	@Size(min = 3, max =60, message = "nombre: entre 3 - 60 caracteres")
	@NotEmpty(message = "nombre no puede estar vacio")
	@Pattern(regexp = "([A-Za-záéíóúÁÉÍÚÓÑñ]+\\s?)+", message = "letras con o sin espacios")
	private String nombre;
	
	@Size(min = 3, max =60, message = "descripcion: entre 3 - 60 caracteres")
	@NotEmpty(message = "descripcion no puede estar vacio")
	@Pattern(regexp = "([A-Za-záéíóúÁÉÍÚÓÑñ]+\\s?)+", message = "letras y espacios")
	private String descripcion;
	
	private boolean alta;
	
	//una vez hecha la asociacion en la clase Libro aqui debemos indicar la asociacion inversa
	//cascade indica como se puede propagar una operacion desde el dato actual
	//con cascade tipo ALL = una operacion aplicada a una categorai pueda ser propagada a los libros asociados
	//mappedBy = nombre de la tabla, que es el nombre de la clase en minuscula
	
	@OneToMany(mappedBy = "categoria")
	private List<Articulo> articulos = new ArrayList<Articulo>();
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	public Categoria() {
		// TODO Auto-generated constructor stub
	}

	public Categoria(String nombre, String descripcion) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isAlta() {
		return alta;
	}

	public void setAlta(boolean alta) {
		this.alta = alta;
	}

	public List<Articulo> getArticulos() {
		return articulos;
	}

	public void setArticulos(List<Articulo> articulos) {
		this.articulos = articulos;
	}


	
	
	
}
