package com.tanvx.measurements.domain.measurement.repository.query;

import java.time.LocalDateTime;

public record MeasurementCityQueryResponse (Double temperature, LocalDateTime measurementTime) {

}
