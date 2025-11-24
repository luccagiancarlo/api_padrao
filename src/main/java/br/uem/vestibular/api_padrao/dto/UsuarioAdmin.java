package br.uem.vestibular.api_padrao.dto;

public class UsuarioAdmin {

    private Integer cdUsuario;
    private String email;
    private String nome;
    private String cdSetor;
    private String deCargo;
    private String tpUsuario;

    public Integer getCdUsuario() {
        return cdUsuario;
    }

    public void setCdUsuario(Integer cdUsuario) {
        this.cdUsuario = cdUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCdSetor() {
        return cdSetor;
    }

    public void setCdSetor(String cdSetor) {
        this.cdSetor = cdSetor;
    }

    public String getDeCargo() {
        return deCargo;
    }

    public void setDeCargo(String deCargo) {
        this.deCargo = deCargo;
    }

    public String getTpUsuario() {
        return tpUsuario;
    }

    public void setTpUsuario(String tpUsuario) {
        this.tpUsuario = tpUsuario;
    }
}
