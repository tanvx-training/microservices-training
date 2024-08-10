package com.tanvx.cities.domain.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "city-service", url = "${clients.city.url}")
public interface CityFeignClient {

  @GetMapping(value = "/api/v1/cities/{cityId}", consumes = "application/json")
  ResponseEntity<CityResponse> findCityById(@PathVariable Long cityId);
}
