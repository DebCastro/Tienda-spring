package springboot.articulos.JPAImpl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springboot.articulos.ConstantesSQL.ConstantesSQL;
import springboot.articulos.model.Articulo;
import springboot.articulos.model.Categoria;
import springboot.articulos.model.Usuario;
import springboot.articulos.servicios.ServicioUsuario;


@Service
@Transactional
public class ServicioUsuarioJPAImpl implements ServicioUsuario{
	
	@Autowired
	private EntityManager entityManager;
	
	@Override
	public void registrarUsuario(Usuario u) {
			try {
				try {
					u.setAvatar(u.getFotoSubida().getBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("no pude procesar la foto subida");
					e.printStackTrace();
				}
			} catch ( NullPointerException e) {
				// TODO Auto-generated catch block
				System.out.println("no has subido ninguna foto");
				e.printStackTrace();
			}
			
		entityManager.persist(u);
		
	}//end registrarUsuario

	@Override
	public Usuario obtenerUsuarioPorEmailYpass(String email, String pass) {
		Query query = entityManager.createQuery("select u from Usuario u where u.email = :email and u.pass = :pass");
		query.setParameter("email", email);
		query.setParameter("pass", pass);
		
		List<Usuario> resultado = query.getResultList();
		if(resultado.size() == 0) {
			return null;
		}else {
			return resultado.get(0);
		}
	}//end obtenerUsuarioPorEmailYpass

	@Override
	public List<Usuario> obtenerUsuarios() {
		return entityManager.createQuery("SELECT u FROM Usuario u ORDER BY u.id DESC").getResultList();
	}

	@Override
	public void borrarUsuario(int id) {
		entityManager.remove(entityManager.find(Usuario.class, id));
	}

	@Override
	public Usuario obtenerUsuarioPorId(int id) {
		return (Usuario)entityManager.find(Usuario.class, id);
	}

	@Override
	public void guardarCambiosUsuario(Usuario u) {
		if(u.getFotoSubida().getSize() == 0) {
			System.out.println("no se subio una nueva foto, mantener foto");
			Usuario usuarioAnterior = entityManager.find(Usuario.class, u.getId());
			u.setAvatar(usuarioAnterior.getAvatar());
		}else {
			System.out.println("Asignar nueva foto");
			try {
				u.setAvatar(u.getFotoSubida().getBytes());
			} catch (IOException e) {
				System.out.println("No pude procesar la foto suya");
				e.printStackTrace();
			}
		}
		entityManager.merge(u);
	}
	
	
	@Override
	public List<Map<String, Object>> obtenerDatosUsuariosParaFormarJSON(int idUsuario) {
		Query query = entityManager.createNativeQuery(
				ConstantesSQL.SQL_OBTENER_DATOS_DE_USUARIO_PARA_JSON);
		query.setParameter("idUsuario", idUsuario);
		NativeQueryImpl nativeQuery = (NativeQueryImpl) query;
		nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		return nativeQuery.getResultList();
	}

	@Override
	public List<Map<String, Object>> obtenerMisDatosParaFormarJSON(int id) {
		Query query = entityManager.createNativeQuery(
				ConstantesSQL.SQL_OBTENER_MISDATOS_PARA_JSON);
		query.setParameter("idUsuario", id);
		NativeQueryImpl nativeQuery = (NativeQueryImpl) query;
		nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		return nativeQuery.getResultList();
	}

}
