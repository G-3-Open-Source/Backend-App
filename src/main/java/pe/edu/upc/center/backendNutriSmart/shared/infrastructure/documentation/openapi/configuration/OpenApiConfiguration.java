package pe.edu.upc.center.backendNutriSmart.shared.infrastructure.documentation.openapi.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // anotación que indica que esta clase es una clase de configuración de Spring
// clase que configura la documentación de la API utilizando OpenAPI
public class OpenApiConfiguration {
    @Bean // anotación que indica que este método devuelve un bean de Spring
    // un bean es un objeto que es instanciado, ensamblado y administrado por el contenedor de Spring

    // método que devuelve una instancia de OpenAPI configurada para la aplicación Learning Platform
    public OpenAPI learningPlatformOpenApi() {
        // General configuration
        var openApi = new OpenAPI();
        openApi
                .info(new Info()
                        .title("Learning Platform API")
                        .description("Learning Platform application REST API documentation.")
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0")
                                .url("https://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Learning Platform Documentation")
                        .url("https://github.com/upc-is-si729/daos-language-reference"));
        return openApi;
    }
}