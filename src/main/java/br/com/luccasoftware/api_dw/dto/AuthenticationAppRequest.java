package br.com.luccasoftware.api_dw.dto;

public class AuthenticationAppRequest {

    private String en_email;
    private String de_senha;

    public String getEn_email() {
        return en_email;
    }

    public void setEn_email(String en_email) {
        this.en_email = en_email;
    }

    public String getDe_senha() {
        return de_senha;
    }

    public void setDe_senha(String de_senha) {
        this.de_senha = de_senha;
    }

    // Getters and setters
}

