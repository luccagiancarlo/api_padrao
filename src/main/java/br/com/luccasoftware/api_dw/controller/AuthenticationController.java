package br.com.luccasoftware.api_dw.controller;

import br.com.luccasoftware.api_dw.dto.AuthenticationRequest;
import br.com.luccasoftware.api_dw.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api_dw/v1")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/autenticar")
    public String createToken(@RequestBody AuthenticationRequest authenticationRequest) {
        return authenticationService.authenticate(authenticationRequest.getEmail(), authenticationRequest.getSenha());
    }
}


