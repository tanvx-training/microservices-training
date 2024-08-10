package com.tanvx.cities.domain.client;

import com.tanvx.cities.app.dto.exception.ServiceException;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.util.Objects;
import org.springframework.http.HttpStatus;

public class CustomErrorDecoder implements ErrorDecoder {

  private static final String CITY_NOT_FOUND_ERROR = "City not found";

  @Override
  public Exception decode(String s, Response response) {
    HttpStatus status = HttpStatus.valueOf(response.status());
    if (Objects.equals(status, HttpStatus.BAD_REQUEST)) {
      return new ServiceException(HttpStatus.BAD_REQUEST, CITY_NOT_FOUND_ERROR);
    }
    return new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
  }
}
