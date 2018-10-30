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

    private final static Logger logger = Logger.getLogger(HelloBean.class);

    private Requisito requisitoAtual;
    private List<Requisito> requisitoList;
    private List<Requisito> requisitoListFilter;

    @PostConstruct
    public void init() {
        this.requisitoAtual = new Requisito();
        this.requisitoList = RequisitoDAO.getInstance().getAll(Requisito.class);
    }

    public void printRequisito() {
        logger.info("Requisito:" + this.requisitoAtual);
    }

    public void salvarRequisito() {
        requisitoAtual = RequisitoDAO.getInstance().save(requisitoAtual);
        requisitoList = RequisitoDAO.getInstance().getAll(Requisito.class);
        requisitoAtual = new Requisito();
        
    }

    public void processaRequisito() {
        String value = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
        System.out.println("ID Requisito " + value);
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
