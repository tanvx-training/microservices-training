package com.tanvx.measurements.domain.measurement.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MeasurementCityResponse {

  private String city;

  private MeasurementCityData max;

  private MeasurementCityData min;

  private BigDecimal average;

  @Data
  @Builder
  public static class MeasurementCityData {

    private Double temperature;

    private LocalDateTime measurementTime;
  }
}