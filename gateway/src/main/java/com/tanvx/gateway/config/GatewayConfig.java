package com.tanvx.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

  @Bean
  public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
    return builder.routes()
        .route("city", r -> r.path("/api/v1/cities/**")
            .uri("http://localhost:8085"))
        .route("measurement", r -> r.path("/api/v1/measurements/**")
            .uri("http://localhost:8086"))
        .route("notification", r -> r.path("/api/v1/notifications/**")
            .uri("http://localhost:8087"))
        .route("user", r -> r.path("/api/v1/users/**")
            .uri("http://localhost:8088"))
        .build();
  }
}
