package com.tanvx.users.domain.user.dto.request;

public record UserRegisterRequest(String name, String email, String password, Long cityId) {

}
