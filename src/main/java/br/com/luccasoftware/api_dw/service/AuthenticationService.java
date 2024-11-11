package br.com.luccasoftware.api_dw.service;

import br.com.luccasoftware.api_dw.dto.RetornoLogin;
import br.com.luccasoftware.api_dw.dto.UsuarioAdmin;
import br.com.luccasoftware.api_dw.jpa.UsuarioAdminRepository;
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
    private UsuarioAdminRepository usuarioAdminRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String authenticate(String email, String senha) {
        // Aqui você pode carregar o hash da senha do banco de dados

        if (email.equals("logistica@institutoaocp.org.br")) {
            if (senha.equals("177900")) {
                return jwtUtil.generateToken(email);
            } else {
                return "Credenciais inválidas ou usuário inativo.";
            }

        } else {

            String storedHash = usuarioRepository.findPasswordByEmail(email); // Supondo que esse método existe

            if (storedHash != null && passwordEncoder.matches(senha, storedHash)) {
                return jwtUtil.generateToken(email); // Gere o token JWT se as credenciais forem válidas
            } else {
                return "Credenciais inválidas ou usuário inativo.";
            }
        }
    }

    public RetornoLogin authenticateApp(String email, String senha) {

        RetornoLogin r = new RetornoLogin();
        r.setLt_login("");
        r.setEn_email("");
        r.setNm_pessoa("");
        r.setDe_mensagem("Credenciais inválidas ou usuário inativo.");
        r.setLt_token("");
        r.setFl_facial("N");
        r.setFl_coletar("N");
        r.setFl_sede("N");
        r.setFl_transmitir("N");

        if (email.equals("admlog@institutoaocp.org.br")) {
            if (senha.equals("177900")) {
                String token = jwtUtil.generateToken(email);
                r.setLt_login("Logistica");
                r.setEn_email("logistica@institutoaocp.org.br");
                r.setNm_pessoa("Logistica");
                r.setDe_mensagem("OK");
                r.setLt_token(token);
                r.setFl_facial("S");
                r.setFl_coletar("S");
                r.setFl_sede("S");
                r.setFl_transmitir("S");

                return r;
            } else {
                return r;
            }

        } else {
            String storedHash = usuarioRepository.findPasswordByEmail(email);
            if (storedHash != null && passwordEncoder.matches(senha, storedHash)) {
                UsuarioAdmin usu = new UsuarioAdmin();
                usu = usuarioAdminRepository.buscarEmail(email);
                String token = jwtUtil.generateToken(email);
                r.setLt_login(usu.getLogin());
                r.setEn_email(email);
                r.setNm_pessoa(usu.getNome());
                r.setDe_mensagem("OK");
                r.setLt_token(token);
                r.setFl_facial("S");
                r.setFl_coletar("S");
                r.setFl_sede("S");
                r.setFl_transmitir("S");

                return r;

            } else {

                return r;
            }
        }
    }

}