package br.com.luccasoftware.api_dw.jpa;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "concurso")
public class Concurso {

    @Id
    private Long id;

    @Column(name = "idContratante")
    private Long idContratante;

    private String nome;
    private String descricao;

    @Column(name = "dataInicioInscricao")
    private LocalDate dataInicioInscricao;

    @Column(name = "dataFinalInscricao")
    private LocalDate dataFinalInscricao;

    private String status;

    @Column(name = "numero_edital")
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

    public LocalDate getDataInicioInscricao() {
        return dataInicioInscricao;
    }

    public void setDataInicioInscricao(LocalDate dataInicioInscricao) {
        this.dataInicioInscricao = dataInicioInscricao;
    }

    public LocalDate getDataFinalInscricao() {
        return dataFinalInscricao;
    }

    public void setDataFinalInscricao(LocalDate dataFinalInscricao) {
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