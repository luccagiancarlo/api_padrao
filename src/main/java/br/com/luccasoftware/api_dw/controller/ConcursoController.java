package br.com.luccasoftware.api_dw.controller;

import br.com.luccasoftware.api_dw.jpa.Concurso;
import br.com.luccasoftware.api_dw.jpa.ConcursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api_dw/v1")
public class ConcursoController {

    @Autowired
    private ConcursoRepository concursoRepository;

    @GetMapping("/retornarConcursos")
    public List<Concurso> retornarConcursos(
            @RequestHeader(value = "Authorization") String authorizationHeader,
            @PathVariable int inicio) {

        // Valida se o Authorization header está presente e começa com "Bearer "
        if (!authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token inválido.");
        }

        // O filtro JWT deve garantir que a solicitação está autenticada
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Usuário não autenticado.");
        }

        // Retorna a lista de concursos
        return concursoRepository.findAllConcursos(inicio);
    }
}
