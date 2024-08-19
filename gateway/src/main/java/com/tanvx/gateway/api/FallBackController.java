package com.tanvx.gateway.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackController {

  @GetMapping("/fallback")
  public ResponseEntity<String> fallback(@RequestHeader("Host") String host) {
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body("Service " + host + " is currently unavailable. Please try again later.");
  }
}
