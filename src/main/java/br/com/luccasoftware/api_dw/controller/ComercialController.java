package br.com.luccasoftware.api_dw.controller;

import br.com.luccasoftware.api_dw.dto.Cargo;
import br.com.luccasoftware.api_dw.dto.Comercial;
import br.com.luccasoftware.api_dw.jpa.CargoRepository;
import br.com.luccasoftware.api_dw.jpa.ComercialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api_dw/v1")
public class ComercialController {

    @Autowired
    private ComercialRepository comercialRepository;

    @GetMapping("/retornarComercial/{prefixo}")
    public List<Comercial> retornaComercial(
            @RequestHeader(value = "Authorization") String authorizationHeader,
            @PathVariable String prefixo,
            @RequestParam(defaultValue = "0") int inicio,
            @RequestParam(defaultValue = "0") int fim) {

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
        List<Comercial> listaComercial = new ArrayList<>();
        if (prefixo != null) {
            if (prefixo.equals("all")) {
                listaComercial = comercialRepository.buscarDados(inicio, fim);
            } else {
                listaComercial = comercialRepository.buscarDados(prefixo);
            }
        }
        return listaComercial;
    }



}

