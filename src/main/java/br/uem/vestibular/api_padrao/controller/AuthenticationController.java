package br.uem.vestibular.api_padrao.controller;

import br.uem.vestibular.api_padrao.dto.AuthenticationAppRequest;
import br.uem.vestibular.api_padrao.dto.AuthenticationRequest;
import br.uem.vestibular.api_padrao.dto.RetornoLogin;
import br.uem.vestibular.api_padrao.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api_dw/v1")
@CrossOrigin(origins = "*")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * Endpoint de autenticação web simples
     * Retorna apenas o token JWT
     */
    @PostMapping("/autenticar")
    public ResponseEntity<?> autenticar(@RequestBody AuthenticationRequest request) {
        try {
            String resultado = authenticationService.authenticate(request.getEmail(), request.getSenha());

            // Verifica se é um token ou mensagem de erro
            if (resultado.startsWith("eyJ") || resultado.length() > 100) {
                // É um token JWT
                Map<String, String> response = new HashMap<>();
                response.put("token", resultado);
                return ResponseEntity.ok(response);
            } else {
                // É uma mensagem de erro
                Map<String, String> response = new HashMap<>();
                response.put("mensagem", resultado);
                return ResponseEntity.status(401).body(response);
            }
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensagem", "Erro ao autenticar: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * Endpoint de autenticação para app
     * Retorna token + dados do usuário + permissões
     */
    @PostMapping("/autenticar_app")
    public ResponseEntity<RetornoLogin> autenticarApp(@RequestBody AuthenticationAppRequest request) {
        try {
            RetornoLogin resultado = authenticationService.authenticateApp(
                request.getEn_email(),
                request.getDe_senha()
            );

            if ("OK".equals(resultado.getDe_mensagem())) {
                return ResponseEntity.ok(resultado);
            } else {
                return ResponseEntity.status(401).body(resultado);
            }
        } catch (Exception e) {
            RetornoLogin erro = new RetornoLogin();
            erro.setDe_mensagem("Erro ao autenticar: " + e.getMessage());
            erro.setLt_token("");
            erro.setEn_email("");
            erro.setNm_pessoa("");
            erro.setLt_login("");
            erro.setFl_facial("N");
            erro.setFl_coletar("N");
            erro.setFl_sede("N");
            erro.setFl_transmitir("N");
            erro.setCd_evento("0");
            return ResponseEntity.status(500).body(erro);
        }
    }

    /**
     * Endpoint de autenticação DW
     * Similar ao autenticar web
     */
    @PostMapping("/autenticar_dw")
    public ResponseEntity<?> autenticarDw(@RequestBody AuthenticationRequest request) {
        // Usa a mesma lógica do autenticar web
        return autenticar(request);
    }

    /**
     * Endpoint de teste para verificar se a API está rodando
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "API Padrão UEM");
        return ResponseEntity.ok(response);
    }
}
