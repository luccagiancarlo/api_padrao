package br.com.luccasoftware.api_dw.service;

import br.com.luccasoftware.api_dw.jpa.UsuarioRepository;
import br.com.luccasoftware.api_dw.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String authenticate(String email, String senha) {
        // Aqui você pode carregar o hash da senha do banco de dados
        String storedHash = usuarioRepository.findPasswordByEmail(email); // Supondo que esse método existe

        if (storedHash != null && passwordEncoder.matches(senha, storedHash)) {
            return jwtUtil.generateToken(email); // Gere o token JWT se as credenciais forem válidas
        } else {
            return "Credenciais inválidas ou usuário inativo.";
        }
    }
}