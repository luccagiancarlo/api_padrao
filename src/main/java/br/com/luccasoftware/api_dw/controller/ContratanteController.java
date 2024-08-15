package br.com.luccasoftware.api_dw.controller;

import br.com.luccasoftware.api_dw.jpa.Contratante;
import br.com.luccasoftware.api_dw.jpa.ContratanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api_dw/v1")
public class ContratanteController {

    @Autowired
    private ContratanteRepository contratanteRepository;

    @GetMapping("/retornarContratantes")
    public List<Contratante> retornarContratantes(
            @RequestHeader(value = "Authorization") String authorizationHeader) {

        // Valida se o Authorization header está presente e começa com "Bearer "
        if (!authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token inválido.");
        }

        // O filtro JWT deve garantir que a solicitação está autenticada
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Usuário não autenticado.");
        }

        // Retorna a lista de contratantes
        return contratanteRepository.findAllContratantes();
    }
}

