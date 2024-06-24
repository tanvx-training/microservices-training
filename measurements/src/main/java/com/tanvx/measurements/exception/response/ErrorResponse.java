package com.tanvx.measurements.exception.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {

  @JsonProperty("result")
  private final String result;

  @JsonProperty("errorMessages")
  private final List<String> errorMessages;
}
