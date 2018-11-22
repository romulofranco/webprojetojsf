/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ifsul.fsi.web.controller;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author admin
 */
@ManagedBean(eager=true)
@SessionScoped
public class BaseBean implements Serializable {

    /**
     * Temas Primefaces *
     */
    public static final String[] POSSIBLE_THEMES = {"afterdark", "afternoon", "afterwork", "aristo", "black-tie",
        "blitzer", "bluesky", "casablanca", "cruze", "cupertino", "dark-hive", "dot-luv", "eggplant", "excite-bike",
        "flick", "glass-x", "home", "hot-sneaks", "humanity", "le-frog", "midnight", "mint-choc", "overcast",
        "pepper-grinder", "redmond", "rocket", "sam", "smoothness", "south-street", "start", "sunny",
        "swanky-purse", "trontastic", "bootstrap", "ui-darkness", "ui-lightness", "vader"};

    public void message(String title, String msg, FacesMessage.Severity severity) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(severity, title, msg));
    }

    public void executeJS(String command) {
        RequestContext reqCtx = RequestContext.getCurrentInstance();
        reqCtx.execute(command);
    }

    public String[] getThemes() {
        return (POSSIBLE_THEMES);
    }

}
