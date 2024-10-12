package br.com.luccasoftware.api_dw.dto;



public class UsuarioAdmin {


    private String login;
    private String email;
    private String senha;
    private String nome;
    private boolean block; // Certifique-se de que os nomes dos campos e tipos correspondem ao banco de dados

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // Getters e Setters
}
