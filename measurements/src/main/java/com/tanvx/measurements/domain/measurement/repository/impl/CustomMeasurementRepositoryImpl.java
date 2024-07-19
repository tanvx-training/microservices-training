package com.tanvx.measurements.domain.measurement.repository.impl;

import com.tanvx.measurements.domain.measurement.dto.response.MeasurementCityQueryResponse;
import com.tanvx.measurements.domain.measurement.repository.CustomMeasurementRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

@Repository
public class CustomMeasurementRepositoryImpl implements CustomMeasurementRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public MeasurementCityQueryResponse findMaxMeasurementCustom(Long cityId) {
    Query typedQuery = entityManager
        .createNativeQuery("""
          SELECT m.temperature, m.measurement_time FROM public._measurement m
                   WHERE city_id = :cityId
          ORDER BY m.temperature DESC
          LIMIT 1""", MeasurementCityQueryResponse.class);
    typedQuery.setParameter("cityId", cityId);
    return (MeasurementCityQueryResponse) typedQuery.getSingleResult();
  }

  @Override
  public MeasurementCityQueryResponse findMinMeasurementCustom(Long cityId) {
    Query typedQuery = entityManager
        .createNativeQuery("""
          SELECT m.temperature, m.measurement_time FROM public._measurement m
                   WHERE city_id = :cityId
          ORDER BY m.temperature ASC
          LIMIT 1""", MeasurementCityQueryResponse.class);
    typedQuery.setParameter("cityId", cityId);
    return (MeasurementCityQueryResponse) typedQuery.getSingleResult();
  }

  @Override
  public Double findAverageTemperatureCustom(Long cityId) {
    Query typedQuery = entityManager
        .createNativeQuery("""
          SELECT AVG(temperature) as average
          FROM public._measurement as m
                   INNER JOIN public._city c on m.city_id = c.id
          WHERE c.id = :cityId""", Double.class);
    typedQuery.setParameter("cityId", cityId);
    return (Double) typedQuery.getSingleResult();
  }
}
