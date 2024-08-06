package com.tanvx.measurements.api;

import com.tanvx.measurements.domain.measurement.dto.request.MeasurementCreateRequest;
import com.tanvx.measurements.domain.measurement.dto.request.MeasurementRequest;
import com.tanvx.measurements.domain.measurement.dto.response.MeasurementCityResponse;
import com.tanvx.measurements.domain.measurement.dto.response.MeasurementCreateResponse;
import com.tanvx.measurements.domain.measurement.dto.response.MeasurementResponse;
import com.tanvx.measurements.domain.measurement.service.MeasurementService;
import com.tanvx.measurements.infrastructure.common.ApiResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MeasurementController {

  private final MeasurementService measurementService;

  // Create a Measurement
  @PostMapping("/measurements")
  public ResponseEntity<ApiResponse<MeasurementCreateResponse>> createMeasurement(@RequestBody
  MeasurementCreateRequest request) {
    log.info("Invoking MeasurementController - createMeasurement: request={}", request);
    Instant start = Instant.now();
    MeasurementCreateResponse measurementCreateResponse = measurementService.createMeasurement(
        request);
    Instant end = Instant.now();
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.<MeasurementCreateResponse>builder()
            .data(measurementCreateResponse)
            .executionTime(Duration.between(start, end))
            .message("Success")
            .build());
  }

  // Get All Measurements
  @GetMapping("/measurements/{cityId}/")
  public ResponseEntity<ApiResponse<Map<String, Page<MeasurementResponse>>>> getAllMeasurements(
      @PathVariable("cityId") Long cityId,
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "50") Integer size,
      @RequestParam(defaultValue = "measurementTime") String sort,
      @RequestParam(defaultValue = "asc") String order) {

    log.info(
        "Invoking MeasurementController - getAllMeasurements: page={}, size={}, sort={}, order={}",
        page, size, sort, order);
    Instant start = Instant.now();
    Map<String, Page<MeasurementResponse>> measurementResponsePage = measurementService.findMeasurement(
        new MeasurementRequest(cityId, page, size, sort, order));
    Instant end = Instant.now();
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.<Map<String, Page<MeasurementResponse>>>builder()
            .data(measurementResponsePage)
            .executionTime(Duration.between(start, end))
            .message("Success")
            .build());
  }

  // Get Measurements by City ID
  @GetMapping("/measurements/{cityId}/city")
  public ResponseEntity<ApiResponse<MeasurementCityResponse>> getMeasurementsByCityId(
      @PathVariable Long cityId, @RequestParam("method") String method) {
    log.info("Invoking MeasurementController - getMeasurementsByCityId: cityId={}, method={}",
        cityId, method);
    Instant start = Instant.now();
    MeasurementCityResponse response =
        measurementService.findMeasurementByCityId(cityId);
    Instant end = Instant.now();
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse
            .<MeasurementCityResponse>builder()
            .data(response)
            .executionTime(Duration.between(start, end))
            .message("Success")
            .build());
  }
}
