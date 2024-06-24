package com.tanvx.measurements.service.impl;

import com.tanvx.measurements.dto.request.MeasurementCreateRequest;
import com.tanvx.measurements.dto.request.MeasurementDeleteRequest;
import com.tanvx.measurements.dto.request.MeasurementRequest;
import com.tanvx.measurements.dto.request.MeasurementUpdateRequest;
import com.tanvx.measurements.dto.response.MeasurementCreateResponse;
import com.tanvx.measurements.dto.response.MeasurementDeleteResponse;
import com.tanvx.measurements.dto.response.MeasurementResponse;
import com.tanvx.measurements.dto.response.MeasurementUpdateResponse;
import com.tanvx.measurements.service.MeasurementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeasurementServiceImpl implements MeasurementService {

  @Override
  public Page<MeasurementResponse> findMeasurement(MeasurementRequest request) {
    return null;
  }

  @Override
  public MeasurementCreateResponse createMeasurement(MeasurementCreateRequest request) {
    return null;
  }

  @Override
  public MeasurementUpdateResponse updateMeasurement(MeasurementUpdateRequest request) {
    return null;
  }

  @Override
  public MeasurementDeleteResponse deleteMeasurement(MeasurementDeleteRequest request) {
    return null;
  }
}
