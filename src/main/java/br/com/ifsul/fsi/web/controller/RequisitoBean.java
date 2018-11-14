/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ifsul.fsi.web.controller;

import br.com.ifsul.fsi.web.model.entity.Requisito;
import br.com.ifsul.fsi.web.model.dao.RequisitoDAO;
import java.io.Serializable;
import java.util.Date;
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
public class RequisitoBean extends BaseBean implements Serializable {

    private final static Logger logger = Logger.getLogger(RequisitoBean.class);

    private Requisito requisitoAtual;
    private List<Requisito> requisitoList;
    private List<Requisito> requisitoListFilter;

    private final String redirect = "/protegido/requisito.xhtml?faces-redirect=true";

    public String menuAction() {
        return redirect;
    }

    @PostConstruct
    public void init() {
        this.requisitoAtual = new Requisito();
        this.requisitoList = RequisitoDAO.getInstance().getAll(Requisito.class);
    }

    public void printRequisito() {
        logger.info("Requisito:" + this.requisitoAtual);
    }

    public void salvarRequisito() {
        this.printRequisito();
        try {
            requisitoAtual = RequisitoDAO.getInstance().save(requisitoAtual);
            requisitoList = RequisitoDAO.getInstance().getAll(Requisito.class);
            requisitoAtual = new Requisito();
            message("Salvar registro", "Requisito salvo com sucesso", FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(RequisitoBean.class.getName()).log(Level.SEVERE, null, ex);
            message("Salvar registro", "Falha ao tentar salvar o registro", FacesMessage.SEVERITY_ERROR);
        }
    }

    public void deletarRequisito() {
        RequisitoDAO.getInstance().delete(Requisito.class, requisitoAtual.getId());
        requisitoAtual = null;
        requisitoList = RequisitoDAO.getInstance().getAll(Requisito.class);
        message("Deletar registro", "Requisito excluído com sucesso", FacesMessage.SEVERITY_INFO);
    }

    public void novoRegistro() {
        this.requisitoAtual = new Requisito();
        this.requisitoAtual.setDataRequisito(new Date(System.currentTimeMillis()));
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
