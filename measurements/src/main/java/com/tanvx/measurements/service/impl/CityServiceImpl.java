package com.tanvx.measurements.service.impl;

import com.tanvx.measurements.dto.request.CityCreateRequest;
import com.tanvx.measurements.dto.request.CityRequest;
import com.tanvx.measurements.dto.request.CityUpdateRequest;
import com.tanvx.measurements.dto.response.CityCreateResponse;
import com.tanvx.measurements.dto.response.CityResponse;
import com.tanvx.measurements.dto.response.CityUpdateResponse;
import com.tanvx.measurements.service.CityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

  @Override
  public Page<CityResponse> findCity(CityRequest request) {
    return null;
  }

  @Override
  public CityResponse findCityByName(String name) {
    return null;
  }

  @Override
  public CityCreateResponse createCity(CityCreateRequest request) {
    return null;
  }

  @Override
  public CityUpdateResponse updateCity(CityUpdateRequest request) {
    return null;
  }
}
