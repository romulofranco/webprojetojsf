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

    public String getJson() {
        JSONObject jsonObj = new JSONObject();

        jsonObj.put("usuario", this.usuario);
        jsonObj.put("descricao", this.descricao);
        jsonObj.put("logChat", "");
        jsonObj.put("potencialRisco", this.potencialRisco);
        jsonObj.put("anger", this.anger);
        jsonObj.put("disgust", this.disgust);
        jsonObj.put("fear", this.fear);
        jsonObj.put("joy", this.joy);
        jsonObj.put("sadness", this.sadness);
        jsonObj.put("tone1", this.tone1);
        jsonObj.put("tone1Value", this.tone1Value);
        jsonObj.put("obs", this.obs);
        return jsonObj.toString();
    }

    public Requisito() {
    }

    public Requisito(String usuario, String nlu, String toneAnalyzer, String descricao, String logChat, String tone1, Double tone1Value) {
        JSONObject json = new JSONObject(nlu);
        JSONObject doc = json.getJSONObject("emotion");
        this.anger = ((double) doc.get("anger")) * 100;
        this.disgust = ((double) doc.get("disgust")) * 100;
        this.fear = ((double) doc.get("fear")) * 100;
        this.joy = ((double) doc.get("joy")) * 100;
        this.sadness = ((double) doc.get("sadness")) * 100;
        this.descricao = descricao;
        this.logChat = logChat;
        this.usuario = usuario;
        this.tone1 = tone1;
        this.tone1Value = tone1Value;

        this.dataRequisito = new Date(System.currentTimeMillis());
        calculaPotencialRisco();
    }

    public void calculaPotencialRisco() {
        if (this.anger > 20) {
            setPotencialRisco(Requisito.REQ_POTENCIAL_RISCO_ALTO);
        }

        if (this.fear > 30) {
            setPotencialRisco(REQ_POTENCIAL_RISCO_MEDIO);
        }

        if (this.disgust > 40) {
            setPotencialRisco(REQ_POTENCIAL_RISCO_BAIXO);
        }

        if (this.sadness > 30) {
            setPotencialRisco(REQ_POTENCIAL_RISCO_MEDIO);
        }

        if (this.joy > 40) {
            setPotencialRisco(REQ_POTENCIAL_RISCO_SEM);
        }

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

    public String getPotencialRiscoDesc() {
        if (this.potencialRisco == null) {
            return "";
        }

        if (this.potencialRisco.equals(REQ_POTENCIAL_RISCO_MEDIO)) {
            return "Médio risco";
        } else if (this.potencialRisco.equals(REQ_POTENCIAL_RISCO_ALTO)) {
            return "Alto risco";
        } else if (this.potencialRisco.equals(REQ_POTENCIAL_RISCO_BAIXO)) {
            return "Baixo risco";
        }

        return "Sem risco";
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

    public String getTone1() {
        return tone1;
    }

    public void setTone1(String tone1) {
        this.tone1 = tone1;
    }

    public Double getTone1Value() {
        return tone1Value;
    }

    public void setTone1Value(Double tone1Value) {
        this.tone1Value = tone1Value;
    }

}
