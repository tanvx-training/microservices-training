package com.tanvx.measurements.domain.measurement.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record MeasurementCreateRequest(
    @NotNull(message = "Temperature is required.") Double temperature,
    @NotNull(message = "Measurement Time is required.") LocalDateTime measurementTime,
    @NotNull(message = "City is required.") Long cityId) {

}
