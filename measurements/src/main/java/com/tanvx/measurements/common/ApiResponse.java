package com.tanvx.measurements.common;

import java.time.Duration;
import lombok.Builder;

@Builder
public class ApiResponse<T> {

  private T data;
  private Duration executionTime;
  private String message;
}
