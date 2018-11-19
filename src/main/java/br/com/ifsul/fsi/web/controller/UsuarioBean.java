/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ifsul.fsi.web.controller;

import br.com.ifsul.fsi.web.model.dao.RequisitoDAO;
import br.com.ifsul.fsi.web.model.entity.Usuario;
import br.com.ifsul.fsi.web.model.dao.UsuarioDAO;
import br.com.ifsul.fsi.web.model.entity.Requisito;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;

/**
 *
 * @author admin
 */
@ManagedBean
@SessionScoped
public class UsuarioBean extends BaseBean implements Serializable {

    private final static Logger logger = Logger.getLogger(UsuarioBean.class);

    private Usuario usuarioAtual;
    private List<Usuario> usuarioList;
    private List<Usuario> usuarioListFilter;
    
    private final String redirect = "/protegido/usuario.xhtml?faces-redirect=true";

    public String menuAction() {
        return redirect;
    }

    @PostConstruct
    public void init() {
        this.usuarioAtual = new Usuario();
        this.usuarioList = UsuarioDAO.getInstance().getAll(Usuario.class);
    }

    public void printUsuario() {
        logger.info("Usuario:" + this.usuarioAtual);
    }

    public void salvarUsuario() {
        try {
            this.printUsuario();
            usuarioAtual = UsuarioDAO.getInstance().save(usuarioAtual);
            usuarioList = UsuarioDAO.getInstance().getAll(Usuario.class);
            usuarioAtual = new Usuario();
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
     public void deletarUsuario() {
        UsuarioDAO.getInstance().delete(Usuario.class, usuarioAtual.getUsername());
        usuarioAtual = null;
        usuarioList = UsuarioDAO.getInstance().getAll(Usuario.class);
        message("Deletar registro", "Usuário excluído com sucesso", FacesMessage.SEVERITY_INFO);
    }
    
    public void novoRegistro() {
        this.usuarioAtual = new Usuario();
    }

    public void processaUsuario() {
        String value = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
        System.out.println("ID Usuario " + value);
    }

    public Usuario getUsuarioAtual() {
        return usuarioAtual;
    }

    public void setUsuarioAtual(Usuario usuarioAtual) {
        this.usuarioAtual = usuarioAtual;
    }

    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    public List<Usuario> getUsuarioListFilter() {
        return usuarioListFilter;
    }

    public void setUsuarioListFilter(List<Usuario> usuarioListFilter) {
        this.usuarioListFilter = usuarioListFilter;
    }

}
