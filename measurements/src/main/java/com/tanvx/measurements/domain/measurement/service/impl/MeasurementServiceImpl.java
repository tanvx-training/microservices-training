package com.tanvx.measurements.domain.measurement.service.impl;

import com.tanvx.measurements.domain.measurement.dto.request.MeasurementCreateRequest;
import com.tanvx.measurements.domain.measurement.dto.request.MeasurementRequest;
import com.tanvx.measurements.domain.measurement.dto.response.MeasurementCityResponse;
import com.tanvx.measurements.domain.measurement.dto.response.MeasurementCityResponse.MeasurementCityData;
import com.tanvx.measurements.domain.measurement.dto.response.MeasurementCreateResponse;
import com.tanvx.measurements.domain.measurement.dto.response.MeasurementResponse;
import com.tanvx.measurements.domain.city.entity.City;
import com.tanvx.measurements.domain.measurement.entity.Measurement;
import com.tanvx.measurements.app.dto.exception.ServiceException;
import com.tanvx.measurements.domain.city.repository.CityRepository;
import com.tanvx.measurements.domain.measurement.repository.MeasurementRepository;
import com.tanvx.measurements.domain.measurement.repository.query.MeasurementCityNativeQueryResponse;
import com.tanvx.measurements.domain.measurement.repository.query.MeasurementCityQueryResponse;
import com.tanvx.measurements.domain.measurement.service.MeasurementService;
import com.tanvx.measurements.infrastructure.util.ValidationUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeasurementServiceImpl implements MeasurementService {

  private static final String CITY_NOT_FOUND_ERROR = "City not found";

  private final ValidationUtil validationUtil;

  private final MeasurementRepository measurementRepository;

  private final CityRepository cityRepository;

  @Override
  public Map<String, Page<MeasurementResponse>> findMeasurement(MeasurementRequest request) {
    Optional<City> optionalCity = cityRepository.findById(request.cityId());
    if (optionalCity.isEmpty()) {
      throw new ServiceException(HttpStatus.BAD_REQUEST, CITY_NOT_FOUND_ERROR);
    }
    Sort sort = null;
    if (Objects.nonNull(request.sort())) {
      sort = Sort.by(Objects.equals(request.order(), "desc") ? Direction.DESC : Direction.ASC,
          request.sort());
    }
    City city = optionalCity.get();
    Pageable pageable = PageRequest.of(request.page(), request.size(), sort);
    Page<Measurement> measurementPage = measurementRepository.findAllByCity(city,
        pageable);

    return Map.of(city.getName(), measurementPage.map(m -> MeasurementResponse.builder()
        .id(m.getId())
        .temperature(m.getTemperature())
        .measurementTime(m.getMeasurementTime())
        .build()));
  }

  @Override
  public MeasurementCityResponse findMeasurementUsingJpa(Long cityId) {
    // Validate city ID
    Optional<City> optionalCity = cityRepository.findById(cityId);
    if (optionalCity.isEmpty()) {
      throw new ServiceException(HttpStatus.BAD_REQUEST, CITY_NOT_FOUND_ERROR);
    }
    // Initial
    Optional<Measurement> measurementMax = Optional.empty();
    Optional<Measurement> measurementMin = Optional.empty();

    int defaultPage = 0;
    int defaultSize = 1000;
    double totalTemperature = 0;
    long totalCount = 0;
    Pageable pageable = PageRequest.of(defaultPage, defaultSize);
    Page<Measurement> measurementPage;

    do {
      measurementPage = measurementRepository.findAll(pageable);
      // Find max temperature in current page
      Optional<Measurement> max = measurementPage.getContent().stream()
          .max(Comparator.comparing(Measurement::getTemperature));
      if (measurementMax.isEmpty()) {
        measurementMax = max;
      } else {
        if (max.isPresent() && (max.get().getTemperature() > measurementMax.get()
            .getTemperature())) {
          measurementMax = max;
        }
      }
      // Find min temperature in current page
      Optional<Measurement> min = measurementPage.getContent().stream()
          .min(Comparator.comparing(Measurement::getTemperature));
      if (measurementMin.isEmpty()) {
        measurementMin = min;
      } else {
        if (min.isPresent() && (min.get().getTemperature() < measurementMin.get()
            .getTemperature())) {
          measurementMin = min;
        }
      }
      // Calculate total temperature and count in current page
      totalTemperature += measurementPage.getContent()
          .stream()
          .mapToDouble(Measurement::getTemperature)
          .sum();
      totalCount += measurementPage.getNumberOfElements();
      pageable = pageable.next();
    } while (measurementPage.hasNext());

    BigDecimal averageTemperature = totalCount > 0
        ? BigDecimal.valueOf(totalTemperature / totalCount).setScale(2, RoundingMode.HALF_UP)
        : BigDecimal.valueOf(Double.NaN);

    return MeasurementCityResponse.builder()
        .city(optionalCity.get().getName())
        .max(measurementMax.map(m -> MeasurementCityData.builder().temperature(m.getTemperature())
                .measurementTime(m.getMeasurementTime()).build())
            .orElse(MeasurementCityData.builder().build()))
        .min(measurementMin.map(m -> MeasurementCityData.builder().temperature(m.getTemperature())
            .measurementTime(m.getMeasurementTime()).build()).orElse(MeasurementCityData.builder()
            .build()))
        .average(averageTemperature)
        .build();
  }

  @Override
  public MeasurementCityResponse findMeasurementUsingJpaWithNativeQuery(Long cityId) {

    // Validate city ID
    Optional<City> optionalCity = cityRepository.findById(cityId);
    if (optionalCity.isEmpty()) {
      throw new ServiceException(HttpStatus.BAD_REQUEST, CITY_NOT_FOUND_ERROR);
    }
    // Find max measurement by temperature and city id
    MeasurementCityNativeQueryResponse maxTemperature = measurementRepository
        .findMaxMeasurementByTemperatureAndCityId(cityId);
    // Find min measurement by temperature and city id
    MeasurementCityNativeQueryResponse minTemperature = measurementRepository
        .findMinMeasurementByTemperatureAndCityId(cityId);
    // Find average measurement by temperature and city id
    Double averageTemperature = measurementRepository.findAverageTemperature(cityId);

    return MeasurementCityResponse.builder()
        .city(optionalCity.get().getName())
        .max(MeasurementCityData.builder().temperature(maxTemperature.getTemperature())
            .measurementTime(maxTemperature.getMeasurementTime()).build())
        .min(MeasurementCityData.builder().temperature(minTemperature.getTemperature())
            .measurementTime(minTemperature.getMeasurementTime()).build())
        .average(BigDecimal.valueOf(averageTemperature).setScale(2, RoundingMode.HALF_UP))
        .build();
  }

  @Override
  public MeasurementCityResponse findMeasurementUsingJpaWithJPQL(Long cityId) {
    // Validate city ID
    Optional<City> optionalCity = cityRepository.findById(cityId);
    if (optionalCity.isEmpty()) {
      throw new ServiceException(HttpStatus.BAD_REQUEST, CITY_NOT_FOUND_ERROR);
    }
    // Find max measurement by temperature and city id
    MeasurementCityQueryResponse max = measurementRepository
        .findMeasurementWithMaxTemperature(cityId);
    // Find min measurement by temperature and city id
    MeasurementCityQueryResponse min = measurementRepository
        .findMeasurementWithMinTemperature(cityId);
    // Find average measurement by temperature and city id
    Double averageTemperature = measurementRepository.findMeasurementWithAverageTemperature(cityId);

    return MeasurementCityResponse.builder()
        .city(optionalCity.get().getName())
        .max(MeasurementCityData.builder().temperature(max.temperature())
            .measurementTime(max.measurementTime()).build())
        .min(MeasurementCityData.builder().temperature(min.temperature())
            .measurementTime(min.measurementTime()).build())
        .average(BigDecimal.valueOf(averageTemperature).setScale(2, RoundingMode.HALF_UP))
        .build();
  }

  @Override
  public MeasurementCreateResponse createMeasurement(MeasurementCreateRequest request) {

    validationUtil.validateRequest(request);

    Optional<City> optionalCity = cityRepository.findById(request.cityId());
    if (optionalCity.isEmpty()) {
      throw new ServiceException(HttpStatus.BAD_REQUEST, CITY_NOT_FOUND_ERROR);
    }
    Measurement measurement = new Measurement();
    measurement.setTemperature(request.temperature());
    measurement.setMeasurementTime(request.measurementTime());
    measurement.setDeleteFlg(Boolean.FALSE);
    measurement.setCity(optionalCity.get());
    measurement.setCreatedAt(LocalDateTime.now());
    measurement.setCreatedBy("System");
    measurementRepository.save(measurement);

    return new MeasurementCreateResponse(measurement.getId(), measurement.getTemperature(),
        measurement.getMeasurementTime(), measurement.getDeleteFlg(),
        measurement.getCity().getName());
  }
}
