package com.tanvx.measurements.domain.measurement.dto.response;

import java.time.LocalDateTime;

public record MeasurementCityQueryResponse (Double temperature, LocalDateTime measurementTime) {

}
