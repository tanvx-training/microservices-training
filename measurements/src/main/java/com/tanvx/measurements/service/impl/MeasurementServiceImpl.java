package com.tanvx.measurements.service.impl;

import com.tanvx.measurements.dto.request.MeasurementCreateRequest;
import com.tanvx.measurements.dto.request.MeasurementDeleteRequest;
import com.tanvx.measurements.dto.request.MeasurementRequest;
import com.tanvx.measurements.dto.request.MeasurementUpdateRequest;
import com.tanvx.measurements.dto.response.MeasurementCreateResponse;
import com.tanvx.measurements.dto.response.MeasurementDeleteResponse;
import com.tanvx.measurements.dto.response.MeasurementResponse;
import com.tanvx.measurements.dto.response.MeasurementUpdateResponse;
import com.tanvx.measurements.entity.City;
import com.tanvx.measurements.entity.Measurement;
import com.tanvx.measurements.exception.ServiceException;
import com.tanvx.measurements.repository.CityRepository;
import com.tanvx.measurements.repository.MeasurementRepository;
import com.tanvx.measurements.service.MeasurementService;
import com.tanvx.measurements.util.ValidationUtil;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeasurementServiceImpl implements MeasurementService {

  private static final String CITY_NOT_FOUND_ERROR = "City not found";

  private static final String MEASUREMENT_NOT_FOUND_ERROR = "Measurement not found";

  private final ValidationUtil validationUtil;

  private final MeasurementRepository measurementRepository;

  private final CityRepository cityRepository;

  @Override
  public Page<MeasurementResponse> findMeasurement(MeasurementRequest request) {
    return null;
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
    measurementRepository.save(measurement);

    return new MeasurementCreateResponse(measurement.getId(), measurement.getTemperature(),
        measurement.getMeasurementTime(), measurement.getDeleteFlg(),
        measurement.getCity().getName());
  }

  @Override
  public MeasurementUpdateResponse updateMeasurement(MeasurementUpdateRequest request) {

    validationUtil.validateRequest(request);

    Optional<Measurement> optionalMeasurement = measurementRepository.findById(request.id());
    if (optionalMeasurement.isEmpty()) {
      throw new ServiceException(HttpStatus.BAD_REQUEST, MEASUREMENT_NOT_FOUND_ERROR);
    }
    Measurement measurement = optionalMeasurement.get();
    if (Objects.nonNull(request.temperature())) {
      measurement.setTemperature(request.temperature());
    }
    if (Objects.nonNull(request.measurementTime())) {
      measurement.setMeasurementTime(request.measurementTime());
    }
    if (Objects.nonNull(request.cityId())) {
      Optional<City> optionalCity = cityRepository.findById(request.cityId());
      if (optionalCity.isEmpty()) {
        throw new ServiceException(HttpStatus.BAD_REQUEST, CITY_NOT_FOUND_ERROR);
      }
      measurement.setCity(optionalCity.get());
    }
    measurementRepository.save(measurement);

    return new MeasurementUpdateResponse(measurement.getId(), measurement.getTemperature(),
        measurement.getMeasurementTime(), measurement.getDeleteFlg(),
        measurement.getCity().getName());
  }

  @Override
  public MeasurementDeleteResponse deleteMeasurement(MeasurementDeleteRequest request) {
    return null;
  }
}
