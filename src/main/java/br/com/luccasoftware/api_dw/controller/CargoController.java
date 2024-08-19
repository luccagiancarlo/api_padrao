package br.com.luccasoftware.api_dw.controller;

import br.com.luccasoftware.api_dw.jpa.Cargo;
import br.com.luccasoftware.api_dw.jpa.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api_dw/v1")
public class CargoController {

    @Autowired
    private CargoRepository cargoRepository;

    @GetMapping("/retornarCargos/{prefixo}")
    public List<Cargo> retornarCargos(
            @RequestHeader(value = "Authorization") String authorizationHeader,
            @PathVariable String prefixo,
            @RequestParam(defaultValue = "2023") int inicio) {

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
        List<Cargo> cargos = new ArrayList<>();
        if (prefixo != null) {
            if (prefixo.equals("all")) {
                cargos = cargoRepository.findAllCargos(inicio);
            } else {
                cargos = cargoRepository.findAllCargos(prefixo);
            }
        }
        return cargos;
    }



}

