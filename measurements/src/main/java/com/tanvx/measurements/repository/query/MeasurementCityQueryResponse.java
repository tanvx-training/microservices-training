package com.tanvx.measurements.repository.query;

import java.time.LocalDateTime;

public interface MeasurementCityQueryResponse {

  Double getTemperature();

  LocalDateTime getMeasurementTime();
}
