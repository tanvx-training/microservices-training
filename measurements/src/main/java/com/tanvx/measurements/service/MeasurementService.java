package com.tanvx.measurements.service;

import com.tanvx.measurements.dto.request.MeasurementCreateRequest;
import com.tanvx.measurements.dto.request.MeasurementDeleteRequest;
import com.tanvx.measurements.dto.request.MeasurementRequest;
import com.tanvx.measurements.dto.request.MeasurementUpdateRequest;
import com.tanvx.measurements.dto.response.MeasurementCreateResponse;
import com.tanvx.measurements.dto.response.MeasurementDeleteResponse;
import com.tanvx.measurements.dto.response.MeasurementResponse;
import com.tanvx.measurements.dto.response.MeasurementUpdateResponse;
import org.springframework.data.domain.Page;

public interface MeasurementService {

  /**
   * Get page measurements
   * @param request MeasurementRequest
   * @return Page<MeasurementResponse>
   */
  Page<MeasurementResponse> findMeasurement(MeasurementRequest request);

  /**
   * Create new measurement
   * @param request MeasurementCreateRequest
   * @return MeasurementCreateResponse
   */
  MeasurementCreateResponse createMeasurement(MeasurementCreateRequest request);

  /**
   * Update measurement
   * @param request MeasurementUpdateRequest
   * @return MeasurementUpdateResponse
   */
  MeasurementUpdateResponse updateMeasurement(MeasurementUpdateRequest request);

  /**
   * Delete measurement
   * @param request MeasurementDeleteRequest
   * @return MeasurementDeleteResponse
   */
  MeasurementDeleteResponse deleteMeasurement(MeasurementDeleteRequest request);
}
