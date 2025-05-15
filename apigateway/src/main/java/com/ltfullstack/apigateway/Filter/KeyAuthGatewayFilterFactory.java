package com.ltfullstack.apigateway.Filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import com.ltfullstack.apigateway.ultils.HandleException;
@Component
public class KeyAuthGatewayFilterFactory extends AbstractGatewayFilterFactory<KeyAuthGatewayFilterFactory.Config> {

    public KeyAuthGatewayFilterFactory(){
        super(Config.class);
    }
    @Value("${apiKey}")
    private String apiKey;

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (!exchange.getRequest().getHeaders().containsKey("apiKey")){
                return HandleException.handle(exchange, "Missing authorization information", HttpStatus.UNAUTHORIZED);
            }
            String key = exchange.getRequest().getHeaders().get("apiKey").get(0);
            if(!apiKey.equals(key)){
                return HandleException.handle(exchange, "Invalid Api Key", HttpStatus.FORBIDDEN);
            }
            ServerHttpRequest request = exchange.getRequest();
            return chain.filter(exchange.mutate()
                    .request(request)
                    .build());
        };
    }

    static class Config{

    }
}
