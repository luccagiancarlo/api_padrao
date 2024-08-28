package br.com.luccasoftware.api_dw.dto;

public class ResultadoCargo {
    private String concurso;
    private long id_cargo;
    private long tt_candidatos;
    private long tt_presentes;
    private long tt_ausentes;
    private long tt_aprovados;
    private long tt_eliminados;

    public ResultadoCargo() {

    }


    public String getConcurso() {
        return concurso;
    }

    public void setConcurso(String concurso) {
        this.concurso = concurso;
    }

    public long getId_cargo() {
        return id_cargo;
    }

    public void setId_cargo(long id_cargo) {
        this.id_cargo = id_cargo;
    }

    public long getTt_candidatos() {
        return tt_candidatos;
    }

    public void setTt_candidatos(long tt_candidatos) {
        this.tt_candidatos = tt_candidatos;
    }

    public long getTt_presentes() {
        return tt_presentes;
    }

    public void setTt_presentes(long tt_presentes) {
        this.tt_presentes = tt_presentes;
    }

    public long getTt_ausentes() {
        return tt_ausentes;
    }

    public void setTt_ausentes(long tt_ausentes) {
        this.tt_ausentes = tt_ausentes;
    }

    public long getTt_aprovados() {
        return tt_aprovados;
    }

    public void setTt_aprovados(long tt_aprovados) {
        this.tt_aprovados = tt_aprovados;
    }

    public long getTt_eliminados() {
        return tt_eliminados;
    }

    public void setTt_eliminados(long tt_eliminados) {
        this.tt_eliminados = tt_eliminados;
    }
}
