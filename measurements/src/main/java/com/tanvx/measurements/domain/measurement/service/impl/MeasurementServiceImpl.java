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
import java.util.List;
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
  public List<MeasurementResponse> findMeasurement(MeasurementRequest request) {
    Optional<City> optionalCity = cityRepository.findById(request.cityId());
    if (optionalCity.isEmpty()) {
      throw new ServiceException(HttpStatus.BAD_REQUEST, CITY_NOT_FOUND_ERROR);
    }
    Pageable pageable = PageRequest.of(request.page(), request.size(),
        Sort.by(Direction.ASC, "measurementTime"));
    return measurementRepository.findAllByCityId(request.cityId(), pageable).getContent()
        .stream()
        .map(e -> MeasurementResponse.builder()
            .id(e.getId())
            .temperature(e.getTemperature())
            .measurementTime(e.getMeasurementTime())
            .build())
        .toList();
  }

  @Override
  public MeasurementCityResponse findMeasurementByCityId(Long cityId) {

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
