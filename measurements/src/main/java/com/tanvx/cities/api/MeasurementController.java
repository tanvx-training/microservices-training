package com.tanvx.cities.api;

import com.tanvx.cities.domain.measurement.dto.request.MeasurementCreateRequest;
import com.tanvx.cities.domain.measurement.dto.request.MeasurementRequest;
import com.tanvx.cities.domain.measurement.dto.response.MeasurementCityResponse;
import com.tanvx.cities.domain.measurement.dto.response.MeasurementCreateResponse;
import com.tanvx.cities.domain.measurement.dto.response.MeasurementResponse;
import com.tanvx.cities.domain.measurement.service.MeasurementService;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/api/v1/measurements")
public class MeasurementController {

  private final MeasurementService measurementService;

  // Create a Measurement
  @PostMapping()
  public ResponseEntity<MeasurementCreateResponse> createMeasurement(@RequestBody
  MeasurementCreateRequest request) {

    log.info("Invoking MeasurementController - createMeasurement: request={}", request);

    MeasurementCreateResponse measurementCreateResponse = measurementService.createMeasurement(
        request);
    return ResponseEntity.status(HttpStatus.CREATED).body(measurementCreateResponse);
  }

  // Get All Measurements
  @GetMapping("/{cityId}/city")
  public ResponseEntity<List<MeasurementResponse>> getAllMeasurements(
      @PathVariable("cityId") Long cityId,
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "50") Integer size) {

    log.info(
        "Invoking MeasurementController - getAllMeasurements: page={}, size={}", page, size);

    List<MeasurementResponse> responses = measurementService.findMeasurement(
        new MeasurementRequest(cityId, page, size));
    return ResponseEntity.status(HttpStatus.OK).body(responses);
  }

  // Get Measurements by City ID
  @GetMapping("/{cityId}/city/info")
  public ResponseEntity<MeasurementCityResponse> getMeasurementsByCityId(
      @PathVariable Long cityId) {

    log.info("Invoking MeasurementController - getMeasurementsByCityId: cityId={}", cityId);

    MeasurementCityResponse response = measurementService.findMeasurementByCityId(cityId);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
