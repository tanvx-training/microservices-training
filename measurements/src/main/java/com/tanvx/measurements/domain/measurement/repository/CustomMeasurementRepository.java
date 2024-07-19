package com.tanvx.measurements.domain.measurement.repository;

import com.tanvx.measurements.domain.measurement.dto.response.MeasurementCityQueryResponse;

public interface CustomMeasurementRepository {

  MeasurementCityQueryResponse findMaxMeasurementCustom(Long cityId);

  MeasurementCityQueryResponse findMinMeasurementCustom(Long cityId);

  Double findAverageTemperatureCustom(Long cityId);
}
