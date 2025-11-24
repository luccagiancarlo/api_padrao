package br.uem.vestibular.api_padrao.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class GerarSenha {
    public static void main(String[] args) {
        // Criar uma instância do BCryptPasswordEncoder
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // Senha que você quer hashear
        String password = "Password";

        // Gerar o hash
        String hashedPassword = passwordEncoder.encode(password);

        // Imprimir o hash
        System.out.println("Hash gerado: " + hashedPassword);
    }
}
