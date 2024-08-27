package br.com.luccasoftware.api_dw.dto;

public class EventoEnsalamento {


    private String concurso;
    private long id_evento;
    private long id_local;
    private long id_cargo;
    private long tt_ensalado;


    public EventoEnsalamento() {
    }


    public String getConcurso() {
        return concurso;
    }

    public void setConcurso(String concurso) {
        this.concurso = concurso;
    }

    public long getId_evento() {
        return id_evento;
    }

    public void setId_evento(long id_evento) {
        this.id_evento = id_evento;
    }

    public long getId_local() {
        return id_local;
    }

    public void setId_local(long id_local) {
        this.id_local = id_local;
    }

    public long getId_cargo() {
        return id_cargo;
    }

    public void setId_cargo(long id_cargo) {
        this.id_cargo = id_cargo;
    }

    public long getTt_ensalado() {
        return tt_ensalado;
    }

    public void setTt_ensalado(long tt_ensalado) {
        this.tt_ensalado = tt_ensalado;
    }
}
