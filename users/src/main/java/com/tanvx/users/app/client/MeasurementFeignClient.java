package com.tanvx.users.app.client;

import com.tanvx.users.infrastructure.common.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("measurement-service")
public interface MeasurementFeignClient {

  @GetMapping(value = "/measurements/{cityId}/city", consumes = "application/json")
  ResponseEntity<ApiResponse<MeasurementCityResponse>> getMeasurementsByCityId(
      @PathVariable Long cityId);
}
