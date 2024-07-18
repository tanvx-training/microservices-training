package com.tanvx.measurements.app.batch.processor;

import com.tanvx.measurements.app.batch.model.MeasurementCsv;
import com.tanvx.measurements.domain.city.entity.City;
import com.tanvx.measurements.domain.measurement.entity.Measurement;
import com.tanvx.measurements.infrastructure.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ProcessorMeasurement implements ItemProcessor<MeasurementCsv, Measurement> {

  private static final String CREATE_BY = "system_admin";

  private static final LocalDateTime startTime = LocalDateTime.of(2022, 1, 1, 0, 0, 0, 0);

  private static final LocalDateTime endTime = LocalDateTime.of(2024, 1, 1, 0, 0, 0, 0);

    private final Map<String, City> mapCity;

  @Override
  public Measurement process(MeasurementCsv item) {

    Measurement measurement = new Measurement();
    measurement.setTemperature(item.getTemperature());
    measurement.setMeasurementTime(DateTimeUtil.getRandomBetween(startTime, endTime));
    measurement.setDeleteFlg(Boolean.FALSE);
    measurement.setCreatedAt(LocalDateTime.now());
    measurement.setCreatedBy(CREATE_BY);
    measurement.setCity(mapCity.get(item.getCity()));
    return measurement;
  }
}
