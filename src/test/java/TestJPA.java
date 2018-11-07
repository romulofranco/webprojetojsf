

import br.com.ifsul.webdesign3.webprojeto.ToDo;
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
		TypedQuery<ToDo> query = em.createQuery("SELECT t from ToDo t", ToDo.class);
		boolean createNewEntries = (query.getResultList().size() == 0);

		if (createNewEntries) {
			org.junit.Assert.assertTrue(query.getResultList().size() == 0);
			ToDo todo = new ToDo();
			todo.setCategoria("Teste");
                        todo.setTarefa("Teste teste");
			em.persist(todo);
                        
			em.flush();
			em.getTransaction().commit();

		}

	}
        
      

}
