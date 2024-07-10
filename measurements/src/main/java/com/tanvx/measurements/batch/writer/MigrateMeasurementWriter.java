package com.tanvx.measurements.batch.writer;

import com.tanvx.measurements.entity.City;
import com.tanvx.measurements.entity.Measurement;
import com.tanvx.measurements.repository.CityRepository;
import com.tanvx.measurements.repository.MeasurementRepository;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
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
