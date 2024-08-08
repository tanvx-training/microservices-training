package com.tanvx.cities.domain.measurement.service;

import com.tanvx.cities.domain.measurement.dto.request.MeasurementCreateRequest;
import com.tanvx.cities.domain.measurement.dto.request.MeasurementRequest;
import com.tanvx.cities.domain.measurement.dto.response.MeasurementCityResponse;
import com.tanvx.cities.domain.measurement.dto.response.MeasurementCreateResponse;
import com.tanvx.cities.domain.measurement.dto.response.MeasurementResponse;
import java.util.List;

public interface MeasurementService {

  List<MeasurementResponse> findMeasurement(MeasurementRequest request);

  MeasurementCityResponse findMeasurementByCityId(Long cityId);

  MeasurementCreateResponse createMeasurement(MeasurementCreateRequest request);
}
