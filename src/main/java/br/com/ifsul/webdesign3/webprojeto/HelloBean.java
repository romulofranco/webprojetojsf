/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ifsul.webdesign3.webprojeto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
public class HelloBean implements Serializable {

    private static final Logger logger = Logger.getLogger(HelloBean.class);
    private String welcome;

    private Requisito requisitoAtual;
    private List<Requisito> requisitoList;
    private List<Requisito> requisitoListFilter;

    @PostConstruct
    public void init() {
        this.welcome = "Olá tudo bem?";
        this.requisitoAtual = new Requisito();
        this.requisitoList = new ArrayList<Requisito>();
    }

    public void message(String detail, String summary, FacesMessage.Severity severity) {
        FacesMessage message = new FacesMessage(severity, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void salvarRequisito() {
        logger.info("Requisito: " + this.requisitoAtual);
        
        if (requisitoAtual.getDescricao().isEmpty()) {
            message("Falha ao salvar requisito", "Salvar requisito", FacesMessage.SEVERITY_ERROR);
            return;
        }
        this.requisitoList.add(requisitoAtual);
        this.requisitoAtual = new Requisito();
        message("Requisito salvo com sucesso", "Salvar requisito", FacesMessage.SEVERITY_INFO);
    }

    public String getWelcome() {
        return welcome;
    }

    public void setWelcome(String welcome) {
        this.welcome = welcome;
    }

    public Requisito getRequisitoAtual() {
        return requisitoAtual;
    }

    public void setRequisitoAtual(Requisito requisitoAtual) {
        this.requisitoAtual = requisitoAtual;
    }

    public List<Requisito> getRequisitoList() {
        return requisitoList;
    }

    public void setRequisitoList(List<Requisito> requisitoList) {
        this.requisitoList = requisitoList;
    }

    public List<Requisito> getRequisitoListFilter() {
        return requisitoListFilter;
    }

    public void setRequisitoListFilter(List<Requisito> requisitoListFilter) {
        this.requisitoListFilter = requisitoListFilter;
    }

}
