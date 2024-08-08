package com.tanvx.cities.domain.measurement.repository.query;

import java.time.LocalDateTime;

public interface MeasurementCityNativeQueryResponse {

  Double getTemperature();

  LocalDateTime getMeasurementTime();
}
