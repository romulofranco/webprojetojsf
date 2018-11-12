package br.com.ifsul.fsi.web.controller;

import br.com.ifsul.fsi.web.model.dao.UsuarioDAO;
import br.com.ifsul.fsi.web.model.entity.Usuario;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1L;
    final static Logger logger = Logger.getLogger(LoginBean.class);

    public static final String HOME_PAGE_REDIRECT = "/protegido/requisito.xhtml?faces-redirect=true";
    public static final String LOGOUT_PAGE_REDIRECT = "/login.xtml?faces-redirect=true";

    private String username;
    private String userPassword;
    private Usuario usuarioAtual;

    public String login() {
        try {
            logger.info("Credenciais enviadas ao servidor " + this.username + " " + this.userPassword);
            
            // lookup the user based on user name and user password
            this.usuarioAtual = buscarUsuario(this.username, this.userPassword);

            if (this.usuarioAtual != null) {
                logger.debug("Usuário " + this.username + " efetuou login na aplicação");

                String redirect = HOME_PAGE_REDIRECT;
                checaNotificacoes();

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Inicial", "Bem vindo a aplicação"));

                return redirect;
            } else {
                // logger.debug("login failed for '{}'", username);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Login falhou", "Credenciais inválidas ou desconhecidas"));

                return null;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public void checaNotificacoes() {
        try {

        } catch (Exception e) {
            logger.error("Falha em obter notificacoes: " + e.getMessage());
        }

    }

    public String logout() {
        String identifier = this.username;
        this.usuarioAtual = null;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

        logger.debug("Usuario " + this.username + " - " + identifier + " efetuou logout na aplicação");
        return LOGOUT_PAGE_REDIRECT;
    }

    public boolean isLoggedIn() {
        return this.usuarioAtual != null;
    }

    public String isLoggedInForwardHome() {
        if (isLoggedIn()) {
            return HOME_PAGE_REDIRECT;
        }

        return null;
    }

    private Usuario buscarUsuario(String username, String password) {
        Usuario result = UsuarioDAO.getInstance().getById(Usuario.class, username);
        
        if (result == null) {
            return null;
        }
        
        if (result.getSenha().equals(password)) {
            return result;
        }

        return null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Usuario getUsuarioAtual() {
        return usuarioAtual;
    }

    public void setUsuarioAtual(Usuario usuarioAtual) {
        this.usuarioAtual = usuarioAtual;
    }

}
