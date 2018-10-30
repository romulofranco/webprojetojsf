package br.com.ifsul.fsi.web.model.dao;

import br.com.ifsul.fsi.web.model.DAOImpl;
import br.com.ifsul.fsi.web.model.DAOImpl;
import br.com.ifsul.fsi.web.model.entity.Requisito;
import java.io.Serializable;

import org.apache.log4j.Logger;

public class RequisitoDAO extends DAOImpl<Requisito, Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    final static Logger logger = Logger.getLogger(RequisitoDAO.class);

    private static RequisitoDAO INSTANCE;

    private RequisitoDAO() {

    }

    public synchronized static RequisitoDAO getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RequisitoDAO();
        }
        return INSTANCE;
    }

}
