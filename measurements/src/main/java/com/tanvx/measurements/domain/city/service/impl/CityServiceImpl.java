package com.tanvx.measurements.domain.city.service.impl;

import com.tanvx.measurements.domain.city.dto.request.CityCreateRequest;
import com.tanvx.measurements.domain.city.dto.request.CityRequest;
import com.tanvx.measurements.domain.city.dto.response.CityCreateResponse;
import com.tanvx.measurements.domain.city.dto.response.CityResponse;
import com.tanvx.measurements.domain.city.entity.City;
import com.tanvx.measurements.app.dto.exception.ServiceException;
import com.tanvx.measurements.domain.city.repository.CityRepository;
import com.tanvx.measurements.domain.city.service.CityService;
import com.tanvx.measurements.infrastructure.util.ValidationUtil;
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
public class CityServiceImpl implements CityService {

  private static final String CITY_NAME_EXISTED_ERROR = "City name already exists";

  private static final String CITY_NOT_FOUND_ERROR = "City not found";

  private final CityRepository cityRepository;

  private final ValidationUtil validationUtil;

  @Override
  public Page<CityResponse> findCity(CityRequest request) {

    validationUtil.validateRequest(request);

    Pageable pageable;
    if(Objects.nonNull(request.field())) {
      Sort sort = Sort.by(Direction.ASC, request.field());
      pageable = PageRequest.of(request.page(), request.size(), sort);
    } else {
      pageable = PageRequest.of(request.page(), request.size());
    }

    return cityRepository.findAllCity(pageable);
  }

  @Override
  public CityResponse findCityByName(String name) {

    Optional<City> optionalCity = cityRepository.findByName(name);
    if(optionalCity.isEmpty()) {
      throw new ServiceException(HttpStatus.BAD_REQUEST, CITY_NOT_FOUND_ERROR);
    }
    City city = optionalCity.get();
    return new CityResponse(city.getId(), city.getName());
  }

  @Override
  public CityCreateResponse createCity(CityCreateRequest request) {

    validationUtil.validateRequest(request);

    Optional<City> optionalCity = cityRepository.findByName(request.name());
    if(optionalCity.isPresent()) {
      throw new ServiceException(HttpStatus.BAD_REQUEST, CITY_NAME_EXISTED_ERROR);
    }
    City city = new City();
    city.setName(request.name().trim());
    cityRepository.save(city);

    return new CityCreateResponse(city.getId(), city.getName());
  }

}
