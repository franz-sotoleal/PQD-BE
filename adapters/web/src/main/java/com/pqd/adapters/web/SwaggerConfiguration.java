package com.pqd.adapters.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
@Component
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Collections.singletonList(apiKey()))
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/api/**"))
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference("JWT", authorizationScopes));
    }

    private ApiInfo apiInfo = new ApiInfo(
            "PQD Web Adapter API",
            "This is Product Quality Dashboard web adapter documentation. \n \n" +
            "Requests require jwt authorization. " +
            "You can get the token from authentication-controller by executing login request with 'user' as username and 'password' as password. " +
            "Alternatively you can register a new user and log in with the new user.\n\n" +
            "Once you have the token, you must autorize yourself by clicking 'Authorize' button and inserting " +
            "'Bearer <your token>' as the value. Note that you must include the word 'Bearer' before the token.\n" +
            "Make a dummy request from dummy-controller to verify success of the authorization - it should give you status 200 with a dummy message.",
            "1.0",
            "",
            new Contact(
                    "Kert Prink",
                    "https://ee.linkedin.com/in/kert-prink-887543177",
                    ""),
            "",
            "",
            new ArrayList<>());
}
