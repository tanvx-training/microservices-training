package com.tanvx.cities.domain.measurement.repository;

import com.tanvx.cities.domain.measurement.entity.Measurement;
import com.tanvx.cities.domain.measurement.repository.query.MeasurementCityNativeQueryResponse;
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
          WHERE m.city_id = :cityId"""
  )
  Double findAverageTemperature(@Param("cityId") Long cityId);
}
