package br.com.ifsul.fsi.web.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

public abstract class DAOImpl<T, I extends Serializable> implements DAO<T, I> {

    private final static Logger logger = Logger.getLogger(DAOImpl.class);

    private DAOConexao conexao;

    @Override
    public T save(T entity) throws Exception  {
        T saved = null;
        this.getEntityManager().getTransaction().begin();
        saved = this.getEntityManager().merge(entity);
        this.getEntityManager().flush();
        this.getEntityManager().getTransaction().commit();
        return saved;
    }

    public void insert(T entity) {
        try {
            this.getEntityManager().getTransaction().begin();
            this.getEntityManager().persist(entity);
            this.getEntityManager().flush();
            this.getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            this.conexao.getEntityManager().getTransaction().rollback();
        }
    }

    public void delete(Class<T> classe, Integer id) {
        try {
            EntityManager em = this.getEntityManager();
            em.getTransaction().begin();
            Query query = em.createQuery("delete from " + classe.getSimpleName() + " o where o.id = :id");
            query.setParameter("id", id);
            query.executeUpdate();
            em.flush();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            this.conexao.getEntityManager().getTransaction().rollback();
        }
    }

    public void delete(Class<T> classe, String id) {
        try {
            EntityManager em = this.getEntityManager();
            em.getTransaction().begin();
            Query query = em.createQuery("delete from " + classe.getSimpleName() + " o where o.username = :id");
            query.setParameter("id", id);
            query.executeUpdate();
            em.flush();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            this.conexao.getEntityManager().getTransaction().rollback();
        }
    }

    @Override
    public void remove(T entity) {
        this.getEntityManager().getTransaction().begin();
        this.getEntityManager().remove(entity);
        this.getEntityManager().getTransaction().commit();

    }

    @Override
    public T getById(Class<T> classe, I pk) {

        try {
            return this.getEntityManager().find(classe, pk);
        } catch (NoResultException e) {
            return null;
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> getAll(Class<T> classe) {
        return this.getEntityManager().createQuery("select o from " + classe.getSimpleName() + " o").getResultList();
    }

    @Override
    public synchronized EntityManager getEntityManager() {

        if (this.conexao == null) {
            this.conexao = new DAOConexao();
        }

        return this.conexao.getEntityManager();
    }

}
