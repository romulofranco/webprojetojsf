/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ifsul.webdesign3.webprojeto;

import java.io.Serializable;

/**
 *
 * @author romulo
 */
public class Requisito implements Serializable {

    public static final String REQ_STATUS_ALTO_RISCO = "a";
    public static final String REQ_STATUS_MEDIO_RISCO = "m";
    public static final String REQ_STATUS_BAIXO_RISCO = "b";
    public static final String REQ_STATUS_SEM_RISCO = "s";

    private Integer id;
    private String descricao;
    private String status;
    private String obs;
    private String username;
    private String toneAnalyzer;
    private String nlu;

    public Requisito() {
    }

    public Requisito(Integer id, String descricao, String status) {
        this.id = id;
        this.descricao = descricao;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatus() {
        if (this.status == null) { 
            return "img/sem-risco.png";
        }
        
        if (this.status.equalsIgnoreCase(REQ_STATUS_ALTO_RISCO)) {
            return "img/alto-risco.png";
        } else if (this.status.equalsIgnoreCase(REQ_STATUS_MEDIO_RISCO)) {
            return "img/medio-risco.png";
        } else if (this.status.equalsIgnoreCase(REQ_STATUS_BAIXO_RISCO)) {
            return "img/baixo-risco.png";
        }
        
        return "img/sem-risco.png";
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToneAnalyzer() {
        return toneAnalyzer;
    }

    public void setToneAnalyzer(String toneAnalyzer) {
        this.toneAnalyzer = toneAnalyzer;
    }

    public String getNlu() {
        return nlu;
    }

    public void setNlu(String nlu) {
        this.nlu = nlu;
    }

    @Override
    public String toString() {
        return "Requisito{" + "id=" + id + ", descricao=" + descricao + ", status=" + status + ", obs=" + obs + ", username=" + username + ", toneAnalyzer=" + toneAnalyzer + ", nlu=" + nlu + '}';
    }

}
