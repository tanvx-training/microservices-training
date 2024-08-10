package com.tanvx.cities.api;

import com.tanvx.cities.domain.city.dto.request.CityCreateRequest;
import com.tanvx.cities.domain.city.dto.request.CityRequest;
import com.tanvx.cities.domain.city.dto.response.CityCreateResponse;
import com.tanvx.cities.domain.city.dto.response.CityResponse;
import com.tanvx.cities.domain.city.service.CityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cities")
public class CityController {

  private final CityService cityService;

  @GetMapping
  public ResponseEntity<Page<CityResponse>> findAll(
      @RequestParam(value = "page", defaultValue = "1") Integer page,
      @RequestParam(value = "size", defaultValue = "50") Integer size,
      @RequestParam(value = "sort", required = false) String sort) {

    log.info("CityController - findAll: page={}, size={}, sort={}", page, size, sort);

    return ResponseEntity.ok(cityService.findCity(new CityRequest(page, size, sort)));
  }

  @PostMapping
  public ResponseEntity<CityCreateResponse> create(@RequestBody CityCreateRequest request) {
    log.info("CityController - create: {}", request);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(cityService.createCity(request));
  }

  @GetMapping("/{cityId}")
  public ResponseEntity<CityResponse> findCityById(@PathVariable Long cityId) {
    log.info("CityController - findCityById={}", cityId);

    return ResponseEntity.status(HttpStatus.OK)
        .body(cityService.findCityById(cityId));
  }
}
