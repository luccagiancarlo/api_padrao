package br.com.luccasoftware.api_dw.jpa;

import jakarta.persistence.*;

@Entity
@Table(name = "app_coleta_fase")
public class AppColetaFase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", length = 200, nullable = false)
    private String nome;

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

