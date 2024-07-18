package com.tanvx.measurements.domain.city.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CityCreateRequest (@NotBlank(message = "City name is required.") String name) {

}
