package com.tanvx.measurements.app.batch.writer;

import com.tanvx.measurements.domain.measurement.entity.Measurement;
import com.tanvx.measurements.domain.measurement.repository.MeasurementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MigrateMeasurementWriter implements ItemWriter<Measurement> {

  private final MeasurementRepository measurementRepository;

  @Override
  public void write(Chunk<? extends Measurement> chunk) {
    measurementRepository.saveAll(chunk);
  }
}
