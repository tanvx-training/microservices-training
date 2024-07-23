package com.tanvx.users.domain.user.dto.response;

import java.time.LocalDateTime;

public record UserResponse (String name, String email, Long cityId, LocalDateTime createdAt, String createdBy) {

}
