/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ifsul.fsi.web.model.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author romulo
 */
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    public static final String USUARIO_ATIVO = "s";
    public static final String USUARIO_INATIVO = "n";
    public static final String USUARIO_TIPO_ANALISTA = "a";
    public static final String USUARIO_TIPO_CLIENTE = "c";

    @Id
    private String username;

    private String nome;
    private String senha;
    private String chatId;
    private String tipo;
    private String ativo;
    private String tema;

    public Usuario() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public String getAtivoImg() {
        if (this.ativo.equals(Usuario.USUARIO_ATIVO)) {
            return "img/sem-risco.png";
        } else {
            return "img/alto-risco.png";
        }
    }
    
     public String getTipoImg() {
        if (this.ativo.equals(Usuario.USUARIO_ATIVO)) {
            return "img/sem-risco.png";
        } else {
            return "img/alto-risco.png";
        }
    }

    public String getTema() {
        
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    @Override
    public String toString() {
        return "Usuario{" + "username=" + username + ", nome=" + nome + ", senha=" + senha + ", chatId=" + chatId + ", tipo=" + tipo + ", ativo=" + ativo + ", tema=" + tema + '}';
    }

}
