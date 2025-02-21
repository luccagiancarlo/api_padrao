package br.com.luccasoftware.api_dw.dto;

public class QdeTitulos {
    private long id;
    private String prefixo;
    private long qde;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getQde() {
        return qde;
    }

    public void setQde(long qde) {
        this.qde = qde;
    }

    public String getPrefixo() {
        return prefixo;
    }

    public void setPrefixo(String prefixo) {
        this.prefixo = prefixo;
    }
}
