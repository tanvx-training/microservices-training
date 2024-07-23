package com.tanvx.measurements.domain.measurement.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MeasurementResponse {

  private Long id;

  private Double temperature;

  private LocalDateTime measurementTime;
}
