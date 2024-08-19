package br.com.luccasoftware.api_dw.jpa;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

import java.time.LocalDate;


public class Concurso {


    private Long id;
    private Long idContratante;
    private String nome;
    private String descricao;
    private String dataInicioInscricao;
    private String dataFinalInscricao;
    private String status;
    private String numeroEdital;
    private String edital;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdContratante() {
        return idContratante;
    }

    public void setIdContratante(Long idContratante) {
        this.idContratante = idContratante;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataInicioInscricao() {
        return dataInicioInscricao;
    }

    public void setDataInicioInscricao(String dataInicioInscricao) {
        this.dataInicioInscricao = dataInicioInscricao;
    }

    public String getDataFinalInscricao() {
        return dataFinalInscricao;
    }

    public void setDataFinalInscricao(String dataFinalInscricao) {
        this.dataFinalInscricao = dataFinalInscricao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNumeroEdital() {
        return numeroEdital;
    }

    public void setNumeroEdital(String numeroEdital) {
        this.numeroEdital = numeroEdital;
    }

    public String getEdital() {
        return edital;
    }

    public void setEdital(String edital) {
        this.edital = edital;
    }
}