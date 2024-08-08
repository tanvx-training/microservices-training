package com.tanvx.cities.infrastructure.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Service;

@Service
public class DateTimeUtil {

  public static LocalDateTime getRandomBetween(LocalDateTime start, LocalDateTime end) {

    long startMs = start.toEpochSecond(ZoneOffset.UTC);
    long endMs = end.toEpochSecond(ZoneOffset.UTC);
    long randomMs = ThreadLocalRandom.current().nextLong(startMs, endMs);
    return LocalDateTime.ofEpochSecond(randomMs,0, ZoneOffset.UTC);
  }
}
