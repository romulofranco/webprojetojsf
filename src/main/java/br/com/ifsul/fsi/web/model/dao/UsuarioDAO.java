/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ifsul.fsi.web.model.dao;

import br.com.ifsul.fsi.web.model.DAOImpl;
import br.com.ifsul.fsi.web.model.entity.Usuario;
import java.io.Serializable;
import org.apache.log4j.Logger;

/**
 *
 * @author romulo
 */
public class UsuarioDAO extends DAOImpl<Usuario, String> implements Serializable {

    private static final long serialVersionUID = 1L;

    final static Logger logger = Logger.getLogger(RequisitoDAO.class);

    private static UsuarioDAO INSTANCE;

    private UsuarioDAO() {

    }

    public synchronized static UsuarioDAO getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UsuarioDAO();
        }
        return INSTANCE;
    }
    
}
