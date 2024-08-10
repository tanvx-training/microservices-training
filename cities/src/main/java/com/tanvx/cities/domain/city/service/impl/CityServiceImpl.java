package com.tanvx.cities.domain.city.service.impl;

import com.tanvx.cities.domain.city.dto.request.CityCreateRequest;
import com.tanvx.cities.domain.city.dto.request.CityRequest;
import com.tanvx.cities.domain.city.dto.response.CityCreateResponse;
import com.tanvx.cities.domain.city.dto.response.CityResponse;
import com.tanvx.cities.domain.city.entity.City;
import com.tanvx.cities.app.dto.exception.ServiceException;
import com.tanvx.cities.domain.city.repository.CityRepository;
import com.tanvx.cities.domain.city.service.CityService;
import com.tanvx.cities.infrastructure.util.ValidationUtil;
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

    Pageable pageable =
        Objects.nonNull(request.sort())
            ? PageRequest.of(request.page() - 1, request.size(), Sort.by(Direction.ASC, request.sort()))
            : PageRequest.of(request.page() - 1, request.size());

    return cityRepository.findAllCity(pageable);
  }

  @Override
  public CityResponse findCityById(Long cityId) {

    Optional<City> optionalCity = cityRepository.findById(cityId);
    if (optionalCity.isEmpty()) {
      throw new ServiceException(HttpStatus.BAD_REQUEST, CITY_NOT_FOUND_ERROR);
    }
    City city = optionalCity.get();
    return new CityResponse(city.getId(), city.getName());
  }

  @Override
  public CityCreateResponse createCity(CityCreateRequest request) {

    validationUtil.validateRequest(request);

    Optional<City> optionalCity = cityRepository.findByName(request.name());
    if (optionalCity.isPresent()) {
      throw new ServiceException(HttpStatus.BAD_REQUEST, CITY_NAME_EXISTED_ERROR);
    }
    City city = new City();
    city.setName(request.name().trim());
    cityRepository.save(city);

    return new CityCreateResponse(city.getId(), city.getName());
  }

}
