//package br.com.luccasoftware.api_dw.controller;
//
//
//import br.com.luccasoftware.api_dw.dto.Evento;
//import br.com.luccasoftware.api_dw.dto.LocalEvento;
//import br.com.luccasoftware.api_dw.jpa.EventoRepository;
//import br.com.luccasoftware.api_dw.jpa.LocalEventoRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api_dw/v1")
//public class LocalEventoController {
//
//
//    @Autowired
//    private LocalEventoRepository localEventoRepository;
//
//    @GetMapping("/retornarLocalEvento/{id_evento}")
//    public List<LocalEvento> retornarLocalEvento(
//            @RequestHeader(value = "Authorization") String authorizationHeader,
//            @PathVariable String id_evento) {
//
//        // Valida se o Authorization header está presente e começa com "Bearer "
//        if (!authorizationHeader.startsWith("Bearer ")) {
//            throw new RuntimeException("Token inválido.");
//        }
//
//        // O filtro JWT deve garantir que a solicitação está autenticada
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || !authentication.isAuthenticated()) {
//            throw new RuntimeException("Usuário não autenticado.");
//        }
//
//        // Retorna a lista de cargos com o prefixo especificado
//        List<LocalEvento> locaisEventos = new ArrayList<>();
//        if (id_evento != null) {
//            if (id_evento.equals("all")) {
//                locaisEventos = localEventoRepository.findAll();
//            } else {
//                try {
//                    locaisEventos = localEventoRepository.findAll(Long.parseLong(id_evento));
//                } catch (Exception e) {
//                    locaisEventos = localEventoRepository.findAll();
//                }
//            }
//        }
//        return locaisEventos;
//    }
//
//
//
//}

