package com.my.bookingsystem.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

@Configuration
public class OpenApiConfig {

    // 1) Define your JWT-Bearer scheme so Swagger UI shows the Authorize button
    @Bean
    public OpenAPI bookingSystemOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("BearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .name("Authorization")
                        )
                )
                .info(new Info()
                        .title("Booking System API")
                        .version("1.0")
                        .description("…")
                );
    }

    // 2) This customizer adds 401/403 to the Responses table of every operation
    @Bean
    public OperationCustomizer globalResponseCustomizer() {
        return (operation, handlerMethod) -> {
            ApiResponses rs = operation.getResponses();
            if (!rs.containsKey("401")) {
                rs.addApiResponse("401",
                        new ApiResponse().description("Unauthorized – invalid or missing token"));
            }
            if (!rs.containsKey("403")) {
                rs.addApiResponse("403",
                        new ApiResponse().description("Forbidden – insufficient permissions"));
            }
            return operation;
        };
    }

    // 3) Hook the customizer into the GroupedOpenApi that Swagger UI uses
    @Bean
    public GroupedOpenApi bookingApi(OperationCustomizer globalResponseCustomizer) {
        return GroupedOpenApi.builder()
                .group("booking-system")
                // match all your controller paths here:
                .pathsToMatch("/package/**","/purchase/**","/booking/**","/classinfo/**",
                        "/admin/**",
                        "/schedule/**", "/entry/**", "/user/**", "/auth/**")
                .addOperationCustomizer(globalResponseCustomizer)
                .build();
    }
}
