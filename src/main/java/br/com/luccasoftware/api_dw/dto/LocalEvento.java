package br.com.luccasoftware.api_dw.dto;

public class LocalEvento {

    private Long id;
    private Long id_evento;
    private String nome;
    private String logradouro;
    private String bairro;
    private String numero;
    private String complemento;
    private String cep;
    private String cidade;
    private String uf;
    private Long ensalados;
    private Long id_data_periodo_evento;
    private Long quantidade_salas;
    private Long capacidade;

    public LocalEvento(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_evento() {
        return id_evento;
    }

    public void setId_evento(Long id_evento) {
        this.id_evento = id_evento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
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

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Long getEnsalados() {
        return ensalados;
    }

    public void setEnsalados(Long ensalados) {
        this.ensalados = ensalados;
    }

    public Long getId_data_periodo_evento() {
        return id_data_periodo_evento;
    }

    public void setId_data_periodo_evento(Long id_data_periodo_evento) {
        this.id_data_periodo_evento = id_data_periodo_evento;
    }

    public Long getQuantidade_salas() {
        return quantidade_salas;
    }

    public void setQuantidade_salas(Long quantidade_salas) {
        this.quantidade_salas = quantidade_salas;
    }

    public Long getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Long capacidade) {
        this.capacidade = capacidade;
    }
}
