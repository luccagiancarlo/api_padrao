package br.com.luccasoftware.api_dw.dto;


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
    private Integer tt_ins;
    private Integer tt_ins_homologados;
    private Integer tt_ins_afro;
    private Integer tt_ins_pcd;
    private Integer tt_ins_isentos;
    private Double taxa;
    private String dt_aplicacao_po;
    private String cd_concurso;


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

    public Integer getTt_ins() {
        return tt_ins;
    }

    public void setTt_ins(Integer tt_ins) {
        this.tt_ins = tt_ins;
    }

    public Integer getTt_ins_homologados() {
        return tt_ins_homologados;
    }

    public void setTt_ins_homologados(Integer tt_ins_homologados) {
        this.tt_ins_homologados = tt_ins_homologados;
    }

    public Integer getTt_ins_afro() {
        return tt_ins_afro;
    }

    public void setTt_ins_afro(Integer tt_ins_afro) {
        this.tt_ins_afro = tt_ins_afro;
    }

    public Integer getTt_ins_pcd() {
        return tt_ins_pcd;
    }

    public void setTt_ins_pcd(Integer tt_ins_pcd) {
        this.tt_ins_pcd = tt_ins_pcd;
    }

    public Integer getTt_ins_isentos() {
        return tt_ins_isentos;
    }

    public void setTt_ins_isentos(Integer tt_ins_isentos) {
        this.tt_ins_isentos = tt_ins_isentos;
    }

    public Double getTaxa() {
        return taxa;
    }

    public void setTaxa(Double taxa) {
        this.taxa = taxa;
    }

    public String getDt_aplicacao_po() {
        return dt_aplicacao_po;
    }

    public void setDt_aplicacao_po(String dt_aplicacao_po) {
        this.dt_aplicacao_po = dt_aplicacao_po;
    }

    public String getCd_concurso() {
        return cd_concurso;
    }

    public void setCd_concurso(String cd_concurso) {
        this.cd_concurso = cd_concurso;
    }


    // Getters and setters
}
