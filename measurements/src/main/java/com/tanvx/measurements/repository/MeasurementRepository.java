package com.tanvx.measurements.repository;

import com.tanvx.measurements.entity.Measurement;
import com.tanvx.measurements.repository.query.MeasurementCityQueryResponse;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Long> {

  @Query("SELECT * FROM Measurement m"
      + "JOIN FETCH")
  Optional<MeasurementCityQueryResponse> findMeasurementByCityIdInNative(Long city);
}
