package com.tanvx.measurements.infrastructure.common;

import java.time.Duration;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {

  private T data;
  private Duration executionTime;
  private String message;
}
