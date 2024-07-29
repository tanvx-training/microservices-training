package com.tanvx.users.domain.user.dto.response;

import com.tanvx.users.app.client.MeasurementCityResponse;
import java.time.LocalDateTime;

public record UserResponse(String name, String email, Long cityId, LocalDateTime createdAt,
                           String createdBy, MeasurementCityResponse city) {

}
