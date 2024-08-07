package com.tanvx.measurements.domain.measurement.repository;

import com.tanvx.measurements.domain.city.entity.City;
import com.tanvx.measurements.domain.measurement.dto.response.MeasurementResponse;
import com.tanvx.measurements.domain.measurement.entity.Measurement;
import com.tanvx.measurements.domain.measurement.repository.query.MeasurementCityNativeQueryResponse;
import com.tanvx.measurements.domain.measurement.repository.query.MeasurementCityQueryResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Long> {

  /**Paging using JPA **/
  Page<Measurement> findAllByCityId(Long cityId, Pageable pageable);

  @Query(nativeQuery = true,
      value = """
          SELECT m.temperature, m.measurement_time FROM _measurement m
                   WHERE city_id = :cityId
          ORDER BY m.temperature DESC
          LIMIT 1"""
  )
  MeasurementCityNativeQueryResponse findMaxMeasurementByTemperatureAndCityId(
      @Param("cityId") Long cityId);

  @Query(nativeQuery = true,
      value = """
          SELECT m.temperature, m.measurement_time FROM _measurement m
                   WHERE city_id = :cityId
          ORDER BY m.temperature ASC
          LIMIT 1"""
  )
  MeasurementCityNativeQueryResponse findMinMeasurementByTemperatureAndCityId(
      @Param("cityId") Long cityId);

  @Query(nativeQuery = true,
      value = """
          SELECT AVG(temperature) as average
          FROM _measurement as m
                   INNER JOIN _city c on m.city_id = c.id
          WHERE c.id = :cityId"""
  )
  Double findAverageTemperature(@Param("cityId") Long cityId);
}
