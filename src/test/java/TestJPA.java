
import br.com.ifsul.fsi.web.model.entity.Requisito;        
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.Test;

/**
 *
 */

/**
 * @author romulofranco
 *
 */

public class TestJPA {

	private static final String PERSISTENCE_UNIT_NAME = "PROJETO_JPA";
	private EntityManagerFactory factory;

	/**
	 * @throws java.lang.Exception
	 */
	@Test
	public void runJPA() throws Exception {

		this.factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = this.factory.createEntityManager();

            
		// Begin a new local transaction so that we can persist a new entity
		em.getTransaction().begin();
		TypedQuery<Requisito> query = em.createQuery("SELECT t from Requisito t", br.com.ifsul.fsi.web.model.entity.Requisito.class);
		boolean createNewEntries = (query.getResultList().size() == 0);

		if (createNewEntries) {
			org.junit.Assert.assertTrue(query.getResultList().size() == 0);
			Requisito req = new Requisito();
			req.setDescricao("Teste");
                        req.setUsuario("user");
			em.persist(req);
                        
                        req = new Requisito();
			req.setDescricao("Teste 1");
                        req.setUsuario("user 1");
			em.persist(req);
                        
                        req = new Requisito();
			req.setDescricao("Teste 2");
                        req.setUsuario("user 1");
			em.persist(req);
			
			em.flush();
			em.getTransaction().commit();

		}

	}
        
      

}
