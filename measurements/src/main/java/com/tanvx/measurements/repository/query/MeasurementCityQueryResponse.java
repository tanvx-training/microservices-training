package com.tanvx.measurements.repository.query;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MeasurementCityQueryResponse {

  private String city;

  private double temperatureMax;

  private LocalDateTime temperatureMaxTime;

  private double temperatureMin;

  private LocalDateTime temperatureMinTime;

  private double average;
}
