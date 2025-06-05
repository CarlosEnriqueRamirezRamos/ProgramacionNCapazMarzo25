package com.gidis01.CRamirezProgramacionNCapasMarzo25;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@OpenAPIDefinition(
    info = @Info(
        title = "Mi API de Usuarios",
        version = "1.0",
        description = "Esta API permite gestionar usuarios y direcciones dentro de un sistema web"
                + "Desarroyada Por Carlos Enrique Ramirez Ramos"
    )
)

@SpringBootApplication
public class CRamirezProgramacionNCapasMarzo25Application {

	public static void main(String[] args) {
		SpringApplication.run(CRamirezProgramacionNCapasMarzo25Application.class, args);
	}
}
