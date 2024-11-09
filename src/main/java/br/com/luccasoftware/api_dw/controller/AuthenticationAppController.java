package br.com.luccasoftware.api_dw.controller;

import br.com.luccasoftware.api_dw.dto.AuthenticationAppRequest;
import br.com.luccasoftware.api_dw.dto.RetornoLogin;
import br.com.luccasoftware.api_dw.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api_dw/v1")
public class AuthenticationAppController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/autenticar_app")
    public RetornoLogin createToken(@RequestBody AuthenticationAppRequest authenticationRequest) {
        return authenticationService.authenticateApp(authenticationRequest.getEn_email(), authenticationRequest.getDe_senha());
    }

    @PostMapping("/autenticar_app_dw")
    public RetornoLogin createTokenDw(@RequestBody AuthenticationAppRequest authenticationRequest) {
        return authenticationService.authenticateApp(authenticationRequest.getEn_email(), authenticationRequest.getDe_senha());
    }

}


