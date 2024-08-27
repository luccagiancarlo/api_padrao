package br.com.luccasoftware.api_dw.dto;

public class Evento {

    private Long id;
    private String concurso;
    private String descricao;
    private String status;
    private Double valorBase;
    private Double valorLanche;
    private Double sobraLanche;
    private Long idOrganizadora;
    private String tipoEvento;
    private Long idProjetoIcode;
    private Long idItemProjetoEventoIcode;
    private Long idItemProjetoLocacaoIcode;
    private Long idItemProjetoLancheIcode;



    public Evento(){

    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getValorBase() {
        return valorBase;
    }

    public void setValorBase(Double valorBase) {
        this.valorBase = valorBase;
    }

    public Double getValorLanche() {
        return valorLanche;
    }

    public void setValorLanche(Double valorLanche) {
        this.valorLanche = valorLanche;
    }

    public Double getSobraLanche() {
        return sobraLanche;
    }

    public void setSobraLanche(Double sobraLanche) {
        this.sobraLanche = sobraLanche;
    }

    public Long getIdOrganizadora() {
        return idOrganizadora;
    }

    public void setIdOrganizadora(Long idOrganizadora) {
        this.idOrganizadora = idOrganizadora;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public Long getIdProjetoIcode() {
        return idProjetoIcode;
    }

    public void setIdProjetoIcode(Long idProjetoIcode) {
        this.idProjetoIcode = idProjetoIcode;
    }

    public Long getIdItemProjetoEventoIcode() {
        return idItemProjetoEventoIcode;
    }

    public void setIdItemProjetoEventoIcode(Long idItemProjetoEventoIcode) {
        this.idItemProjetoEventoIcode = idItemProjetoEventoIcode;
    }

    public Long getIdItemProjetoLocacaoIcode() {
        return idItemProjetoLocacaoIcode;
    }

    public void setIdItemProjetoLocacaoIcode(Long idItemProjetoLocacaoIcode) {
        this.idItemProjetoLocacaoIcode = idItemProjetoLocacaoIcode;
    }

    public Long getIdItemProjetoLancheIcode() {
        return idItemProjetoLancheIcode;
    }

    public void setIdItemProjetoLancheIcode(Long idItemProjetoLancheIcode) {
        this.idItemProjetoLancheIcode = idItemProjetoLancheIcode;
    }


    public String getConcurso() {
        return concurso;
    }

    public void setConcurso(String concurso) {
        this.concurso = concurso;
    }


}
