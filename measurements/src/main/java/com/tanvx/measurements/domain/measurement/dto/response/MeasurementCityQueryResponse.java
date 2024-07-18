package com.tanvx.measurements.domain.measurement.dto.response;

import java.time.LocalDateTime;

public interface MeasurementCityQueryResponse {

  Double getTemperature();

  LocalDateTime getMeasurementTime();
}
