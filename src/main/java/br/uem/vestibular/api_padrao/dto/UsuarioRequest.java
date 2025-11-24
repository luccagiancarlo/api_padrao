package br.uem.vestibular.api_padrao.dto;

public class UsuarioRequest {

    private Integer cdUsuario;
    private String enEmail;
    private String nmUsuario;
    private String seUsuario;
    private Short cdGestor;
    private String cdSetor;
    private String tpUsuario;
    private String flInventario;
    private String flRespsetor;
    private Integer nuMatricula;
    private String dePortaria;
    private String deCargo;
    private String tpDas;
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
