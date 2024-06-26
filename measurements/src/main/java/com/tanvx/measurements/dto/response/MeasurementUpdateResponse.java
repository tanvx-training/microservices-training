package com.tanvx.measurements.dto.response;

import java.time.LocalDateTime;

public record MeasurementUpdateResponse(
    Long id, Double temperature, LocalDateTime measurementTime, Boolean deleteFlg,
    String cityName) {

}
