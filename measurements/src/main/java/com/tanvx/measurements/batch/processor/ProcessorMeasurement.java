package com.tanvx.measurements.batch.processor;

import com.tanvx.measurements.batch.model.MeasurementCsv;
import com.tanvx.measurements.entity.Measurement;
import com.tanvx.measurements.repository.CityRepository;
import com.tanvx.measurements.util.DateTimeUtil;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProcessorMeasurement implements ItemProcessor<MeasurementCsv, Measurement> {

  private static final String CREATE_BY = "system_admin";

  private static final LocalDateTime startTime = LocalDateTime.of(2022, 1, 1, 0, 0, 0, 0);

  private static final LocalDateTime endTime = LocalDateTime.of(2024, 1, 1, 0, 0, 0, 0);

  private final CityRepository cityRepository;

  @Override
  public Measurement process(MeasurementCsv item) {

    Measurement measurement = new Measurement();
    measurement.setTemperature(item.getTemperature());
    measurement.setMeasurementTime(DateTimeUtil.getRandomBetween(startTime, endTime));
    measurement.setDeleteFlg(Boolean.FALSE);
    measurement.setCreatedAt(LocalDateTime.now());
    measurement.setCreatedBy(CREATE_BY);
    measurement.setCity(cityRepository.findByName(item.getCity()).orElse(null));
    return measurement;
  }
}
