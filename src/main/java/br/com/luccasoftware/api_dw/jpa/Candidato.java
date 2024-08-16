package br.com.luccasoftware.api_dw.jpa;

public class Candidato {

    private String prefixo;
    private Long idCandidato;
    private Long idCargo;
    private String nome;
    private String sexo;
    private String nascimento;  // Usando String para a data
    private String cep;
    private String cidade;
    private String estado;
    private String status;
    private String tipoDef;
    private Boolean afro;
    private Boolean solicitouInscricaoNegro;

    // Getters and Setters

    public Long getIdCandidato() {
        return idCandidato;
    }

    public void setIdCandidato(Long idCandidato) {
        this.idCandidato = idCandidato;
    }

    public Long getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Long idCargo) {
        this.idCargo = idCargo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTipoDef() {
        return tipoDef;
    }

    public void setTipoDef(String tipoDef) {
        this.tipoDef = tipoDef;
    }

    public Boolean getAfro() {
        return afro;
    }

    public void setAfro(Boolean afro) {
        this.afro = afro;
    }

    public Boolean getSolicitouInscricaoNegro() {
        return solicitouInscricaoNegro;
    }

    public void setSolicitouInscricaoNegro(Boolean solicitouInscricaoNegro) {
        this.solicitouInscricaoNegro = solicitouInscricaoNegro;
    }

    public String getPrefixo() {
        return prefixo;
    }

    public void setPrefixo(String prefixo) {
        this.prefixo = prefixo;
    }
}
