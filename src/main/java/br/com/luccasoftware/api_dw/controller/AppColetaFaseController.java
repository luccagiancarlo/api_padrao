package br.com.luccasoftware.api_dw.controller;

import br.com.luccasoftware.api_dw.jpa.AppColetaFaseRepository;
import br.com.luccasoftware.api_dw.jpa.AppColetaFase;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api_dw/v1")
public class AppColetaFaseController {

    @Autowired
    private AppColetaFaseRepository appColetaFaseRepository;

    // Método para buscar todos ou por ID específico
    @GetMapping("/coleta-fase/{id}")
    public List<AppColetaFase> getColetaFase(
            @RequestHeader(value = "Authorization") String authorizationHeader,
            @PathVariable String id) {

        // Validação básica do token JWT no cabeçalho
        if (!authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token inválido.");
        }

        // Verifica se a solicitação está autenticada
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Usuário não autenticado.");
        }

        // Lógica para retornar todas as fases ou uma fase específica por ID
        List<AppColetaFase> fases = new ArrayList<>();
        if (id != null) {
            if (id.equals("all")) {
                try {
                    fases = appColetaFaseRepository.findAll();
                } catch (Exception e) {
                    AppColetaFase appColetaFase = new AppColetaFase();
                    appColetaFase.setId(0);
                    appColetaFase.setNome(e.getMessage());
                    fases.add(appColetaFase);

                }

            } else {
                try {
                    Optional<AppColetaFase> fase = appColetaFaseRepository.findById(Integer.parseInt(id));
                    fase.ifPresent(fases::add);
                } catch (Exception e) {
                    try {
                        fases = appColetaFaseRepository.findAll();
                    } catch (Exception ex) {
                        AppColetaFase appColetaFase = new AppColetaFase();
                        appColetaFase.setId(0);
                        appColetaFase.setNome(ex.getMessage());
                        fases.add(appColetaFase);

                    }
                }
            }
        }
        return fases;
    }

    // Método para buscar por nome parcial (ignora case)
    @GetMapping("/coleta-fase/buscar")
    public List<AppColetaFase> getByNome(
            @RequestHeader(value = "Authorization") String authorizationHeader,
            @RequestParam String nome) {

        // Validação básica do token JWT no cabeçalho
        if (!authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token inválido.");
        }

        // Verifica se a solicitação está autenticada
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Usuário não autenticado.");
        }

        // Busca as fases por nome parcial, ignorando case sensitivity
        return appColetaFaseRepository.findByNomeContainingIgnoreCase(nome);
    }

    // Método para criar uma nova fase
    @PostMapping("/coleta-fase")
    public AppColetaFase createColetaFase(
            @RequestHeader(value = "Authorization") String authorizationHeader,
            @RequestBody AppColetaFase novaFase) {

        // Validação do token JWT no cabeçalho
        if (!authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token inválido.");
        }

        // Verifica se a solicitação está autenticada
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Usuário não autenticado.");
        }

        // Salva a nova fase no banco de dados
        return appColetaFaseRepository.save(novaFase);
    }

    // Método para atualizar uma fase existente
    @PutMapping("/coleta-fase/{id}")
    public AppColetaFase updateColetaFase(
            @RequestHeader(value = "Authorization") String authorizationHeader,
            @PathVariable Integer id,
            @RequestBody AppColetaFase faseAtualizada) {

        // Validação do token JWT no cabeçalho
        if (!authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token inválido.");
        }

        // Verifica se a solicitação está autenticada
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Usuário não autenticado.");
        }

        // Atualiza a fase se existir
        return appColetaFaseRepository.findById(id)
                .map(fase -> {
                    fase.setNome(faseAtualizada.getNome());
                    return appColetaFaseRepository.save(fase);
                })
                .orElseThrow(() -> new RuntimeException("Fase não encontrada"));
    }

    // Método para excluir uma fase por ID
    @DeleteMapping("/coleta-fase/{id}")
    public String deleteColetaFase(
            @RequestHeader(value = "Authorization") String authorizationHeader,
            @PathVariable Integer id) {

        // Validação do token JWT no cabeçalho
        if (!authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token inválido.");
        }

        // Verifica se a solicitação está autenticada
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Usuário não autenticado.");
        }

        // Exclui a fase se existir
        if (appColetaFaseRepository.existsById(id)) {
            appColetaFaseRepository.deleteById(id);
            return "Fase excluída com sucesso!";
        } else {
            throw new RuntimeException("Fase não encontrada");
        }
    }

    @GetMapping("/fases/all")
    public String fases_all(
            @RequestHeader(value = "Authorization") String authorizationHeader) throws IOException {

        // Validação do token JWT no cabeçalho
        if (!authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token inválido.");
        }

        // Verifica se a solicitação está autenticada
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Usuário não autenticado.");
        }

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, "{\n    \"tp_operacao\" : \"pesquisarAll\"\n}");
        Request request = new Request.Builder()
                .url("https://luccasoftware.com.br/api/iaocp/fases")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjZF9wcm9qZXRvIjoicHJvamV0bzEyMyJ9.bvMTrQYM7z8PzL8S0mZsnIzP5eUMo0VDqg7yd5u2pcc")
                .build();
        Response response = client.newCall(request).execute();


        // Retorna uma mensagem de sucesso se autenticado
        return response.body().string();
    }



}
