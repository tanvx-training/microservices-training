package com.tanvx.measurements.domain.measurement.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record MeasurementUpdateRequest(
    @NotNull(message = "Id is required.") Long id, Double temperature,
    LocalDateTime measurementTime,
    Long cityId) {

}
