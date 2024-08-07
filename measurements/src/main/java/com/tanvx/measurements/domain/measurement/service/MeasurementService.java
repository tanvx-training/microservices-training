package com.tanvx.measurements.domain.measurement.service;

import com.tanvx.measurements.domain.city.entity.City;
import com.tanvx.measurements.domain.measurement.dto.request.MeasurementCreateRequest;
import com.tanvx.measurements.domain.measurement.dto.request.MeasurementRequest;
import com.tanvx.measurements.domain.measurement.dto.response.MeasurementCityResponse;
import com.tanvx.measurements.domain.measurement.dto.response.MeasurementCreateResponse;
import com.tanvx.measurements.domain.measurement.dto.response.MeasurementResponse;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;

public interface MeasurementService {

  List<MeasurementResponse> findMeasurement(MeasurementRequest request);

  MeasurementCityResponse findMeasurementByCityId(Long cityId);

  MeasurementCreateResponse createMeasurement(MeasurementCreateRequest request);
}
