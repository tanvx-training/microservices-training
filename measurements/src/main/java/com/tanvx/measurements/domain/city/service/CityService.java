package com.tanvx.measurements.domain.city.service;

import com.tanvx.measurements.domain.city.dto.request.CityCreateRequest;
import com.tanvx.measurements.domain.city.dto.request.CityRequest;
import com.tanvx.measurements.domain.city.dto.request.CityUpdateRequest;
import com.tanvx.measurements.domain.city.dto.response.CityCreateResponse;
import com.tanvx.measurements.domain.city.dto.response.CityResponse;
import com.tanvx.measurements.domain.city.dto.response.CityUpdateResponse;
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
   * @param name String
   * @return CityResponse
   */
  CityResponse findCityByName(String name);

  /**
   * Method to create a new city
   * @param request CityCreateRequest
   * @return CityCreateResponse
   */
  CityCreateResponse createCity(CityCreateRequest request);

  /**
   * Method to update a city
   * @param request CityUpdateRequest
   * @return CityUpdateResponse
   */
  CityUpdateResponse updateCity(CityUpdateRequest request);
}
