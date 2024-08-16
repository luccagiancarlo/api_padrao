package br.com.luccasoftware.api_dw.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;


public class Cargo {

    private String prefixo;
    private Long id_cargo;
    private String numero;
    private String nome;
    private String sigla;
    private String cidade;
    private Integer vagas;
    private Integer vagaspne;
    private Integer vagasAfro;

    public String getPrefixo() {
        return prefixo;
    }

    public void setPrefixo(String prefixo) {
        this.prefixo = prefixo;
    }

    public Long getId_cargo() {
        return id_cargo;
    }

    public void setId_cargo(Long id_cargo) {
        this.id_cargo = id_cargo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Integer getVagas() {
        return vagas;
    }

    public void setVagas(Integer vagas) {
        this.vagas = vagas;
    }

    public Integer getVagaspne() {
        return vagaspne;
    }

    public void setVagaspne(Integer vagaspne) {
        this.vagaspne = vagaspne;
    }

    public Integer getVagasAfro() {
        return vagasAfro;
    }

    public void setVagasAfro(Integer vagasAfro) {
        this.vagasAfro = vagasAfro;
    }


    // Getters and setters
}
