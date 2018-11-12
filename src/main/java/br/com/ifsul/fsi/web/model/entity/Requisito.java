/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ifsul.fsi.web.model.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.json.JSONObject;

/**
 *
 * @author romulo
 */
@Entity
@Table(name = "requisito")
public class Requisito implements Serializable {

    public static final String REQ_POTENCIAL_RISCO_ALTO = "a";
    public static final String REQ_POTENCIAL_RISCO_MEDIO = "m";
    public static final String REQ_POTENCIAL_RISCO_BAIXO = "b";
    public static final String REQ_POTENCIAL_RISCO_SEM = "s";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String usuario;
    private String descricao;
    private String logChat;
    private String potencialRisco;
    private String nlu;
    private String toneAnalyzer;
    private String obs;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataRequisito;

    private Double anger;
    private Double disgust;
    private Double fear;
    private Double joy;
    private Double sadness;
    
    private String tone1;
    private Double tone1Value;
    
    private String tone2;
    private Double tone2Value;

    public Requisito() {
    }

    public Requisito(String usuario, String nlu, String toneAnalyzer, String descricao, String logChat, String tone1, Double tone1Value, String tone2, Double tone2Value) {
        JSONObject json = new JSONObject(nlu);
        JSONObject doc = json.getJSONObject("emotion");
        this.anger = (double) doc.get("anger");
        this.disgust = (double) doc.get("disgust");
        this.fear = (double) doc.get("fear");
        this.joy = (double) doc.get("joy");
        this.sadness = (double) doc.get("sadness");
        this.descricao = descricao;
        this.logChat = logChat;
        this.usuario = usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLogChat() {
        return logChat;
    }

    public void setLogChat(String logChat) {
        this.logChat = logChat;
    }

    public String getPotencialRisco() {
        return potencialRisco;
    }

    public String getPotencialRiscoImg() {
        if (this.potencialRisco == null) {
            return "img/sem-risco.png";
        }

        if (this.potencialRisco.equals(REQ_POTENCIAL_RISCO_MEDIO)) {
            return "img/medio-risco.png";
        } else if (this.potencialRisco.equals(REQ_POTENCIAL_RISCO_ALTO)) {
            return "img/alto-risco.png";
        } else if (this.potencialRisco.equals(REQ_POTENCIAL_RISCO_BAIXO)) {
            return "img/baixo-risco.png";
        }

        return "img/sem-risco.png";
    }

    public void setPotencialRisco(String potencialRisco) {
        this.potencialRisco = potencialRisco;
    }

    public String getNlu() {
        return nlu;
    }

    public void setNlu(String nlu) {
        this.nlu = nlu;
    }

    public String getToneAnalyzer() {
        return toneAnalyzer;
    }

    public void setToneAnalyzer(String toneAnalyzer) {
        this.toneAnalyzer = toneAnalyzer;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public Date getDataRequisito() {
        return dataRequisito;
    }

    public void setDataRequisito(Date data) {
        this.dataRequisito = data;
    }

    public Double getAnger() {
        return anger;
    }

    public void setAnger(Double anger) {
        this.anger = anger;
    }

    
    public Double getFear() {
        return fear;
    }

    public void setFear(Double fear) {
        this.fear = fear;
    }

    public Double getJoy() {
        return joy;
    }

    public void setJoy(Double joy) {
        this.joy = joy;
    }

    public Double getSadness() {
        return sadness;
    }

    public void setSadness(Double sadness) {
        this.sadness = sadness;
    }

    @Override
    public String toString() {
        return "Requisito{" + "id=" + id + ", usuario=" + usuario + ", descricao=" + descricao + ", logChat=" + logChat + ", potencialRisco=" + potencialRisco + ", nlu=" + nlu + ", toneAnalyzer=" + toneAnalyzer + ", obs=" + obs + ", dataRequisito=" + dataRequisito + ", anger=" + anger + ", disgust=" + disgust + ", fear=" + fear + ", joy=" + joy + ", sadness=" + sadness + '}';
    }

    public Double getDisgust() {
        return disgust;
    }

    public void setDisgust(Double disgust) {
        this.disgust = disgust;
    }

}
