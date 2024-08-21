package com.tanvx.gateway.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class FallbackController {

  @GetMapping("/fallback")
  public ResponseEntity<String> fallback(@RequestHeader HttpHeaders headers) {
    log.warn("Request forwarded to fallback. Headers: {}", headers);

    // Optionally log any specific header that might indicate the issue
    if (headers.containsKey("X-CircuitBreaker-Name")) {
      log.warn("Circuit breaker name: {}", headers.getFirst("X-CircuitBreaker-Name"));
    }
    return ResponseEntity.ok("This is a fallback response. The service is currently unavailable.");
  }
}
