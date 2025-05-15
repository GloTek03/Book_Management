package com.ltfullstack.apigateway.ultils;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
@Component
public class HandleException {
    public static Mono<Void> handle(ServerWebExchange exchange, String message, HttpStatus status){
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        String errorResponse = String.format(
                "{\"timestamp\": \"%s\",\"status\": %d, \"error\":\"%s\", \"message\": \"%s\", \"path\": \"%s\"}",
                java.time.ZonedDateTime.now().toString(),
                status.value(),
                status.getReasonPhrase(),
                message,
                exchange.getRequest().getURI().getPath()
        );
        return response.writeWith(Mono.just(response.bufferFactory().wrap(errorResponse.getBytes())));
    }
}
