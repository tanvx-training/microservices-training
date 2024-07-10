package com.tanvx.measurements.schedule;

import com.tanvx.measurements.entity.City;
import com.tanvx.measurements.repository.CityRepository;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduledTasks {

  private static final String CREATE_BY = "system_admin";

  private static final String FILE_NAME = "E:\\Java\\spring-microservices\\measurements\\src\\main\\resources\\measurements.csv";

  private final CityRepository cityRepository;

//  @Scheduled(cron = "0 55 22 * * ?")
  public void migrateCity() {
    // Implement logic to migrate city data
    log.info("Starting migration...: {}", Instant.now());
    long startTime = System.currentTimeMillis();

    Set<String> set = new HashSet<>();
    String line;
    try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
      while ((line = br.readLine()) != null) {
        String[] data = line.split(";");
        set.add(data[0]);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    log.info("Migrating size: {}", set.size());

    set.stream().map(ScheduledTasks::convertToCity)
        .forEach(cityRepository::save);

    long endTime = System.currentTimeMillis();
    Duration duration = Duration.of(endTime - startTime, ChronoUnit.NANOS);
    log.info("Finished migration: {}", duration);
  }

  private static City convertToCity(String city) {
    return City.builder()
        .name(city)
        .createdAt(LocalDateTime.now())
        .createdBy(CREATE_BY)
        .build();
  }
}
