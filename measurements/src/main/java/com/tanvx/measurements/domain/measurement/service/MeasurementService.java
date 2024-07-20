package com.tanvx.measurements.domain.measurement.service;

import com.tanvx.measurements.domain.measurement.dto.request.MeasurementCreateRequest;
import com.tanvx.measurements.domain.measurement.dto.request.MeasurementDeleteRequest;
import com.tanvx.measurements.domain.measurement.dto.request.MeasurementRequest;
import com.tanvx.measurements.domain.measurement.dto.request.MeasurementUpdateRequest;
import com.tanvx.measurements.domain.measurement.dto.response.MeasurementCityResponse;
import com.tanvx.measurements.domain.measurement.dto.response.MeasurementCreateResponse;
import com.tanvx.measurements.domain.measurement.dto.response.MeasurementDeleteResponse;
import com.tanvx.measurements.domain.measurement.dto.response.MeasurementResponse;
import com.tanvx.measurements.domain.measurement.dto.response.MeasurementUpdateResponse;
import org.springframework.data.domain.Page;

public interface MeasurementService {

  /**
   * Get page measurements
   * @param request MeasurementRequest
   * @return Page<MeasurementResponse>
   */
  Page<MeasurementResponse> findMeasurement(MeasurementRequest request);

  /**
   * Get page measurements
   * @param cityId Long
   * @return Page<MeasurementResponse>
   */
  MeasurementCityResponse findMeasurementUsingJpa(Long cityId);

  /**
   * Get page measurements
   * @param cityId Long
   * @return Page<MeasurementResponse>
   */
  MeasurementCityResponse findMeasurementUsingJpaWithNativeQuery(Long cityId);

  MeasurementCityResponse findMeasurementUsingJpaWithJPQL(Long cityId);

  MeasurementCityResponse findMeasurementByCityIdInCustom(Long cityId);

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
