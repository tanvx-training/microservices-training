package com.tanvx.cities.domain.city.service;

import com.tanvx.cities.domain.city.dto.request.CityCreateRequest;
import com.tanvx.cities.domain.city.dto.request.CityRequest;
import com.tanvx.cities.domain.city.dto.response.CityCreateResponse;
import com.tanvx.cities.domain.city.dto.response.CityResponse;
import org.springframework.data.domain.Page;

public interface CityService {

  /**
   * Method to find page cities
   * @param request CityRequest
   * @return Page<CityResponse>
   */
  Page<CityResponse> findCity(CityRequest request);

  /**
   * Method to find city by name
   * @param cityId Long
   * @return CityResponse
   */
  CityResponse findCityById(Long cityId);

  /**
   * Method to create a new city
   * @param request CityCreateRequest
   * @return CityCreateResponse
   */
  CityCreateResponse createCity(CityCreateRequest request);
}
