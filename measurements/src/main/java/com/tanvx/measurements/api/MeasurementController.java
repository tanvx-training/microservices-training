package com.tanvx.measurements.api;

import com.tanvx.measurements.infrastructure.common.ApiResponse;
import com.tanvx.measurements.domain.measurement.dto.request.MeasurementCreateRequest;
import com.tanvx.measurements.domain.measurement.dto.request.MeasurementRequest;
import com.tanvx.measurements.domain.measurement.dto.request.MeasurementUpdateRequest;
import com.tanvx.measurements.domain.measurement.dto.response.MeasurementCityResponse;
import com.tanvx.measurements.domain.measurement.dto.response.MeasurementCreateResponse;
import com.tanvx.measurements.domain.measurement.dto.response.MeasurementDeleteResponse;
import com.tanvx.measurements.domain.measurement.dto.response.MeasurementResponse;
import com.tanvx.measurements.domain.measurement.dto.response.MeasurementUpdateResponse;
import com.tanvx.measurements.domain.measurement.service.MeasurementService;
import java.time.Duration;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    return null;
  }

  // Get All Measurements
  @GetMapping("/measurements")
  public ResponseEntity<ApiResponse<MeasurementResponse>> getAllMeasurements(
      @ModelAttribute MeasurementRequest request) {

    return null;
  }

  // Get Measurements by City ID
  @GetMapping("/cities/{cityId}/measurements")
  public ResponseEntity<ApiResponse<MeasurementCityResponse>> getMeasurementsByCityId(
      @PathVariable Long cityId) {
    log.info("Invoking MeasurementController - getMeasurementsByCityId: cityId={}", cityId);
    Instant start = Instant.now();
    MeasurementCityResponse response = measurementService.findMeasurementByCityIdInCustom(cityId);
    Instant end = Instant.now();
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse
            .<MeasurementCityResponse>builder()
            .data(response)
            .executionTime(Duration.between(start, end))
            .message("Success")
            .build());
  }

  // Update a Measurement
  @PutMapping("/measurements/{id}")
  public ResponseEntity<ApiResponse<MeasurementUpdateResponse>> updateMeasurement(
      @PathVariable Long id, @RequestBody MeasurementUpdateRequest request) {

    return null;
  }

  // Delete a Measurement
  @DeleteMapping("/measurements/{id}")
  public ResponseEntity<ApiResponse<MeasurementDeleteResponse>> deleteMeasurement(
      @PathVariable Long id) {
    return null;
  }
}
