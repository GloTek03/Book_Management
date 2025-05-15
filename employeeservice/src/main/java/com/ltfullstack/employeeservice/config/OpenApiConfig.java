package com.ltfullstack.employeeservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Employee Api Specification - LT FullStack",
                description = "Api document for Employee Service",
                version = "1.0",
                contact = @Contact(
                        name = "SonNH",
                        email = "sonnguyen0610031@gmail.com"
                )
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:9002"
                ),
                @Server(
                        description = "SIT ENV",
                        url = "https://employee-service.sit.com"
                ),
                @Server(
                        description = "PRO ENV",
                        url = "https://employee-service.pro.com"
                )
        }
)
public class OpenApiConfig {
}
