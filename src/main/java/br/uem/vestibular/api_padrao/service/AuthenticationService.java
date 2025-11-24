package br.uem.vestibular.api_padrao.service;

import br.uem.vestibular.api_padrao.dto.RetornoLogin;
import br.uem.vestibular.api_padrao.jpa.UsuarioAdminRepository;
import br.uem.vestibular.api_padrao.jpa.UsuarioRepository;
import br.uem.vestibular.api_padrao.utils.JwtUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class AuthenticationService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioAdminRepository usuarioAdminRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public String authenticate(String email, String senha) {
        // Autenticação com senha em texto plano (sem BCrypt)

        if (email.equals("admcvu@uem.br")) {
            if (senha.equals("177900")) {
                return jwtUtil.generateToken(email);
            } else {
                return "Credenciais inválidas ou usuário inativo.";
            }

        } else {
            // Busca a senha em texto plano do banco
            String storedPassword = usuarioRepository.findPasswordByEmail(email);

            // Comparação direta (sem hash)
            if (storedPassword != null && storedPassword.equals(senha)) {
                return jwtUtil.generateToken(email);
            } else {
                return "Credenciais inválidas ou usuário inativo.";
            }
        }
    }

    public String authenticateLucca(String email, String senha) {
        String apiUrl = "https://luccasoftware.com.br/api/iaocp_auth";

        try {
            // Criar a URL da requisição
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Configurar a requisição como POST
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true); // Permite envio de dados no body

            // Criar o JSON com email e senha
            JSONObject requestBody = new JSONObject();
            requestBody.put("en_email", email);
            requestBody.put("lt_password", senha);

            // Enviar o JSON no corpo da requisição
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = requestBody.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Ler a resposta do servidor
            int responseCode = conn.getResponseCode();
            InputStream is = (responseCode == 200) ? conn.getInputStream() : conn.getErrorStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            br.close();

            // Processar a resposta JSON
            JSONObject jsonResponse = new JSONObject(response.toString());

            if (jsonResponse.has("token")) {
                return jsonResponse.getString("cd_setor"); // Retorna o token em caso de sucesso
            } else if (jsonResponse.has("error")) {
                return "Erro: " + jsonResponse.getString("error"); // Retorna a mensagem de erro
            }
        } catch (Exception e) {
            return "Erro ao autenticar: " + e.getMessage();
        }

        return "Erro desconhecido";
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
        r.setCd_evento("0");

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
                r.setCd_evento("0");

                return r;
            } else {
                return r;
            }

        } else {

            String cd_evento = authenticateLucca(email, senha);
            if (!cd_evento.contains("Erro")) {

                String token = jwtUtil.generateToken(email);
                //r.setLt_login(tk);
                r.setLt_login("Logistica");
                r.setEn_email(email);
                r.setNm_pessoa("Logistica");
                r.setDe_mensagem("OK");
                r.setLt_token(token);
                r.setFl_facial("S");
                r.setFl_coletar("S");
                r.setFl_sede("S");
                r.setFl_transmitir("S");
                r.setCd_evento(cd_evento);

                return r;

            } else {
                return r;
            }

            /*
            // ALTERNATIVA: Autenticação usando banco local (sem API externa)
            String storedPassword = usuarioRepository.findPasswordByEmail(email);
            if (storedPassword != null && storedPassword.equals(senha)) {
                UsuarioAdmin usu = usuarioAdminRepository.buscarEmail(email);
                String token = jwtUtil.generateToken(email);
                r.setLt_login(usu.getNome());
                r.setEn_email(email);
                r.setNm_pessoa(usu.getNome());
                r.setDe_mensagem("OK");
                r.setLt_token(token);
                r.setFl_facial("S");
                r.setFl_coletar("S");
                r.setFl_sede("S");
                r.setFl_transmitir("S");
                r.setCd_evento(usu.getCdSetor() != null ? usu.getCdSetor() : "0");

                return r;
            } else {
                return r;
            }
            */


        }

    }
}