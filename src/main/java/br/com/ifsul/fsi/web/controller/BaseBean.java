/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ifsul.fsi.web.controller;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.component.export.ExcelOptions;
import org.primefaces.component.export.PDFOptions;

/**
 *
 * @author admin
 */
public class BaseBean implements Serializable {

    public void message(String title, String msg, FacesMessage.Severity severity) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(severity, title, msg));
    }
}
