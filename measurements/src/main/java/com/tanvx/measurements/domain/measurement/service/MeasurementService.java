package com.tanvx.measurements.domain.measurement.service;

import com.tanvx.measurements.domain.city.entity.City;
import com.tanvx.measurements.domain.measurement.dto.request.MeasurementCreateRequest;
import com.tanvx.measurements.domain.measurement.dto.request.MeasurementRequest;
import com.tanvx.measurements.domain.measurement.dto.response.MeasurementCityResponse;
import com.tanvx.measurements.domain.measurement.dto.response.MeasurementCreateResponse;
import com.tanvx.measurements.domain.measurement.dto.response.MeasurementResponse;
import java.util.Map;
import org.springframework.data.domain.Page;

public interface MeasurementService {

  Map<String, Page<MeasurementResponse>> findMeasurement(MeasurementRequest request);

  MeasurementCityResponse findMeasurementUsingJpa(Long cityId);

  MeasurementCityResponse findMeasurementUsingJpaWithNativeQuery(Long cityId);

  MeasurementCityResponse findMeasurementUsingJpaWithJPQL(Long cityId);

  /**
   * Create new measurement
   * @param request MeasurementCreateRequest
   * @return MeasurementCreateResponse
   */
  MeasurementCreateResponse createMeasurement(MeasurementCreateRequest request);
}
