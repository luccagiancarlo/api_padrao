package br.com.luccasoftware.api_dw.controller;


import br.com.luccasoftware.api_dw.dto.EventoEnsalamentoCandidato;
import br.com.luccasoftware.api_dw.dto.EventoEnsalamentoLocal;
import br.com.luccasoftware.api_dw.jpa.EventoEnsalamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api_dw/v1")
public class EventoEnsalamentoLocalController {


    @Autowired
    private EventoEnsalamentoRepository eventoEnsalamentoRepository;

    @GetMapping("/retornarEventoEnsalamentoLocal/{id_evento}")
    public List<EventoEnsalamentoLocal> retornarEventoEnsalamentoLocal(
            @RequestHeader(value = "Authorization") String authorizationHeader,
            @PathVariable long id_evento) {

        // Valida se o Authorization header está presente e começa com "Bearer "
        if (!authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token inválido.");
        }

        // O filtro JWT deve garantir que a solicitação está autenticada
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Usuário não autenticado.");
        }

        // Retorna a lista de cargos com o prefixo especificado
        List<EventoEnsalamentoLocal> locais = new ArrayList<>();
        if (id_evento > 0) {

                locais = eventoEnsalamentoRepository.findAll(id_evento);

        }
        return locais;
    }



}

