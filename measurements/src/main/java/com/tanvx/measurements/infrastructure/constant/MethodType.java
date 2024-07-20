package com.tanvx.measurements.infrastructure.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MethodType {
    JPA("jpa"),
    JPA_WITH_JPQL("jpa-with-jpql"),
    JPA_WITH_NATIVE("jpa-with-native");

    private final String value;
}
