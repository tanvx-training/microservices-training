package com.tanvx.cities.domain.measurement.service.impl;

import com.tanvx.cities.domain.measurement.dto.request.MeasurementCreateRequest;
import com.tanvx.cities.domain.measurement.dto.request.MeasurementRequest;
import com.tanvx.cities.domain.measurement.dto.response.MeasurementCityResponse;
import com.tanvx.cities.domain.measurement.dto.response.MeasurementCityResponse.MeasurementCityData;
import com.tanvx.cities.domain.measurement.dto.response.MeasurementCreateResponse;
import com.tanvx.cities.domain.measurement.dto.response.MeasurementResponse;
import com.tanvx.cities.domain.measurement.entity.Measurement;
import com.tanvx.cities.domain.measurement.repository.MeasurementRepository;
import com.tanvx.cities.domain.measurement.repository.query.MeasurementCityNativeQueryResponse;
import com.tanvx.cities.domain.measurement.service.MeasurementService;
import com.tanvx.cities.infrastructure.util.ValidationUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeasurementServiceImpl implements MeasurementService {

  private final ValidationUtil validationUtil;

  private final MeasurementRepository measurementRepository;

  @Override
  public List<MeasurementResponse> findMeasurement(MeasurementRequest request) {

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

    // Find max measurement by temperature and city id
    MeasurementCityNativeQueryResponse maxTemperature = measurementRepository
        .findMaxMeasurementByTemperatureAndCityId(cityId);
    // Find min measurement by temperature and city id
    MeasurementCityNativeQueryResponse minTemperature = measurementRepository
        .findMinMeasurementByTemperatureAndCityId(cityId);
    // Find average measurement by temperature and city id
    Double averageTemperature = measurementRepository.findAverageTemperature(cityId);

    return MeasurementCityResponse.builder()
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

    Measurement measurement = new Measurement();
    measurement.setTemperature(request.temperature());
    measurement.setMeasurementTime(request.measurementTime());
    measurement.setDeleteFlg(Boolean.FALSE);
    measurement.setCityId(request.cityId());
    measurement.setCreatedAt(LocalDateTime.now());
    measurement.setCreatedBy("System");
    measurementRepository.save(measurement);

    return new MeasurementCreateResponse(measurement.getId(), measurement.getTemperature(),
        measurement.getMeasurementTime(), measurement.getDeleteFlg(),
        measurement.getCityId());
  }
}
