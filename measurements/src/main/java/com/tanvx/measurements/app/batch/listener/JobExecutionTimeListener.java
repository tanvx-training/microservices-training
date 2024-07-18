package com.tanvx.measurements.app.batch.listener;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JobExecutionTimeListener implements JobExecutionListener {

  private Instant startTime;

  @Override
  public void beforeJob(JobExecution jobExecution) {
    startTime = Instant.now();
  }

  @Override
  public void afterJob(JobExecution jobExecution) {
    Instant endTime = Instant.now();
    Duration duration = Duration.between(startTime, endTime);
    log.info("Execution time: {}", duration);
  }
}
