package springboot.articulos.utilidadesSetup;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springboot.articulos.constantes.EstadosPedido;
import springboot.articulos.model.Articulo;
import springboot.articulos.model.Categoria;
import springboot.articulos.model.Pedido;
import springboot.articulos.model.ProductoPedido;
import springboot.articulos.model.Usuario;
import springboot.articulos.model.Valoracion;
import springboot.articulos.model.setup.SetUp;




@Service
@Transactional
public class ServicioSetUpImpl implements ServicioSetUp{

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void prepararSetUp() {
		SetUp setUp = null;
		try {
			setUp =	(SetUp) entityManager.createQuery("select s from SetUp s").getSingleResult();
		} catch (NoResultException e) {
			System.out.println("no se encontro un registro de SetUp, empezamos setUp...");
		}		
	
		if( setUp == null || !setUp.isCompletado()) {
			//si no hay nada registrado en la tabla setup
			//o el unico elemento registrado no tiene completado a true
			//pues es cuando preparo una serie de registros para poder testear la tienda
			
			//preparar unas categorias para la tienda
			Categoria cCama = new Categoria("Cama", "Fundas de cama");
			cCama.setAlta(true);
			entityManager.persist(cCama);
			Categoria cManta = new Categoria("Mantas", "Mantas para la casa");
			cManta.setAlta(true);
			entityManager.persist(cManta);
			Categoria cDecoracion = new Categoria("Decoración", "Cojín o cortinas o alfombras");
			cDecoracion.setAlta(true);
			entityManager.persist(cDecoracion);
			Categoria cPremium = new Categoria("Premium", "Calidad superior");
			cPremium.setAlta(true);
			entityManager.persist(cPremium);
			
			//preparo unos articulos para la tienda:		
			Articulo a = new Articulo("Cojín", 5.60, "naranja", "habitacion");
			a.setCategoria(cDecoracion);
			a.setImagenPortada(copiarImagenBase("http://localhost:8083/imagenes_base/1.jpg"));
			a.setAlta(true);
			entityManager.persist(a);
			
			 a = new Articulo("Funda nordica", 35.99, "flores verde", "habitacion");
			a.setCategoria(cCama);
			a.setImagenPortada(copiarImagenBase("http://localhost:8083/imagenes_base/2.jpg"));
			a.setAlta(true);
			entityManager.persist(a);
			
			a = new Articulo("Cortina", 8.90, "rosa", "habitacion");
			a.setCategoria(cDecoracion);
			a.setImagenPortada(copiarImagenBase("http://localhost:8083/imagenes_base/3.jpg"));
			a.setAlta(true);
			entityManager.persist(a);
			
			 a = new Articulo("Alfombra", 49.50, "negra jardín", "casa");
			a.setCategoria(cDecoracion);
			a.setImagenPortada(copiarImagenBase("http://localhost:8083/imagenes_base/4.jpg"));
			a.setAlta(true);
			entityManager.persist(a);
			
			 a = new Articulo("Edredon", 95.00, "blanco", "cama");
			a.setCategoria(cPremium);
			a.setImagenPortada(copiarImagenBase("http://localhost:8083/imagenes_base/5.jpg"));
			a.setAlta(true);
			entityManager.persist(a);
			
			 a = new Articulo("Cojín", 12.60, "rosa", "habitacion");
			a.setCategoria(cDecoracion);
			a.setImagenPortada(copiarImagenBase("http://localhost:8083/imagenes_base/6.jpg"));
			a.setAlta(true);
			entityManager.persist(a);
			
			a = new Articulo("Funda nordica", 35.99, "flores rosas", "habitacion");
			a.setCategoria(cCama);
			a.setImagenPortada(copiarImagenBase("http://localhost:8083/imagenes_base/7.jpg"));
			a.setAlta(true);
			entityManager.persist(a);
			
			 a = new Articulo("Cortina", 29.50, "mostaza", "casa");
			a.setCategoria(cDecoracion);
			a.setImagenPortada(copiarImagenBase("http://localhost:8083/imagenes_base/8.jpg"));
			a.setAlta(true);
			entityManager.persist(a);
			
			a = new Articulo("Cortina", 25.99, "flores", "habitacion");
			a.setCategoria(cDecoracion);
			a.setImagenPortada(copiarImagenBase("http://localhost:8083/imagenes_base/9.jpg"));
			a.setAlta(true);
			entityManager.persist(a);
			
			a = new Articulo("Alfombra baño", 8.90, "blanco", "baño");
			a.setCategoria(cDecoracion);
			a.setImagenPortada(copiarImagenBase("http://localhost:8083/imagenes_base/10.jpg"));
			a.setAlta(true);
			entityManager.persist(a);
			
			 a = new Articulo("Alfombra baño", 8.00, "rosa", "baño");
			a.setCategoria(cDecoracion);
			a.setImagenPortada(copiarImagenBase("http://localhost:8083/imagenes_base/11.jpg"));
			a.setAlta(true);
			entityManager.persist(a);
			
			 a = new Articulo("Edredon", 99.50, "azul", "habitacion");
			a.setCategoria(cPremium);
			a.setImagenPortada(copiarImagenBase("http://localhost:8083/imagenes_base/12.jpg"));
			a.setAlta(true);
			entityManager.persist(a);
			
			 a = new Articulo("Edredon", 65.00, "rosa", "cama");
			a.setCategoria(cCama);
			a.setImagenPortada(copiarImagenBase("http://localhost:8083/imagenes_base/13.jpg"));
			a.setAlta(true);
			entityManager.persist(a);
			
			 a = new Articulo("Manta", 25.60, "naranja", "casa");
			a.setCategoria(cCama);
			a.setImagenPortada(copiarImagenBase("http://localhost:8083/imagenes_base/14.jpg"));
			a.setAlta(true);
			entityManager.persist(a);
			
			 a = new Articulo("Manta", 25.60, "naranja", "casa");
			a.setCategoria(cCama);
			a.setImagenPortada(copiarImagenBase("http://localhost:8083/imagenes_base/15.jpg"));
			a.setAlta(true);
			entityManager.persist(a);
			
			a = new Articulo("Alfombra", 58.90, "amarilla", "salon");
			a.setCategoria(cDecoracion);
			a.setImagenPortada(copiarImagenBase("http://localhost:8083/imagenes_base/16.jpg"));
			a.setAlta(true);
			entityManager.persist(a);
			
			 a = new Articulo("Cojin", 14.50, "teja", "salon");
			a.setCategoria(cDecoracion);
			a.setImagenPortada(copiarImagenBase("http://localhost:8083/imagenes_base/17.jpg"));
			a.setAlta(true);
			entityManager.persist(a);
			
			a = new Articulo("Funda nordica", 68.90, "blanca", "habitacion");
			a.setCategoria(cPremium);
			a.setImagenPortada(copiarImagenBase("http://localhost:8083/imagenes_base/18.jpg"));
			a.setAlta(true);
			entityManager.persist(a);
			
			Usuario u = new Usuario ("Maria", "Lopez", "maria@gmail.com", "123");
			u.setAvatar(copiarImagenBase("http://localhost:8083/imagenes_base_usuarios/u1.jpg"));
			entityManager.persist(u);

			 u = new Usuario ("Lucas", "Gomez", "lucas@gmail.com", "123");
			 u.setAvatar(copiarImagenBase("http://localhost:8083/imagenes_base_usuarios/u2.jpg"));
			 entityManager.persist(u);
			
			 u = new Usuario ("Alicia", "Amo", "alicia@gmail.com", "123");
			 u.setAvatar(copiarImagenBase("http://localhost:8083/imagenes_base_usuarios/u3.jpg"));
			 entityManager.persist(u);
			 
			//Preparamos pedido para la tienda
				Pedido p = new Pedido();
				p.setNombreCompleto("Nombre Completo");
				p.setDireccion("Info Direccion 120");
				p.setProvincia("Info provincia");
				p.setEstado("Estado del pedido");
				p.setNumeroTarjeta("5126123520");
				p.setTipoTarjeta("visa");
				p.setTitularTarjeta("Info titular tarjeta");
				p.setUsuario(u);
				p.setCodigo_descuento("Codigo de descuento 01");
				p.setCodigoPostal("28087");
				p.setCvv("458");
				p.setEmail("prueb@gmail.com");
				p.setExpiracion("Caducidad de la tarjeta");
				p.setTelefono("695412854");
				entityManager.persist(p);
				ProductoPedido pp1 = new ProductoPedido();
				pp1.setPedido(p);
				pp1.setArticulo(a);
				pp1.setCantidad(2);
				entityManager.persist(pp1);
				
				//vamos a registrar 100 articulos de prueba para hacer la paginacion
				/*String nombre = "cojin ";
				Double precio = 15.99;
				String color = "rosa";
				String estancia = "habitación";
				for (int i = 0; i < 22; i++) {
				    Articulo aNuevo = new Articulo (nombre + i, precio + i/100, color, estancia);
				    aNuevo.setAlta(true);
				    aNuevo.setCategoria(cDecoracion);
				    aNuevo.setImagenPortada(copiarImagenBase("http://localhost:8083/imagenes_base/cojin" + i + ".jpg"));
				    entityManager.persist(aNuevo);
				}*/

				
				p = new Pedido();
				p.setNombreCompleto("Alicia Amo");
				p.setDireccion("Centro Nelson");
				p.setProvincia("Madrid");
				p.setEstado(EstadosPedido.TERMINADO);
				p.setNumeroTarjeta("1234 5216 9841 1245");
				p.setTipoTarjeta("visa");
				p.setTitularTarjeta("Alicia Amo");
				p.setUsuario(u);
				p.setCodigo_descuento("HolaSummer");
				p.setCodigoPostal("28018");
				p.setCvv("568");
				p.setEmail("alicia@gmail.com");
				p.setExpiracion("12/26");
				p.setTelefono("69356845");
				entityManager.persist(p);
				pp1 = new ProductoPedido();
				pp1.setPedido(p);
				pp1.setArticulo(a);
				pp1.setCantidad(4);
				entityManager.persist(pp1);
				
			//vamos a hacer el set up de valoraciones
				Valoracion v = new Valoracion("Perfecto", "La tela es de calidad y el estampado tambien", 0 , u, a);
				entityManager.persist(v);
				
				 v = new Valoracion("No es lo que esperaba", "La tela esta bien pero la textura no me gusta", 4 , u, a);
				entityManager.persist(v);
				
				 v = new Valoracion("Bastante bien", "Lo que esperaba, esta bastante bien", 2 , u, a);
				entityManager.persist(v);
				
			//una vez realizados los registros iniciales
			//vamos a marcar el setup como completado
			SetUp registroSetUp = new SetUp();
			registroSetUp.setCompletado(true);
			entityManager.persist(registroSetUp);
			
		}//end comprobacion setup
	}//end prepararSetUp	
	
	private byte[] copiarImagenBase(String ruta_origen) {
		byte[] info = null;
		try {
			 URL url = new URL(ruta_origen);
			 info = IOUtils.toByteArray(url);
			 
			} catch (IOException e) {
			System.out.println("no pude copiar imagen base");
			e.printStackTrace();
		}
		return info;
	}//end copiarImagenBase
	
}//end class
