package br.uem.vestibular.api_padrao.jpa;

import jakarta.persistence.*;

@Entity
@Table(name = "SGV_USUARIO", schema = "SGV")
public class Usuario {

    @Id
    @Column(name = "CD_USUARIO")
    private Integer cdUsuario;

    @Column(name = "EN_EMAIL", nullable = false, length = 50)
    private String enEmail;

    @Column(name = "NM_USUARIO", nullable = false, length = 50)
    private String nmUsuario;

    @Column(name = "SE_USUARIO", nullable = false, length = 100)
    private String seUsuario;

    @Column(name = "CD_GESTOR")
    private Short cdGestor;

    @Column(name = "CD_SETOR", length = 15)
    private String cdSetor;

    @Column(name = "TP_USUARIO", length = 50)
    private String tpUsuario;

    @Column(name = "FL_INVENTARIO", length = 1)
    private String flInventario;

    @Column(name = "FL_RESPSETOR", length = 1)
    private String flRespsetor;

    @Column(name = "NU_MATRICULA")
    private Integer nuMatricula;

    @Column(name = "DE_PORTARIA", length = 20)
    private String dePortaria;

    @Column(name = "DE_CARGO", length = 200)
    private String deCargo;

    @Column(name = "TP_DAS", length = 10)
    private String tpDas;

    @Column(name = "TP_COMISSAO", length = 10)
    private String tpComissao;

    // Getters e Setters
    public Integer getCdUsuario() {
        return cdUsuario;
    }

    public void setCdUsuario(Integer cdUsuario) {
        this.cdUsuario = cdUsuario;
    }

    public String getEnEmail() {
        return enEmail;
    }

    public void setEnEmail(String enEmail) {
        this.enEmail = enEmail;
    }

    public String getNmUsuario() {
        return nmUsuario;
    }

    public void setNmUsuario(String nmUsuario) {
        this.nmUsuario = nmUsuario;
    }

    public String getSeUsuario() {
        return seUsuario;
    }

    public void setSeUsuario(String seUsuario) {
        this.seUsuario = seUsuario;
    }

    public Short getCdGestor() {
        return cdGestor;
    }

    public void setCdGestor(Short cdGestor) {
        this.cdGestor = cdGestor;
    }

    public String getCdSetor() {
        return cdSetor;
    }

    public void setCdSetor(String cdSetor) {
        this.cdSetor = cdSetor;
    }

    public String getTpUsuario() {
        return tpUsuario;
    }

    public void setTpUsuario(String tpUsuario) {
        this.tpUsuario = tpUsuario;
    }

    public String getFlInventario() {
        return flInventario;
    }

    public void setFlInventario(String flInventario) {
        this.flInventario = flInventario;
    }

    public String getFlRespsetor() {
        return flRespsetor;
    }

    public void setFlRespsetor(String flRespsetor) {
        this.flRespsetor = flRespsetor;
    }

    public Integer getNuMatricula() {
        return nuMatricula;
    }

    public void setNuMatricula(Integer nuMatricula) {
        this.nuMatricula = nuMatricula;
    }

    public String getDePortaria() {
        return dePortaria;
    }

    public void setDePortaria(String dePortaria) {
        this.dePortaria = dePortaria;
    }

    public String getDeCargo() {
        return deCargo;
    }

    public void setDeCargo(String deCargo) {
        this.deCargo = deCargo;
    }

    public String getTpDas() {
        return tpDas;
    }

    public void setTpDas(String tpDas) {
        this.tpDas = tpDas;
    }

    public String getTpComissao() {
        return tpComissao;
    }

    public void setTpComissao(String tpComissao) {
        this.tpComissao = tpComissao;
    }
}
