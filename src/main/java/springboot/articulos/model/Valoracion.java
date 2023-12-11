package springboot.articulos.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "valoracion")
public class Valoracion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String titulo;
	private String descripcion;
	private int contador;
	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private Usuario usuario;
	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private Articulo articulo;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getContador() {
		return contador;
	}
	public void setContador(int contador) {
		this.contador = contador;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Articulo getArticulo() {
		return articulo;
	}
	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}
	public Valoracion(int id, String titulo, String descripcion, int contador, Usuario usuario, Articulo articulo) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.contador = contador;
		this.usuario = usuario;
		this.articulo = articulo;
	}
	
	public Valoracion() {
		// TODO Auto-generated constructor stub
	}
	public Valoracion(String titulo, String descripcion, int contador, Usuario usuario, Articulo articulo) {
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.contador = contador;
		this.usuario = usuario;
		this.articulo = articulo;
	}
}
