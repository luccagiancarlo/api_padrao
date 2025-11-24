package br.uem.vestibular.api_padrao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "br.uem.vestibular.api_padrao.jpa") // Altere conforme necessário
public class ApiDwApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiDwApplication.class, args);
    }
}
