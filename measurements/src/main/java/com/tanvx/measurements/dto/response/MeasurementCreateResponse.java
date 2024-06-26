package com.tanvx.measurements.dto.response;

import java.time.LocalDateTime;

public record MeasurementCreateResponse(Long id,
                                        Double temperature,
                                        LocalDateTime measurementTime,
                                        Boolean deleteFlg,
                                        String cityName) {

}
