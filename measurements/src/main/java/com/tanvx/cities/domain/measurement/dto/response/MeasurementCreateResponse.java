package com.tanvx.cities.domain.measurement.dto.response;

import java.time.LocalDateTime;

public record MeasurementCreateResponse(Long id,
                                        Double temperature,
                                        LocalDateTime measurementTime,
                                        Boolean deleteFlg,
                                        Long cityId) {

}
