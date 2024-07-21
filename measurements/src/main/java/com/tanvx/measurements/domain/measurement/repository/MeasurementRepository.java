package com.tanvx.measurements.domain.measurement.repository;

import com.tanvx.measurements.domain.measurement.entity.Measurement;
import com.tanvx.measurements.domain.measurement.repository.query.MeasurementCityNativeQueryResponse;
import com.tanvx.measurements.domain.measurement.repository.query.MeasurementCityQueryResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Long> {

  /**Using JPA with JPQL**/
  @Query("""
  SELECT new com.tanvx.measurements.domain.measurement.repository.query.MeasurementCityQueryResponse(m.temperature, m.measurementTime)
  FROM Measurement m
  JOIN FETCH City c
  ON m.city.id = c.id
  WHERE c.id = :cityId
      AND m.deleteFlg = FALSE
  ORDER BY m.temperature
  DESC LIMIT 1""")
  MeasurementCityQueryResponse findMeasurementWithMaxTemperature(@Param("cityId") Long id);

  @Query("""
  SELECT new com.tanvx.measurements.domain.measurement.repository.query.MeasurementCityQueryResponse(m.temperature, m.measurementTime)
  FROM Measurement m
  JOIN FETCH City c
  ON m.city.id = c.id
  WHERE c.id = :cityId
    AND m.deleteFlg = FALSE
  ORDER BY m.temperature
  ASC LIMIT 1""")
  MeasurementCityQueryResponse findMeasurementWithMinTemperature(@Param("cityId") Long id);

  @Query("""
  SELECT AVG(m.temperature)
  FROM Measurement m
    JOIN FETCH City c
  ON m.city.id = c.id
  WHERE c.id = :cityId
    AND m.deleteFlg = false""")
  Double findMeasurementWithAverageTemperature(@Param("cityId") Long id);

  /**Using JPA with Native Query**/
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
  MeasurementCityNativeQueryResponse  findMinMeasurementByTemperatureAndCityId(
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
