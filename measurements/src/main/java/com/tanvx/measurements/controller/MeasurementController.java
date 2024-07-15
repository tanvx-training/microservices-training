package com.tanvx.measurements.controller;

import com.tanvx.measurements.common.ApiResponse;
import com.tanvx.measurements.dto.request.MeasurementCreateRequest;
import com.tanvx.measurements.dto.request.MeasurementRequest;
import com.tanvx.measurements.dto.request.MeasurementUpdateRequest;
import com.tanvx.measurements.dto.response.MeasurementCreateResponse;
import com.tanvx.measurements.dto.response.MeasurementDeleteResponse;
import com.tanvx.measurements.dto.response.MeasurementResponse;
import com.tanvx.measurements.dto.response.MeasurementUpdateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
  public ResponseEntity<ApiResponse<MeasurementCreateResponse>> getMeasurementsByCityId(
      @PathVariable Long cityId) {

    return null;
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
