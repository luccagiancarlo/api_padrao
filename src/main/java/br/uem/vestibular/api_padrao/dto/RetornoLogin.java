package br.uem.vestibular.api_padrao.dto;

public class RetornoLogin {

    private String lt_login;
    private String en_email;
    private String nm_pessoa;
    private String de_mensagem;
    private String lt_token;
    private String fl_facial;
    private String fl_sede;
    private String fl_coletar;
    private String fl_transmitir;
    private String cd_evento;


    public String getLt_login() {
        return lt_login;
    }

    public void setLt_login(String lt_login) {
        this.lt_login = lt_login;
    }

    public String getEn_email() {
        return en_email;
    }

    public void setEn_email(String en_email) {
        this.en_email = en_email;
    }

    public String getNm_pessoa() {
        return nm_pessoa;
    }

    public void setNm_pessoa(String nm_pessoa) {
        this.nm_pessoa = nm_pessoa;
    }

    public String getDe_mensagem() {
        return de_mensagem;
    }

    public void setDe_mensagem(String de_mensagem) {
        this.de_mensagem = de_mensagem;
    }

    public String getLt_token() {
        return lt_token;
    }

    public void setLt_token(String lt_token) {
        this.lt_token = lt_token;
    }

    public String getFl_facial() {
        return fl_facial;
    }

    public void setFl_facial(String fl_facial) {
        this.fl_facial = fl_facial;
    }

    public String getFl_sede() {
        return fl_sede;
    }

    public void setFl_sede(String fl_sede) {
        this.fl_sede = fl_sede;
    }

    public String getFl_coletar() {
        return fl_coletar;
    }

    public void setFl_coletar(String fl_coletar) {
        this.fl_coletar = fl_coletar;
    }

    public String getFl_transmitir() {
        return fl_transmitir;
    }

    public void setFl_transmitir(String fl_transmitir) {
        this.fl_transmitir = fl_transmitir;
    }

    public String getCd_evento() {
        return cd_evento;
    }

    public void setCd_evento(String cd_evento) {
        this.cd_evento = cd_evento;
    }
}
