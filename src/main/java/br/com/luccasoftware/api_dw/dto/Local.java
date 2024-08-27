package br.com.luccasoftware.api_dw.dto;

public class Local {

    private String concurso;
    private long id_local;
    private String cidade;
    private String escola;
    private String data_uso;
    private String periodo;
    private long cep;
    private String campos_adicionais;
    private long id_local_aplicacao_evento;


    public Local() {
    }

    public long getId_local() {
        return id_local;
    }

    public void setId_local(long id_local) {
        this.id_local = id_local;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEscola() {
        return escola;
    }

    public void setEscola(String escola) {
        this.escola = escola;
    }

    public String getData_uso() {
        return data_uso;
    }

    public void setData_uso(String data_uso) {
        this.data_uso = data_uso;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public long getCep() {
        return cep;
    }

    public void setCep(long cep) {
        this.cep = cep;
    }

    public String getCampos_adicionais() {
        return campos_adicionais;
    }

    public void setCampos_adicionais(String campos_adicionais) {
        this.campos_adicionais = campos_adicionais;
    }

    public long getId_local_aplicacao_evento() {
        return id_local_aplicacao_evento;
    }

    public void setId_local_aplicacao_evento(long id_local_aplicacao_evento) {
        this.id_local_aplicacao_evento = id_local_aplicacao_evento;
    }

    public String getConcurso() {
        return concurso;
    }

    public void setConcurso(String concurso) {
        this.concurso = concurso;
    }
}
