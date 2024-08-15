package br.com.luccasoftware.api_dw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = "br.com.luccasoftware.api_dw.jpa") // Altere conforme necessário
public class ApiDwApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiDwApplication.class, args);
    }
}
