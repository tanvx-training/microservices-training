package com.tanvx.cities.domain.measurement.service.impl;

import com.tanvx.cities.domain.client.CityFeignClient;
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
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeasurementServiceImpl implements MeasurementService {

  private final ValidationUtil validationUtil;

  private final MeasurementRepository measurementRepository;

  private final CityFeignClient cityFeignClient;

  @Override
  public List<MeasurementResponse> findMeasurement(MeasurementRequest request) {

    cityFeignClient.findCityById(request.cityId());

    Pageable pageable = PageRequest.of(request.page(), request.size());

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

    cityFeignClient.findCityById(cityId);

    MeasurementCityNativeQueryResponse maxTemperature = measurementRepository
        .findMaxMeasurementByTemperatureAndCityId(cityId);

    MeasurementCityNativeQueryResponse minTemperature = measurementRepository
        .findMinMeasurementByTemperatureAndCityId(cityId);

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
    cityFeignClient.findCityById(request.cityId());

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
