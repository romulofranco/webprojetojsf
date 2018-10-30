package br.com.ifsul.fsi.web.model;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class DAOConexao {

	// nome da unidade de persistencia definia no persistence.xml
	private static final String UNIT_NAME = "PROJETO_JPA";

	private javax.persistence.EntityManagerFactory emf = null;

	private EntityManager em = null;

	public EntityManager getEntityManager() {

		if (emf == null) {
			emf = Persistence.createEntityManagerFactory(UNIT_NAME);
		}

		if (em == null) {
			em = emf.createEntityManager();
		}

		return em;
	}
}