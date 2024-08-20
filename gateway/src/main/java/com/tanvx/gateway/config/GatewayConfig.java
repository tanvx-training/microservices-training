package com.tanvx.gateway.config;

import java.time.ZonedDateTime;
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
            .filters(f -> f.addResponseHeader("X-Response-Time", ZonedDateTime.now().toString()))
            .uri("lb://CITY-SERVICE"))
        .route("measurement", r -> r.path("/api/v1/measurements/**")
            .filters(f -> f.addResponseHeader("X-Response-Time", ZonedDateTime.now().toString()))
            .uri("lb://f"))
        .route("notification", r -> r.path("/api/v1/notifications/**")
            .filters(f -> f.addResponseHeader("X-Response-Time", ZonedDateTime.now().toString()))
            .uri("lb://NOTIFICATION-SERVICE"))
        .route("user", r -> r.path("/api/v1/users/**")
            .filters(f -> f.addResponseHeader("X-Response-Time", ZonedDateTime.now().toString()))
            .uri("lb://USER-SERVICE"))
        .build();
  }
}
